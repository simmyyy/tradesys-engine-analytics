package com.tradesys.streams

import com.tradesys.utils.properties.ApplicationProperties
import com.tradesys.utils.sources.{KafkaService, PostgreSQLService}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * class under development
  */
object ForeignExchangeStreamProcess {
  def execute(sparkSession: SparkSession, properties: ApplicationProperties): Unit = {
    import org.apache.spark.sql.functions._
    import sparkSession.implicits._

    val kafkaService = new KafkaService(properties)
    val postgreService = new PostgreSQLService(properties)
    val schema = sparkSession.read.json("schemas/fxrate_coinapi.json").schema

    val df = sparkSession
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkaService.getBootstrapServer())
      .option("subscribe", "fxrate")
      .load()
      .selectExpr("CAST(value as STRING)")


    val query1 = df.withColumn("new", from_json($"value", schema))
      .withColumn("rates", explode(col("new.data._children.rates._children")))
      .select(col("rates._children.asset_id_quote._value").alias("asset_quote_id"),
        col("rates._children.rate._value").alias("base_to_quote_rate"),
        col("rates._children.time._value").alias("measure_time"),
        col("new.data._children.asset_id_base._value").alias("asset_id_base"),
        col("new.data._children.rates").alias("rates"),
        col("new.dataProvider").alias("data_provider"),
        col("new.processId").alias("process_id")
      ).withColumn("quote_to_base_rate", lit(1) / col("base_to_quote_rate"))
      .drop("rates")

    query1.printSchema

    val queryWritable = query1.writeStream.foreachBatch((batchDF: DataFrame, batchId: Long) => {
      Class.forName(postgreService.getDriverName())
      batchDF.write
        .format("jdbc")
        .mode("overwrite")
        .options(postgreService.createConfig("fxrate"))
        .save()
    }).start()

    queryWritable.awaitTermination()

  }

  def sampleKafkaCollection(sparkSession: SparkSession, properties: ApplicationProperties, collection: String) = {
    val kafkaService = new KafkaService(properties)
    sparkSession
      .read
      .format("kafka")
      .option("kafka.bootstrap.servers", kafkaService.getBootstrapServer())
      .option("subscribe", collection)
      .option("endingOffsets", s"""{"${collection}":{"0":2}}""")
      .load()
      .selectExpr("CAST(value as STRING) as String")
      .toDF().write.mode("overwrite").format("text").save("schemas/")
  }
}
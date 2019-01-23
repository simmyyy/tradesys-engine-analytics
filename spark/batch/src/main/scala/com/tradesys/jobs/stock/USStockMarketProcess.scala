package com.tradesys.jobs.stock

import com.engine.processes.IProcessable
import com.mongodb.spark.sql.toSparkSessionFunctions
import com.tradesys.utils.properties.ApplicationProperties
import com.tradesys.utils.sources.{MongoDbService, PostgreSQLService}
import org.apache.spark.sql.SparkSession

class USStockMarketProcess extends IProcessable {
  override def execute(sparkSession: SparkSession, properties: ApplicationProperties): Unit = {
    import org.apache.spark.sql.functions._
    import sparkSession.implicits._

    val mongoDbService = new MongoDbService(properties)
    val postgreService = new PostgreSQLService(properties)
    val mongoReadConfig = mongoDbService.createMongoReadConfig("stock_chart")
    val sourceData = sparkSession.loadFromMongoDB(mongoReadConfig)

    val query1 = sourceData.withColumn("explo", explode($"data._children"))
      .withColumn("measure_time", concat_ws(" ", col("explo._children.date._value"), col("explo._children.minute._value")))
      .select(
        col("measure_time").alias("measure_time"),
        col("explo._children.label._value").alias("label"),
        col("explo._children.high._value").alias("high"),
        col("explo._children.low._value").alias("low"),
        col("explo._children.average._value").alias("average"),
        col("explo._children.volume._value").alias("volume"),
        col("explo._children.notional._value").alias("notional"),
        col("explo._children.numberOfTrades._value").alias("number_of_trades"),
        col("explo._children.marketHigh._value").alias("market_high"),
        col("explo._children.marketLow._value").alias("market_low"),
        col("explo._children.marketAverage._value").alias("market_average"),
        col("explo._children.marketVolume._value").alias("market_volume"),
        col("explo._children.marketNotional._value").alias("market_notional"),
        col("explo._children.marketNumberOfTrades._value").alias("market_number_of_trades"),
        col("explo._children.open._value").alias("open"),
        col("explo._children.close._value").alias("close"),
        col("explo._children.marketOpen._value").alias("market_open"),
        col("explo._children.marketClose._value").alias("market_close"),
        col("explo._children.changeOverTime._value").alias("change_over_time"),
        col("explo._children.marketChangeOverTime._value").alias("market_change_over_time"),
        col("dataProvider").alias("data_provider"),
        col("processid").alias("process_id"),
      )


    query1.printSchema()
    query1.show(1, false)

    val queryWritable = query1

    queryWritable.write
      .format("jdbc")
      .mode("overwrite")
      .options(postgreService.createConfig("stock_chart"))
      .save()

  }
}

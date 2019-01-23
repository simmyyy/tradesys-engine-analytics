package com.tradesys.utils.kafka

import com.tradesys.utils.properties.ApplicationProperties
import com.tradesys.utils.sources.KafkaService
import org.apache.spark.sql.SparkSession

object FileStructures {

  def dumpKafkaBSONFileStructure(sparkSession: SparkSession, properties: ApplicationProperties, collection: String): Unit = {
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

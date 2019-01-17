package com.tradesys.jobs

import com.mongodb.spark.sql.toSparkSessionFunctions
import com.tradesys.utils.properties.ApplicationProperties
import com.tradesys.utils.sourcedb.MongoDbService
import org.apache.spark.sql.SparkSession

object DataNormalizer {
  def execute(sparkSession: SparkSession, properties: ApplicationProperties): Unit = {
    import sparkSession.implicits._
    Seq(
      (1, "hello-1"),
      (2, "hello-2"),
      (3, "hello-3")
    )
      .toDF("index", "message")
      .show(3, truncate = false)

    val mongoDbService = new MongoDbService(properties)
    var mongoReadConfig = mongoDbService.createMongoReadConfig("stock_chart")

    val sourceData = sparkSession.loadFromMongoDB(mongoReadConfig).filter("dataProvider='IEXTrading'")
    sourceData.show(5, truncate = false)
  }
}

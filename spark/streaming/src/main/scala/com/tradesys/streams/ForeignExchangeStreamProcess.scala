package com.tradesys.streams

import com.mongodb.spark.sql.toSparkSessionFunctions
import com.tradesys.utils.properties.ApplicationProperties
import com.tradesys.utils.sourcedb.MongoDbService
import org.apache.spark.sql.SparkSession

object ForeignExchangeStreamProcess {
  def execute(sparkSession: SparkSession, properties: ApplicationProperties): Unit = {
    val mongoDbService = new MongoDbService(properties)
    val fxRateReadConfig = mongoDbService.createMongoReadConfig("fxrate")

    val fxSourceData = sparkSession.loadFromMongoDB(fxRateReadConfig).filter("dataProvider='COINApi'")
    fxSourceData.show(5, truncate = false)
    fxSourceData.limit(1).printSchema()

    val companyReadConfig = mongoDbService.createMongoReadConfig("company")
    val companySourceData = sparkSession.loadFromMongoDB(companyReadConfig)
    companySourceData.show(5, truncate = false)
    companySourceData.limit(1).printSchema()

    val stockReadConfig = mongoDbService.createMongoReadConfig("stock_chart")
    val stockSourceData = sparkSession.loadFromMongoDB(stockReadConfig).filter("dataProvider='IEXTrading'")
    stockSourceData.show(5, truncate = false)
    stockSourceData.limit(1).printSchema()

  }
}

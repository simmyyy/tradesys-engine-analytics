package com.tradesys.jobs.fx

import com.engine.processes.IProcessable
import com.mongodb.spark.sql.toSparkSessionFunctions
import com.tradesys.utils.properties.ApplicationProperties
import com.tradesys.utils.sources.MongoDbService
import org.apache.spark.sql.SparkSession

class ForeignExchangeDailyRates extends IProcessable {

  /**
    * todo
    *
    * @param session    spark session attached to the job
    * @param properties property file with postgresql connection details
    */
  override def execute(session: SparkSession, properties: ApplicationProperties): Unit = {
    import org.apache.spark.sql.functions._
    import session.implicits._

    val mongoDbService = new MongoDbService(properties)
    val mongoConfig = mongoDbService.createMongoReadConfig("fxrate")
    val sourceData = session.loadFromMongoDB(mongoConfig)
    sourceData.show(2, false)
  }
}

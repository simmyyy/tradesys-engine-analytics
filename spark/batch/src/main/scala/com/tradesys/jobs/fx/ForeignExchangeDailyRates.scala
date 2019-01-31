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
    sourceData.printSchema()

    val mappedDf = sourceData
      .filter("dataprovider == 'ALPHAVANTAGE'")
      .withColumn("startTimeDate", concat_ws("-", col("startTime.date.year"), col("startTime.date.month"), col("startTime.date.day")))
      .withColumn("startTimeTime", concat_ws(":", col("startTime.time.hour"), col("startTime.time.minute"), col("startTime.time.second")))
      .withColumn("startTime", concat_ws("T", col("startTimeDate"), col("startTimeTime")))
      .withColumn("from_symbol", col("data._children.Meta Data._children.2__ From Symbol"))
      .withColumn("to_symbol", col("data._children.Meta Data._children.3__ To Symbol"))
      .filter("from_symbol is not null")
      .select($"startTime", $"from_symbol", $"to_symbol", to_json($"data._children.Time Series FX (Daily)._children"))

    mappedDf.printSchema()
    mappedDf.show(10, truncate = false)

    val collectedData = mappedDf.collectAsList()
    println(collectedData.size())
//    collectedData.forEach(ok => {
//      println(ok.toString())
//    })
  }
}

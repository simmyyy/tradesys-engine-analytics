package com.tradesys.jobs.fx

import com.engine.processes.IProcessable
import com.tradesys.utils.properties.ApplicationProperties
import org.apache.spark.sql.SparkSession

class ForeignExchangeDailyRates extends IProcessable {

  /**
    * todo
    *
    * @param session    spark session attached to the job
    * @param properties property file with postgresql connection details
    */
  override def execute(session: SparkSession, properties: ApplicationProperties): Unit = {
    println("hello job")
  }
}

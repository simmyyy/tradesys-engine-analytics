package com.tradesys.jobs.refdata

import com.engine.processes.IProcessable
import com.tradesys.utils.properties.ApplicationProperties
import com.tradesys.utils.sources.PostgreSQLService
import org.apache.spark.sql.SparkSession

class FXRefDataProcess extends IProcessable {

  /**
    * This implementation of IProcessable is intended to process static csv files
    * with digital and physical currencies description. Files are not available
    * from REST API so were pulled to schemas directory manually.
    *
    * Future releases of Spring reactive engine may include streaming csv files.
    *
    * @param session    spark session attached to the job
    * @param properties property file with postgresql connection details
    */
  override def execute(session: SparkSession, properties: ApplicationProperties): Unit = {
    val digitalDF = session.read.format("csv")
      .option("header", "true")
      .load("schemas/digital_currency_list.csv")

    val physicalDF = session.read.format("csv")
      .option("header", "true")
      .load("schemas/physical_currency_list.csv")

    val unionDF = digitalDF.unionByName(physicalDF)
    val postgreService = new PostgreSQLService(properties)

    Class.forName(postgreService.getDriverName())
    unionDF.write
      .format("jdbc")
      .mode("overwrite")
      .options(postgreService.createConfig("currency"))
      .save()
  }
}

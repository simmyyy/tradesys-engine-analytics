package com.tradesys.jobs

import com.tradesys.utils.cli.CliReader
import com.tradesys.utils.properties.ApplicationProperties
import org.apache.spark.sql.SparkSession

object DataNormalizerJob {

  /**
    * Entry point for application batch processing.
    *
    * @param args e.g. --config /path_to_conf.../application.conf --source-collection fxrate
    */
  def main(args: Array[String]): Unit = {
    val cliReader = new CliReader(args)
    val cliObject = cliReader.createCliObject()
    val properties = new ApplicationProperties(cliObject)

    val sparkSession = SparkSession
      .builder()
      .appName("DataNormalizer")
      .master("local")
      .getOrCreate()

    DataNormalizer.execute(sparkSession, properties)
  }
}

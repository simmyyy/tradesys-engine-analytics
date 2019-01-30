package com.engine.processes

import com.tradesys.utils.cli.CliReader
import com.tradesys.utils.properties.ApplicationProperties
import org.apache.spark.sql.SparkSession

object ProcessRunner {

  /**
    * Main entry for all application processes, both batches and streams.
    * To run process using ProcessRunner make sure to create custom
    * implementation of IProcessable trait and add it to ProcessableFactory.
    *
    * @param args config path with name of job you would like to run e.g.
    *             --config /path_to_conf.../application.conf
    *             --process-name fxstream
    */
  def main(args: Array[String]): Unit = {
    val cliObject = new CliReader(args).createCliObject()
    val properties = new ApplicationProperties(cliObject)
    val processName = cliObject.processName

    val sparkSession = SparkSession
      .builder()
      .appName(processName)
      .master("local[2]")
      .getOrCreate()

    ProcessableFactory.getProcessImplementation(processName).execute(sparkSession, properties)
  }
}

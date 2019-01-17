package com.tradesys.streams

import com.tradesys.utils.cli.CliReader
import com.tradesys.utils.properties.ApplicationProperties
import org.apache.spark.sql.SparkSession

object DataStreamingJob {
  def main(args: Array[String]): Unit = {
    val cliReader = new CliReader(args)
    val properties = new ApplicationProperties(cliReader.createCliObject())

    val sparkSession = SparkSession
      .builder()
      .appName("ForeignExchangeRateStream")
      .master("local[2]")
      .getOrCreate()

    ForeignExchangeStreamProcess.execute(sparkSession, properties)
  }
}

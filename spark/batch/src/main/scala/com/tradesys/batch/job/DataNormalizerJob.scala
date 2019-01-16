package com.tradesys.batch.job

import com.tradesys.batch.cli.CliReader
import com.tradesys.batch.properties.ApplicationProperties
import org.apache.spark.sql.SparkSession

object DataNormalizerJob {
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
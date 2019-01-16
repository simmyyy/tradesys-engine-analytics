package com.tradesys.job

import com.tradesys.cli.CliReader
import org.apache.spark.sql.SparkSession

object DataNormalizerJob {
  def main(args: Array[String]): Unit = {
    val cliReader = new CliReader(args)
    val cliObject = cliReader.createCliObject()

    val sparkSession = SparkSession
      .builder()
      .appName("DataNormalizer")
      .master("local")
      .getOrCreate()

    DataNormalizer.execute(sparkSession)
  }
}

package com.tradesys.job

import org.apache.spark.sql.SparkSession

object DataNormalizerJob {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession
      .builder()
      .appName("DataNormalizer")
      .master("local")
      .getOrCreate()

    DataNormalizer.execute(sparkSession)
  }
}

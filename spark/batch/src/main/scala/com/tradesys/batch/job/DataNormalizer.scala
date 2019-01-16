package com.tradesys.batch.job

import com.tradesys.batch.properties.ApplicationProperties
import org.apache.spark.sql.SparkSession

object DataNormalizer {
  def execute(sparkSession: SparkSession, properties: ApplicationProperties): Unit = {
    import sparkSession.implicits._

    Seq(
      (1, "hello-1"),
      (2, "hello-2"),
      (3, "hello-3")
    )
      .toDF("index", "message")
      .show(3, truncate = false)

  }
}

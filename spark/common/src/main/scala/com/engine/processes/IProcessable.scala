package com.engine.processes

import com.tradesys.utils.properties.ApplicationProperties
import org.apache.spark.sql.SparkSession

trait IProcessable {

  def execute(session: SparkSession, properties: ApplicationProperties)

}

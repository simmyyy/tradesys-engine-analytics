package com.tradesys.utils.properties

import java.io.File

import com.tradesys.utils.cli.CliObject
import com.typesafe.config.{Config, ConfigFactory}

final case class SourceDbData(user: String, password: String, host: String, port: String, database: String)

final case class SourceKafka(server: String, port: String)

case class ApplicationProperties(cliObject: CliObject) {
  val config: Config = ConfigFactory.parseFile(new File(cliObject.configPath))
}

object ApplicationProperties {
  /**
    * Creates source database object
    *
    * @return source database data object
    */
  def createSourceDbObject(config: Config): SourceDbData = {
    val sourceDbUser = config.getString("source-db.user")
    val sourceDbPassword = config.getString("source-db.password")
    val sourceDbHost = config.getString("source-db.host")
    val sourceDbPort = config.getString("source-db.port")
    val sourceDbDatabase = config.getString("source-db.database")

    SourceDbData(sourceDbUser, sourceDbPassword, sourceDbHost, sourceDbPort, sourceDbDatabase)
  }

  def createKafkaSource(config: Config): SourceKafka = {
    val server = config.getString("source-kafka.host")
    val port = config.getString("source-kafka.port")

    SourceKafka(server, port)
  }
}

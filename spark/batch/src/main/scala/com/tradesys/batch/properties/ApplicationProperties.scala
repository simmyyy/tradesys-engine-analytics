package com.tradesys.batch.properties

import java.io.File

import com.tradesys.batch.cli.CliObject
import com.typesafe.config.{Config, ConfigFactory}

final case class SourceDbData(user: String, password: String, host: String, port: String, database: String)

class ApplicationProperties(cliObject: CliObject) {
  val config: Config = ConfigFactory.parseFile(new File(cliObject.configPath))

  val sourceDbData: SourceDbData = createSourceDbObject()

  /**
    * Creates source database object
    *
    * @return source database data object
    */
  private def createSourceDbObject(): SourceDbData = {
    val sourceDbUser = config.getString("source-db.user")
    val sourceDbPassword = config.getString("source-db.password")
    val sourceDbHost = config.getString("source-db.host")
    val sourceDbPort = config.getString("source-db.port")
    val sourceDbDatabase = config.getString("source-db.database")

    SourceDbData(sourceDbUser, sourceDbPassword, sourceDbHost, sourceDbPort, sourceDbDatabase)
  }
}

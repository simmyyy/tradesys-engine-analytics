package com.tradesys.utils.sources

import com.tradesys.utils.properties.{ApplicationProperties, SourceDbData}

class PostgreSQLService(properties: ApplicationProperties) {
  private val sourceConfig: SourceDbData = ApplicationProperties.createTargetPostgreDbObject(properties.config)
  private val url = createUrl()
  private val driver = "org.postgresql.Driver"


  def createConfig(tableName: String): Map[String, String] = {
    Map("url" -> url, "driver" -> driver, "dbtable" -> tableName)
  }

  def createUrl(): String = {
    s"""jdbc:postgresql://${sourceConfig.host}:${sourceConfig.port}/${sourceConfig.database}?user=${sourceConfig.user}&password=${sourceConfig.password}"""
  }

  def getDriverName(): String = {
    driver
  }


}

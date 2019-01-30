package com.tradesys.utils.sources

import com.mongodb.spark.config.ReadConfig
import com.tradesys.utils.properties.{ApplicationProperties, SourceDbData}

class MongoDbService(properties: ApplicationProperties) {
  private val sourceDbObject: SourceDbData = ApplicationProperties.createSourceDbObject(properties.config)
  private val connectionString: String = createConnectionString()

  /**
    * Creates configuration object for Mongo database
    *
    * @return configuration object
    */
  def createMongoReadConfig(collectionName: String): ReadConfig = {
    ReadConfig(Map("database" -> sourceDbObject.database, "collection" -> collectionName, "uri" -> connectionString))
  }

  /**
    * Creates connection string to Mongo database
    * Notice! without 'authSource={database}' in URI authentication would fail
    *
    * @return connection string
    */
  private def createConnectionString(): String = {
    s"""mongodb://${sourceDbObject.user}:${sourceDbObject.password}@${sourceDbObject.host}:${sourceDbObject.port}/?authSource=tradesys"""
  }
}

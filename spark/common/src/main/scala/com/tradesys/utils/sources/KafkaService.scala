package com.tradesys.utils.sources

import com.tradesys.utils.properties.{ApplicationProperties, SourceKafka}

class KafkaService(properties: ApplicationProperties) {
  private val sourceKafkaConfig: SourceKafka = ApplicationProperties.createKafkaSource(properties.config)


  def getDefaultKafkaConfig(groupId: String) = {
    import org.apache.kafka.common.serialization.StringDeserializer
    val kafkaParams = Map(
      "bootstrap.servers" -> s"""${sourceKafkaConfig.server}:${sourceKafkaConfig.port}""",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> groupId,
      "auto.offset.reset" -> "earliest"
    )
    kafkaParams
  }

  //@TODO what if more servers?
  def getBootstrapServer(): String = {
    s"""${sourceKafkaConfig.server}:${sourceKafkaConfig.port}"""
  }
}

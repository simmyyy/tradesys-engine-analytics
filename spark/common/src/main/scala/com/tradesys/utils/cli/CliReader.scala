package com.tradesys.utils.cli

import org.rogach.scallop.{ScallopConf, ScallopOption}

final case class CliObject(configPath: String)

class CliReader(args: Array[String]) extends ScallopConf(args) {
  private val configPath: ScallopOption[String] = opt[String](name = "config", required = true)

  def createCliObject(): CliObject = CliObject(configPath.apply())

  verify()
}


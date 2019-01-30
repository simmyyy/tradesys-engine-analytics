package com.tradesys.utils.cli

import org.rogach.scallop.{ScallopConf, ScallopOption}

final case class CliObject(configPath: String, processName: String)

class CliReader(args: Array[String]) extends ScallopConf(args) {
  private val configPath: ScallopOption[String] = opt[String](name = "config", required = true)
  private val processName: ScallopOption[String] = opt[String](name = "process-name", required = false)

  def createCliObject(): CliObject = CliObject(configPath.apply(), processName.getOrElse(""))

  verify()
}


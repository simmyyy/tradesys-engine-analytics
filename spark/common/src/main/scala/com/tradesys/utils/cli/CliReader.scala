package com.tradesys.utils.cli

import java.nio.file.{Files, Paths}
import org.rogach.scallop.{ScallopConf, ScallopOption}

final case class CliObject(configPath: String, processName: String)

class CliReader(args: Array[String]) extends ScallopConf(args) {
  private val configPath: ScallopOption[String] = opt[String](name = "config", required = true)
  private val processName: ScallopOption[String] = opt[String](name = "process-name", required = false)

  /**
    * Creates CLI object
    *
    * @return CLI object
    */
  def createCliObject(): CliObject = {
    val isConfigPathValid = validateConfigPath(configPath.apply())
    if (!isConfigPathValid) {
      throw new IllegalArgumentException("Config file: " + configPath.apply() + " not found")
    }
    CliObject(configPath.apply(), processName.getOrElse(""))
  }

  /**
    * Validates configuration file path passed by user
    *
    * @param configPath configuration file path
    * @return whether configuration file exists
    */
  private def validateConfigPath(configPath: String): Boolean = {
    Files.exists(Paths.get(configPath))
  }

  verify()
}


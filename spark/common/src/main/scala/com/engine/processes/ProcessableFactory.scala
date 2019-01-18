package com.engine.processes

import com.tradesys.jobs.refdata.FXRefDataProcess
import com.tradesys.streams.ForeignExchangeStreamProcess

object ProcessableFactory {

  def getProcessImplementation(processName: String): IProcessable = {
    if (processName equals "fxstream") {
      new ForeignExchangeStreamProcess()
    }
    else if (processName equals "fxrefdata") {
      new FXRefDataProcess()
    }
    else {
      throw new RuntimeException("Cannot get instance of: " + processName)
    }
  }


}

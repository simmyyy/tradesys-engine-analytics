package com.engine.processes

import com.tradesys.streams.ForeignExchangeStreamProcess

object ProcessableFactory {

  def getProcessImplementation(processName: String): IProcessable = {
    if (processName.equals("fxstream")) {
      new ForeignExchangeStreamProcess()
    }
    else {
      throw new RuntimeException("Cannot get instance of: " + processName)
    }
  }


}

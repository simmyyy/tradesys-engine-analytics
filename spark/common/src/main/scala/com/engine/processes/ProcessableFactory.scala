package com.engine.processes

import com.tradesys.jobs.refdata.FXRefDataProcess
import com.tradesys.jobs.stock.USStockMarketProcess
import com.tradesys.streams.ForeignExchangeStreamProcess

object ProcessableFactory {

  def getProcessImplementation(processName: String): IProcessable = {
    if (processName equalsIgnoreCase  "fxstream") {
      new ForeignExchangeStreamProcess()
    }
    else if (processName equalsIgnoreCase  "fxrefdata") {
      new FXRefDataProcess()
    }
    else if(processName equalsIgnoreCase  "usstockmarket") {
      new USStockMarketProcess()
    }
    else {
      throw new RuntimeException("Cannot get instance of: " + processName)
    }
  }


}

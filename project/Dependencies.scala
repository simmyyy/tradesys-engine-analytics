import sbt._

object Dependencies {
  object versions {
    val java = "11"
    val scala = "2.12.8"
    val spark = "2.4.0"
  }
  
  val sparkSql = "org.apache.spark" %% "spark-sql" % versions.spark
  val sparkCore = "org.apache.spark" %% "spark-core" % versions.spark
  val spark: Seq[ModuleID] = Seq(sparkSql, sparkCore)
}

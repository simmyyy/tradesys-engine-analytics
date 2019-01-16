import sbt._

object Dependencies {
  object versions {
    val java = "11"
    val scala = "2.12.8"
    val spark = "2.4.0"
    val scallop = "3.1.5"
    val typeSafeConfig = "1.3.2"
  }
  
  val sparkSql = "org.apache.spark" %% "spark-sql" % versions.spark
  val sparkCore = "org.apache.spark" %% "spark-core" % versions.spark
  val sparkMongoConnector = "org.mongodb.spark" %% "mongo-spark-connector" % versions.spark
  
  val scallop = "org.rogach" %% "scallop" % versions.scallop
  val typeSafeConfig = "com.typesafe" % "config" % versions.typeSafeConfig
  
  val spark: Seq[ModuleID] = Seq(sparkSql, sparkCore, sparkMongoConnector)
}
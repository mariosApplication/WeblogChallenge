name := "WeblogChallenge"

version := "1.0"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.1.1",
  "org.apache.spark" %% "spark-sql" % "2.1.1",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test"
)
package Engine

/**
  * Created by mariosk on 15/06/17.
  */
import Engine.PreprocessData.{LogLine, parseLine}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ReadData {
  def getWeblogs(context: SparkContext, filepath: String): RDD[LogLine] =
    context
      .textFile(filepath)
      .map(parseLine)

  def defaultContext(): SparkContext =
    SparkSession
      .builder()
      .appName("Weblog Challenge")
      .master("local[2]")
      .getOrCreate()
      .sparkContext
}

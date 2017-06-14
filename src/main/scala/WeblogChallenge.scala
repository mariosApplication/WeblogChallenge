/**
  * Created by mariosk on 12/06/17.
  */
import PreprocessData.{LogLine, parseLine}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object WeblogChallenge extends App {
  def getWeblogs(context: SparkContext, filepath: String): RDD[LogLine] = {
    context
      .textFile(filepath)
      .map(parseLine)
  }

  def defaultContext() =
    SparkSession
      .builder()
      .appName("Weblog Challenge")
      .master("local[2]")
      .getOrCreate()
      .sparkContext

  getWeblogs(defaultContext(), "data/2015_07_22_mktplace_shop_web_log_sample.log")
    .take(3)
    .foreach(println)
}

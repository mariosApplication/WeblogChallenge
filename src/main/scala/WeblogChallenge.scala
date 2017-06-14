/**
  * Created by mariosk on 12/06/17.
  */
import PreprocessData.parseLine
import org.apache.spark.sql.SparkSession

object WeblogChallenge extends App {
  val spark = SparkSession.builder().appName("Weblog Challenge").master("local[2]").getOrCreate()

  val lines = spark.sparkContext.textFile("data/2015_07_22_mktplace_shop_web_log_sample.log")
  val dataset = lines.map(parseLine)

  dataset.take(3).foreach(println)
  spark.close()
}

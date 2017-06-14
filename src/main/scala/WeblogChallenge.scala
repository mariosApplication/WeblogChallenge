/**
  * Created by mariosk on 12/06/17.
  */
import org.apache.spark.sql.{Row, SparkSession}

object WeblogChallenge extends App {
  val spark = SparkSession.builder().appName("Weblog Challenge").master("local[2]").getOrCreate()

  def parseLine(line: String): Row = {
    val logsPattern = """^(\S+) (\S+) (\S+) (\S+) (\S+) (\S+) (\S+) (\S+) (\S+) (\S+) (\S+) \"([^\"]+)\" \"([^\"]+)\" (\S+) (\S+)""".r
    val a:AnyVal = 4
    line match {
      case logsPattern(a @ _*) => Row.fromSeq(a)
    }
  }

  val weblogs =
    spark.sparkContext
      .textFile("data/2015_07_22_mktplace_shop_web_log_sample.log")
      .map(parseLine)
}

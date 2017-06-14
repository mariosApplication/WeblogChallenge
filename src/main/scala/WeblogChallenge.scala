/**
  * Created by mariosk on 12/06/17.
  */
import org.apache.spark.sql.types._
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

  val structFields =
    "timestamp elb client:port backend:port request_processing_time backend_processing_time response_processing_time elb_status_code backend_status_code received_bytes sent_bytes request user_agent ssl_cipher ssl_protocol"
      .split(" ")
      .map(name => StructField(name, StringType, nullable = false))
  val schema = StructType(structFields)

  val rows =
    spark.sparkContext
      .textFile("data/2015_07_22_mktplace_shop_web_log_sample.log")
      .map(parseLine)

  val weblogs = spark.createDataFrame(rows, schema)
  weblogs.createOrReplaceTempView("weblogs")

  spark.sql("select request from weblogs limit 5").show()

  spark.close()
}

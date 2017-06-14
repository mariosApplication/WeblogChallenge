/**
  * Created by mariosk on 13/06/17.
  */
import PreprocessData.{LogLine, parseLine}
import org.apache.spark.sql.SparkSession
import org.scalatest._

class WeblogChallengeSpec extends FlatSpec with Matchers with BeforeAndAfter {
  var spark: SparkSession = _
  before {
    spark = SparkSession
      .builder
      .master("local")
      .appName("testing")
      .getOrCreate()
  }

  after {
    spark.close()
  }

  "WeblogChallenge" should "parse a weblog file" in {
    val input = spark.sparkContext.textFile("src/test/resources/weblogs_first_3_lines.log")
    val expected = Array(
      Array("2015-07-22T09:00:28.019143Z", "GET https://paytm.com:443/shop/authresponse?code=f2405b05-e2ee-4b0d-8f6a-9fed0fcfe2e0&state=null HTTP/1.1"),
      Array("2015-07-22T09:00:27.894580Z", "GET https://paytm.com:443/shop/wallet/txnhistory?page_size=10&page_number=0&channel=web&version=2 HTTP/1.1"),
      Array("2015-07-22T09:00:27.885745Z", "GET https://paytm.com:443/shop/wallet/txnhistory?page_size=10&page_number=0&channel=web&version=2 HTTP/1.1"))

    val result = input
        .map(
          ((l: LogLine) => Array(l.timestamp, l.request))
            .compose(parseLine))
        .take(3)

    result should be(expected)
  }
}

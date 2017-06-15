/**
  * Created by mariosk on 13/06/17.
  */
import PreprocessData.{parseLine, LogLine}
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

class PreprocessDataSpec extends FlatSpec with BeforeAndAfter with Matchers {
  "PreprocessData" should "parse lines to rows" in {
    val rawLine = "2015-07-22T09:00:28.019143Z marketpalce-shop 123.242.248.130:54635 10.0.6.158:80 0.000022 0.026109 0.00002 200 200 0 699 \"GET https://paytm.com:443/shop/authresponse?code=f2405b05-e2ee-4b0d-8f6a-9fed0fcfe2e0&state=null HTTP/1.1\" \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36\" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2"

    val expected = LogLine(BigDecimal("23959260.46698333333333333333333333"), "marketpalce-shop", "123.242.248.130:54635", "10.0.6.158:80", "0.000022", "0.026109", "0.00002", "200", "200", "0", "699", "GET https://paytm.com:443/shop/authresponse?code=f2405b05-e2ee-4b0d-8f6a-9fed0fcfe2e0&state=null HTTP/1.1", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36", "ECDHE-RSA-AES128-GCM-SHA256", "TLSv1.2")

    parseLine(rawLine) should be(expected)
  }

  it should "parse a line with an empty user agent" in {
    val rawLine = "2015-07-22T17:45:17.549417Z marketpalce-shop 106.202.23.193:47132 10.0.4.150:80 0.000021 0.001118 0.000032 200 200 0 1150 \"GET https://paytm.com:443/favicon.ico HTTP/1.1\" \"\" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2"

    val expected = LogLine(BigDecimal("23959785.29248333333333333333333333"), "marketpalce-shop", "106.202.23.193:47132", "10.0.4.150:80", "0.000021", "0.001118", "0.000032", "200", "200", "0", "1150", "GET https://paytm.com:443/favicon.ico HTTP/1.1", "", "ECDHE-RSA-AES128-GCM-SHA256", "TLSv1.2")

    parseLine(rawLine) should be(expected)
  }

  it should "parse a line with double quotes in the request" in {
    val rawLine = "2015-07-22T16:10:38.028609Z marketpalce-shop 106.51.132.54:4841 10.0.4.227:80 0.000022 0.000989 0.00002 400 400 0 166 \"GET https://paytm.com:443/'\"\\'\\\");|]*{%0d%0a<%00>/about/ HTTP/1.1\" \"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)\" DHE-RSA-AES128-SHA TLSv1"

    val expected = LogLine(23959690.6338, "marketpalce-shop", "106.51.132.54:4841", "10.0.4.227:80", "0.000022", "0.000989", "0.00002", "400", "400", "0", "166", "GET https://paytm.com:443/'\"\\'\\\");|]*{%0d%0a<%00>/about/ HTTP/1.1", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)", "DHE-RSA-AES128-SHA", "TLSv1")

    parseLine(rawLine) should be(expected)
  }
}

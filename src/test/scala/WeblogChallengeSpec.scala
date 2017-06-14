package unit

/**
  * Created by mariosk on 13/06/17.
  */
import org.scalatest._

class WeblogChallengeSpec extends FlatSpec with Matchers {
  "The " should "" in {
    val rawLine = "2015-07-22T09:00:28.019143Z marketpalce-shop 123.242.248.130:54635 10.0.6.158:80 0.000022 0.026109 0.00002 200 200 0 699 \"GET https://paytm.com:443/shop/authresponse?code=f2405b05-e2ee-4b0d-8f6a-9fed0fcfe2e0&state=null HTTP/1.1\" \"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36\" ECDHE-RSA-AES128-GCM-SHA256 TLSv1.2"

    val expected = List("2015-07-22T09:00:28.019143Z", "marketpalce-shop", "123.242.248.130:54635", "10.0.6.158:80", "0.000022", "0.026109", "0.00002", "200", "200", "0", "699", "GET https://paytm.com:443/shop/authresponse?code=f2405b05-e2ee-4b0d-8f6a-9fed0fcfe2e0&state=null HTTP/1.1", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36", "ECDHE-RSA-AES128-GCM-SHA256", "TLSv1.2")

    WeblogChallenge.parseLine(rawLine) should be(expected)
  }
}

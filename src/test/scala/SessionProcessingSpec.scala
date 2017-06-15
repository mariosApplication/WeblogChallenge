/**
  * Created by mariosk on 15/06/17.
  */
import SessionProcessing.getSessionLenght
import org.scalatest.{FlatSpec, Matchers}
import TestHelper.simpleLogLine

class SessionProcessingSpec extends FlatSpec with Matchers {
  "GetSessionLength" should "compute the time length of a session" in {
    val session = List(2,5,3,6,3).map(simpleLogLine)

    getSessionLenght(session) should be(BigDecimal(4))
  }
}

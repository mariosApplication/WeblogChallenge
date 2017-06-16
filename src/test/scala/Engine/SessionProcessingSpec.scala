package Engine

/**
  * Created by mariosk on 15/06/17.
  */
import Engine.SessionProcessing.getSessionLenght
import TestHelper.simpleLogLine
import org.scalatest.{FlatSpec, Matchers}

class SessionProcessingSpec extends FlatSpec with Matchers {
  "GetSessionLength" should "compute the time length of a session" in {
    val session = List(2,5,3,6,3).map(simpleLogLine)

    getSessionLenght(session) should be(BigDecimal(4))
  }
}

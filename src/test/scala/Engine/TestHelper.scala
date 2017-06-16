package Engine

/**
  * Created by mariosk on 15/06/17.
  */
import Engine.PreprocessData.LogLine
import scala.math.BigDecimal.int2bigDecimal

object TestHelper {
  val simpleLogLineWithIp: (Int, String) => LogLine = (minutes, ip) => LogLine(timestamp = int2bigDecimal(minutes), "", client_port = ip,"","","","","","","","","","","","")

  val simpleLogLine: Int => LogLine = minutes => simpleLogLineWithIp(minutes, "")
}

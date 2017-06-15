/**
  * Created by mariosk on 12/06/17.
  */
import ReadData.{getWeblogs, defaultContext}
import ExtractSessions.extractSessions
import SessionProcessing.getSessionLenght

object WeblogChallenge extends Serializable with App {
  val sessions = extractSessions()(getWeblogs(defaultContext(), "data/2015_07_22_mktplace_shop_web_log_sample.log"))

  val averageSessionTime =
    sessions
      .flatMap {
        case (_, Some(ipSessions)) => ipSessions.map(getSessionLenght)
        case (_, None) => Seq() }
      .mean()

  println(s"Average session time: ${(averageSessionTime * 60.0)} seconds")
  // Average session time: 28.23673603909804 seconds
}

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
        case (_, Some(ipSessions)) =>
            ipSessions
              .filter(_.length >= 2)
              .map(getSessionLenght)
        case (_, None) => Seq() }
      .mean()

  println(s"Average session time: ${averageSessionTime * 60.0} seconds")
  // Averate session time: 38.03842136008329 seconds -- excluding all 0 time length sessions
  // Average session time: 37.95641017922952 seconds -- excluding single log sessions
  // Average session time: 14.487383547154677 seconds -- including single log sessions with 0 time length
}

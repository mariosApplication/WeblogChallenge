/**
  * Created by mariosk on 12/06/17.
  */
import ReadData.{getWeblogs, defaultContext}
import ExtractSessions.extractSessions
import SessionProcessing.getSessionLenght

object WeblogChallenge extends Serializable with App {
  val sessionsPerIP = extractSessions()(getWeblogs(defaultContext(), "data/2015_07_22_mktplace_shop_web_log_sample.log"))

  val sessions = sessionsPerIP
    .flatMap{
      case (_, Some(ipSessions)) => ipSessions
      case _ => Seq()
    }

  /// AVERAGE SESSION TIME ///
  val averageSessionTime =
    sessions
      .filter(_.length >= 2)
      .map(getSessionLenght)
      .mean()

  println(s"Average session time: ${averageSessionTime * 60.0} seconds")
  // Averate session time: 38.03842136008329 seconds -- excluding all 0 time length sessions
  // Average session time: 37.95641017922952 seconds -- excluding single log sessions
  // Average session time: 14.487383547154677 seconds -- including single log sessions with 0 time length


  /// UNIQUE URL VISITS PER SESSION ///
  def parseUrlFromRequest(request: String) : String = {
    val pattern = """^\S+ (\S+:\/\/\S+):\S+ \S+$""".r
    request match { case pattern(url) => url }
  }

  val uniqueUrlVisits =
    sessions
      .map(session =>
        (session,
         session
           .map(log => parseUrlFromRequest(log.request))
           .distinct))

  println(s"Number of unique url visits: ${uniqueUrlVisits.map(_._2.length).sum()}")
  // Number of unique url visits: 438886.0
}

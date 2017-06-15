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

  /// MOST ENGAGED USERS ///
  val mostEngagedUsers =
    sessionsPerIP
      .collect{
        case (ip, Some(ipSessions)) => (ip, ipSessions.map(getSessionLenght).max)
      }
      .sortBy(-_._2)

  println(s"Those are the most engaged ip addresses: ${mostEngagedUsers.take(10).foreach(println)}")
  // Weird... The lengths seem to be very close...
  //  (213.239.204.204:35094,34.42645)
  //  (103.29.159.138:57045,34.42343333333333333333333333)
  //  (203.191.34.178:10400,34.42243333333333333333333333)
  //  (78.46.60.71:58504,34.41436666666666666666666666)
  //  (54.169.191.85:15462,34.36146666666666666666666667)
  //  (103.29.159.186:27174,34.34790000000000000000000000)
  //  (122.169.141.4:11486,34.3355)
  //  (122.169.141.4:50427,34.31826666666666666666666666)
  //  (103.29.159.62:55416,34.31655000000000000000000000)
  //  (103.29.159.213:59453,34.30885000000000000000000000)
}

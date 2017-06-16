package Engine

/**
  * Created by mariosk on 15/06/17.
  */
import Engine.ExtractSessions.{Session, extractSessions}
import Engine.ReadData.{defaultContext, getWeblogs}
import Engine.SessionProcessing.getSessionLenght
import org.apache.spark.rdd.RDD

object AnalyticalGoals {
  def getSessionsPerIp(filepath: String): RDD[(String, Seq[Session])] =
    extractSessions()(getWeblogs(defaultContext(), filepath))
      .collect{ case (ip, Some(ipSessions)) => (ip, ipSessions)}

  def allSessions(sessionsPerIp: RDD[(String, Seq[Session])]): RDD[Session] =
    sessionsPerIp.flatMap(_._2)

  def getAverageSessionTime(sessions: RDD[Session]): Double =
    sessions
      .filter(_.length >= 2)
      .map(getSessionLenght)
      .mean()

  private[this] def parseUrlFromRequest(request: String) : String = {
    val pattern = """^\S+ (\S+:\/\/\S+):\S+ \S+$""".r
    request match { case pattern(url) => url }
  }

  def getUniqueUrlVisits(sessions: RDD[Session]): RDD[(Session, List[String])] =
    sessions
      .map(session =>
        (session,
         session
           .map(log => parseUrlFromRequest(log.request))
           .distinct))

  def getMostEngagedUsers(sessionsPerIp: RDD[(String, Seq[Session])]): RDD[(String, BigDecimal)] =
    sessionsPerIp
      .collect{
        case (ip, ipSessions) => (ip, ipSessions.map(getSessionLenght).max)
      }
      .sortBy(-_._2)
}

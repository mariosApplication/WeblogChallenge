/**
  * Created by mariosk on 12/06/17.
  */
import AnalyticalGoals.{getSessionsPerIP, allSessions, getAverageSessionTime, getUniqueUrlVisits, getMostEngagedUsers}

object WeblogChallenge extends Serializable with App {
  val dataFilepath = "data/2015_07_22_mktplace_shop_web_log_sample.log"

  val sessionsPerIP = getSessionsPerIP(dataFilepath)

  val sessions = allSessions(sessionsPerIP)

  val averageSessionTime = getAverageSessionTime(sessions)

  val uniqueUrlVisits = getUniqueUrlVisits(sessions)

  val mostEngagedUsers = getMostEngagedUsers(sessionsPerIP)
}

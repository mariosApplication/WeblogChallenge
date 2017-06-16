/**
  * Created by mariosk on 12/06/17.
  */
import Engine.AnalyticalGoals.{allSessions, getAverageSessionTime, getMostEngagedUsers, getSessionsPerIp, getUniqueUrlVisits}

import scala.reflect.io.File

object WeblogChallenge extends Serializable with App {
  def runAnalysis(): Unit = {
    val t0 = System.nanoTime()

    val sessionsPerIp = getSessionsPerIp("data/2015_07_22_mktplace_shop_web_log_sample.log")

    val sessions = allSessions(sessionsPerIp)

    val averageSessionTime = getAverageSessionTime(sessions)

    val uniqueUrlVisits = getUniqueUrlVisits(sessions)

    val mostEngagedUsers = getMostEngagedUsers(sessionsPerIp)

    val top100mostEngagedUsers =
      mostEngagedUsers
        .take(100)
        .map(_.toString())

    sessionsPerIp.saveAsTextFile("results/sessions_per_ip")
    sessions.saveAsTextFile("results/sessions")
    File("results/average_session_time").writeAll(s"Average session time: ${averageSessionTime * 60} seconds")
    uniqueUrlVisits.saveAsTextFile("results/unique_url_visits")
    mostEngagedUsers.saveAsTextFile("results/most_engaged_users")
    File("results/top_100_most_engaged_users").writeAll(top100mostEngagedUsers:_*)

    val t1 = System.nanoTime()
    val totalTime = f"${(t1 - t0).toDouble/Math.pow(10, 9)}%1.0f"
    println(s"Total elapsed time: $totalTime seconds")
  }
}

/**
  * Created by mariosk on 15/06/17.
  */
object SessionProcessing {
  def getSessionLenght(session: ExtractSessions.Session): BigDecimal = {
    val timestamps = session.map(_.timestamp)
    timestamps.max - timestamps.min
  }
}

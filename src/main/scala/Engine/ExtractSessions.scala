package Engine

/**
  * Created by mariosk on 14/06/17.
  */
import Engine.PreprocessData.LogLine
import org.apache.spark.rdd.RDD

object ExtractSessions {
  type Session = List[LogLine]

  def splitToSessions(inactiveWindow: Int)(logs: Seq[LogLine]): Option[Seq[Session]] = {
    val logPairs = logs
      .sortBy(log => log.timestamp)
      .sliding(2)
      .toList

    (logPairs.isEmpty, logPairs.head.length) match {
      case (false, 2) =>
      Some(logPairs
        .foldLeft (Seq (List(logPairs.head.head)) ) {
          case (head :: agg, next) =>
            if (next(1).timestamp - next.head.timestamp <= inactiveWindow)
              (next(1) :: head) :: agg
            else List(next(1)) :: (head :: agg)
        }
        .map (x => x.reverse)
        .reverse)

      case (false, 1) => Some(Seq(List(logPairs.head.head)))
      case _ => None
    }
  }

  def extractSessions(inactiveWindow: Int = 15)(logs: RDD[LogLine]): RDD[(String, Option[Seq[Session]])] =
    logs
      .groupBy(log => log.client_port)
      .map { case (ip, relevantLogs) => (ip, splitToSessions(inactiveWindow)(relevantLogs.toSeq)) }
}
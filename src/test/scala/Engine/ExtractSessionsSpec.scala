package Engine

/**
  * Created by mariosk on 14/06/17.
  */
import Engine.ExtractSessions.{extractSessions, splitToSessions}
import Engine.ReadData.getWeblogs
import TestHelper.simpleLogLine
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class ExtractSessionsSpec extends FlatSpec with Matchers with BeforeAndAfterAll {
  var spark: SparkSession = _
  override def beforeAll(): Unit =
    spark = SparkSession
      .builder
      .master("local")
      .appName("testing")
      .getOrCreate()

  override def afterAll {
    spark.close()
  }

  "Split to sessions" should "extract all sessions with an inactive window of 15 minutes" in {
    val logs = Seq(93, 143, 5, 132, 37, 95, 116, 90, 120, 22, 140, 8, 100).map(simpleLogLine)
    val expected = Seq(
      Seq(5, 8, 22, 37),
      Seq(90, 93, 95, 100),
      Seq(116, 120, 132, 140, 143))
        .map(session => session.map(simpleLogLine))

    splitToSessions(15)(logs) should be(expected)
  }

  it should "extract a session from a single log line" in {
    val logs = Seq(1).map(simpleLogLine)
    val expected = Seq(Seq(1)).map(_.map(simpleLogLine))

    splitToSessions(15)(logs) should be(expected)
  }

  "Extract sessions" should "extract all sessions for each ip" in {
    val expected = Array(
      ("183.82.1.148:59048", List(List(2.3959260615933333E7, 2.3959260654933333E7, 2.39592606681E7, 2.39592607061E7, 2.3959260764033332E7, 2.395926077315E7))),
      ("117.203.144.55:50653", List(List(2.3959260525683332E7, 2.39592607319E7))),
      ("180.179.74.201:51852", List(List(2.3959260555866666E7))),
      ("59.94.106.79:8346", List(List(2.3959260613066666E7))),
      ("54.251.151.39:44347", List(List(2.39592606393E7))))

    def timestampsFromSessions(sessions: Seq[ExtractSessions.Session]) = sessions.map(session => session.map(line => line.timestamp.doubleValue()))

    val result =
      extractSessions()(getWeblogs(spark.sparkContext, "src/test/resources/weblogs_first_10000_lines.log"))
        .map { case (ip, s) => (ip, timestampsFromSessions(s)) }
        .take(5)

    result should be(expected)
  }
}

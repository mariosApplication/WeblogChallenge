/**
  * Created by mariosk on 12/06/17.
  */
import ReadData.{getWeblogs, defaultContext}
import ExtractSessions.extractSessions

object WeblogChallenge extends Serializable with App {
  def test() =
    extractSessions()(getWeblogs(defaultContext(), "data/2015_07_22_mktplace_shop_web_log_sample.log"))
      .take(10)
      .foreach(println)

  test()
}

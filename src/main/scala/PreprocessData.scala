/**
  * Created by mariosk on 13/06/17.
  */
object PreprocessData {
  case class LogLine(
    timestamp: String,
    elb: String,
    client_port: String,
    backend_port: String,
    request_processing_time: String,
    backend_processing_time: String,
    response_processing_time: String,
    elb_status_code: String,
    backend_status_code: String,
    received_bytes: String,
    sent_bytes: String,
    request: String,
    user_agent: String,
    ssl_cipher: String,
    ssl_protocol: String)

  def parseLine(line: String): LogLine = {
    val logsPattern = """^(\S+) (\S+) (\S+) (\S+) (\S+) (\S+) (\S+) (\S+) (\S+) (\S+) (\S+) \"([^\"]+)\" \"([^\"]+)\" (\S+) (\S+)""".r

    line match {
      case logsPattern(a @ _*) => LogLine(a(0),a(1),a(2),a(3),a(4),a(5),a(6),a(7),a(8),a(9),a(10),a(11),a(12),a(13),a(14))
    }
  }
}
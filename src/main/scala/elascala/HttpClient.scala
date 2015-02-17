package elascala

import org.slf4j.LoggerFactory

import scalaj.http.{HttpResponse, Http}

/**
 * Created by Vayne on 2015-02-18.
 */
object HttpClient {
  val logger = LoggerFactory.getLogger(classOf[Elascala])

  def get(url: String): HttpResult = {
    execute(url, "") {
      Http(url).asString
    }
  }

  def put(url: String, any: Any): HttpResult = {
    put(url, Json.toJson(any))
  }

  def put(url: String, body: String): HttpResult = {
    execute(url, body) {
      Http(url).method("put").postData(body).asString
    }
  }

  def post(url: String, body: String): HttpResult = {
    execute(url, body) {
      Http(url).method("post").postData(body).asString
    }
  }

  private def execute(url: Any, body: Any)(f: => HttpResponse[String]): HttpResult = {
    logger.info("url: {}, body: {}", url, body)
    val result = f
    logger.info("url: {}, status: {}", url, result.code)
    HttpResult(result.code, result.body)
  }
}

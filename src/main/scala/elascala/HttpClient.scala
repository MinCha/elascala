package elascala

import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._
import scalaj.http.{Http, HttpResponse}

/**
 * Created by Vayne on 2015-02-18.
 */
object HttpClient {
  val logger = LoggerFactory.getLogger(classOf[Elascala])

  def get(url: String): HttpResult = {
    execute(url, "") {
      http(url).asString
    }
  }

  private def http(url: String) = {
    Http(url).timeout(5000, 5000)
  }

  private def execute(url: Any, body: Any)(f: => HttpResponse[String]): HttpResult = {
    val result = f
    logger.info("url: {}, body: {} => status: {}",
      List(url, body, result.code).asJava.toArray: _*)
    HttpResult(result.code, result.body)
  }

  def put(url: String, any: Any): HttpResult = {
    put(url, Json.toJson(any))
  }

  def put(url: String, body: String): HttpResult = {
    execute(url, body) {
      http(url).method("put").postData(body).asString
    }
  }

  def post(url: String, body: String): HttpResult = {
    execute(url, body) {
      http(url).method("post").postData(body).asString
    }
  }
}

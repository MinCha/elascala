package elascala

import elascala.HttpMethod.HttpMethod
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._
import scalaj.http.{Http, HttpResponse}
import HttpMethod._

/**
 * Created by Vayne on 2015-02-18.
 */
object HttpClient {
  val Logger = LoggerFactory.getLogger(classOf[Elascala])
  val Newline = sys.props("line.separator")
  val Tab = "\t"

  def get(url: String): HttpResult = {
    execute(Get, url, "") {
      http(url).asString
    }
  }

  def put(url: String, any: Any): HttpResult = {
    put(url, Json.toJson(any))
  }

  def put(url: String, body: String): HttpResult = {
    execute(Put, url, body) {
      http(url).method("put").postData(body).asString
    }
  }

  def post(url: String, body: String): HttpResult = {
    execute(Post, url, body) {
      http(url).method("post").postData(body).asString
    }
  }

  private def http(url: String) = {
    Http(url).timeout(5000, 5000)
  }

  private def execute(method: HttpMethod, url: Any, body: Any)(f: => HttpResponse[String]): HttpResult = {
    val result = f
    Logger.info("{} Req => {} {}, body: {} {} Res => status: {}, response body: {}",
      List(Newline + Tab, method.toString.toUpperCase, url, body, Newline + Tab, result.code, result.body).asJava.toArray: _*)
    if (result.isSuccess)
      HttpResult(result.code, result.body)
    else
      throw new ElascalaException(result.code, result.body)
  }
}


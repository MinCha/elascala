package elascala

import com.google.gson.Gson

import scala.collection.JavaConverters._
import scalaj.http.Http

/**
 * Created by vayne on 15. 2. 16..
 */
class Elascala(host: String, port: Int) {
  val url = host + ":" + port
  val gson = new Gson

  def select(id: String)(implicit index: ElascalaIndexType): GetResult = {
    HttpClient.get(url + index.uri + "/" + id).to(classOf[GetResult])
  }

  def update(id: String, key: String, value: Any)(implicit index: ElascalaIndexType): PostResult = {
    update(id, (key, value))
  }

  def update(id: String, keyValues: Tuple2[String, Any]*)(implicit index: ElascalaIndexType): PostResult = {
    val wholeUrl = url + index.uri + "/" + id + "/_update"
    val template = """ctx._source.%s = %s"""
    val parameters = for (x <- keyValues) yield {
      x match {
        case s: (String, String) => template.format(x._1, "'" + x._2 + "'") + ";"
        case _ => template.format(x._1, x._2) + ";"
      }
    }
    val body = """{"script" : "%s"}""".format(parameters.mkString)
    val result = HttpClient.post(wholeUrl, body).to(classOf[PostResult])
    require(result.id == id)

    result
  }

  def insert(p: Tuple2[String, Any]*)(implicit index: ElascalaIndexType): PutResult = {
    val result = HttpClient.put(url + index.uri, p.toMap.asJava).to(classOf[PutResult])
    require(result.created, result.toString)
    result
  }
}
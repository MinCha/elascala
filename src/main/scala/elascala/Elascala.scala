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
    val wholeUrl = url + index.uri + "/" + id + "/_update"
    val body = """{"script" : "ctx._source.%s = '%s'"}""".format(key, value.toString)
    val result = HttpClient.post(wholeUrl, body).to(classOf[PostResult])
    require(result.id == id)

    result
  }

  def insert(p: Map[String, String])(implicit index: ElascalaIndexType): PutResult = {
    val result = HttpClient.put(url + index.uri, p.asJava).to(classOf[PutResult])
    require(result.created, result.toString)
    result
  }
}
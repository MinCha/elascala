import com.google.gson.Gson

import scala.collection.JavaConverters._
import scalaj.http.Http

/**
 * Created by vayne on 15. 2. 16..
 */
class Elascala(host: String, port: Int) {
  val url = host + ":" + port

  def select(id: String): Option[Map[String, AnyRef]] = ???

  def update(key: String, value: AnyVal) = ???

  def insert(p: Map[String, String])(implicit index: ElascalaIndexType): String = {
    val json = new Gson().toJson(p.asJava)
    println(json)

    Http(url + index.uri).method("put").postData(json).asString.contentType.get
  }
}

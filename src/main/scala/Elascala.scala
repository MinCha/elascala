import com.google.gson.Gson

import scala.collection.JavaConverters._
import scalaj.http.Http

/**
 * Created by vayne on 15. 2. 16..
 */
class Elascala(host: String, port: Int) {
  val url = host + ":" + port
  val gson = new Gson()

  def select(id: String)(implicit index: ElascalaIndexType): GetResult = {
    gson.fromJson(Http(url + index.uri + "/" + id).asString.body, classOf[GetResult])
  }

  def update(id: String, key: String, value: Any)(implicit index: ElascalaIndexType): PostResult = {
    val result = gson.fromJson(Http(url + index.uri + "/" + id + "/_update")
      .method("post")
      .postData("""{"script" : "ctx._source.%s = '%s'"}""".format(key, value.toString))
      .asString.body, classOf[PostResult])
    require(result.id == id)
    result
  }

  def insert(p: Map[String, String])(implicit index: ElascalaIndexType): PutResult = {
    val result = gson.fromJson(
      Http(url + index.uri)
        .method("put")
        .postData(gson.toJson(p.asJava))
        .asString.body, classOf[PutResult])
    require(result.created, result.toString)
    result
  }
}
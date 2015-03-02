package elascala

import com.google.gson.Gson

import scala.collection.JavaConverters._

/**
 * Created by vayne on 15. 2. 16..
 */
class Elascala(domain: String, port: Int) {
  val host = domain + ":" + port
  val gson = new Gson

  def select(id: String)(implicit index: ElascalaIndexType): GetResult = {
    HttpClient.get(host + index.uri + "/" + id).to(classOf[GetResult])
  }

  def update(id: String, key: String, value: Any)(implicit index: ElascalaIndexType): PostResult = {
    update(id, (key, value))
  }

  def update(id: String, keyValues: (String, Any)*)(implicit index: ElascalaIndexType): PostResult = {
    val json = ESJsonBuilder.update(keyValues:_*).build
    update(id, json)
  }

  def upsert(id: String, keyValues: (String, Any)*)(implicit index: ElascalaIndexType): PostResult = {
    val json = ESJsonBuilder.update(keyValues:_*).upsert(keyValues:_*).build
    update(id, json)
  }

  def update(id: String, json: String)(implicit index: ElascalaIndexType): PostResult = {
    val url = host + index.uri + "/" + id + "/_update"
    val result = HttpClient.post(url, json).to(classOf[PostResult])
    require(result.id == id)

    result
  }

  def insert(p: (String, Any)*)(implicit index: ElascalaIndexType): PutResult = {
    val result = HttpClient.put(host + index.uri, p.toMap.asJava).to(classOf[PutResult])
    require(result.created, result.toString)
    result
  }

  def refresh(index: String): PostResult = {
    HttpClient.post(host + "/" + index + "/_refresh", "").to(classOf[PostResult])
  }
}
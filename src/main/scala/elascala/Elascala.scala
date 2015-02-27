package elascala

import com.google.gson.Gson

import elascala.ESJsonExpression._
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
    val url = host + index.uri + "/" + id + "/_update"
    val parameters = for (x <- keyValues) yield
      UpdateScriptFragment.format(x._1, wrap(x._2)) + ";"
    val body = UpdateTemplate.format(parameters.mkString, "")
    val result = HttpClient.post(url, body).to(classOf[PostResult])
    require(result.id == id)

    result
  }

  def upsert(id: String, keyValues: (String, Any)*)(implicit index: ElascalaIndexType): PostResult = {
    val url = host + index.uri + "/" + id + "/_update"
    val update = for (x <- keyValues) yield UpdateScriptFragment.format(x._1, wrap(x._2)) + ";"
    val upsert = for (x <- keyValues) yield UpdateUpsertFragment.format(x._1, wrap2(x._2)) + ","
    val body = UpdateTemplate.format(update.mkString, upsert.mkString.dropRight(1))
    val result = HttpClient.post(url, body).to(classOf[PostResult])
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
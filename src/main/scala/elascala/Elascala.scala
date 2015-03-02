package elascala

import com.google.gson.Gson

import scala.collection.JavaConverters._

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

  def update(id: String, keyValues: (String, Any)*)(implicit index: ElascalaIndexType): PostResult = {
    val wholeUrl = url + index.uri + "/" + id + "/_update"

    val body = """{"script" : "%s"}""".format(formatUpdateParameters(keyValues: _*))
    val result = HttpClient.post(wholeUrl, body).to(classOf[PostResult])
    require(result.id == id)

    result
  }

  def upsert(id: String, keyValues: (String, Any)*)(implicit index: ElascalaIndexType): PostResult = {
    val wholeUrl = url + index.uri + "/" + id + "/_update"

    val body =
      """{
        |   "script" : "%s",
        |   "upsert" : { %s }
        |}""".stripMargin.format(formatUpdateParameters(keyValues: _*), formatUpsertParameteres(keyValues: _*))

    val result = HttpClient.post(wholeUrl, body).to(classOf[PostResult])
    require(result.id == id)

    result
  }

  def insert(p: (String, Any)*)(implicit index: ElascalaIndexType): PutResult = {
    val result = HttpClient.put(url + index.uri, p.toMap.asJava).to(classOf[PutResult])
    require(result.created, result.toString)
    result
  }

  private def formatUpdateParameters(keyValues: (String, Any)*): String = {
    val template = """ctx._source.%s = %s"""

    val parameters = for (x <- keyValues) yield {
      x match {
        case (k: String, v: String) => template.format(k, "'%s'".format(v))
        case _ => template.format(x._1, x._2)
      }
    }

    parameters.mkString(";")
  }

  private def formatUpsertParameteres(keyValues: (String, Any)*): String = {
    val template = """"%s" : %s"""
    val parameters = for (x <- keyValues) yield {
      x match {
        case (k: String, v: String) => template.format(k, "\"%s\"".format(v))
        case _ => template.format(x._1, x._2)
      }
    }

    parameters.mkString(",")
  }
}
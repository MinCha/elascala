package elascala

import com.google.gson.Gson

/**
 * Created by vayne on 15. 2. 17..
 */
case class PutResult(private val _index: String,
                     private val _type: String,
                     private val _id: String,
                     private val _version: Int,
                     created: Boolean) {
  def index = _index

  def id = _id

  def `type` = _type

  def version = _version
}

case class PostResult(private val _index: String,
                      private val _type: String,
                      private val _id: String,
                      private val _version: Int) {
  def index = _index

  def id = _id

  def `type` = _type

  def version = _version
}

case class GetResult(private val _index: String,
                     private val _type: String,
                     private val _id: String,
                     private val _version: Int,
                     found: Boolean,
                     private val _source: Object) {
  def index = _index

  def id = _id

  def `type` = _type

  def version = _version

  def source[T](classz: Class[T]): T = new Gson().fromJson(_source.toString, classz)
}

case class HttpResult(code: Int, body: String) {
  def to[T](classz: Class[T]): T = new Gson().fromJson(body, classz)

  //TODO Need to improve expression
  def ok = code >= 200 && code < 300
}
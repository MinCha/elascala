package elascala

import com.google.gson.Gson

/**
 * Created by Vayne on 2015-02-18.
 */
object Json {
  def toJson(any: Any) = new Gson().toJson(any)

  def fromJson[T](json:String, classz: Class[T]) = new Gson().fromJson(json, classz)
}

package elascala

/**
 * Created by vayne on 15. 2. 27..
 */
object ESJsonExpression {
  val UpdateTemplate = """{
    "script" : "%s",
    "upsert" : {%s}
  }"""
  val UpdateScriptFragment = """ctx._source.%s = %s"""
  val UpdateUpsertFragment = """"%s" : %s"""

  def wrap2(x: Any): String = {
    wrap(x, """"""")
  }

  def wrap(x: Any): String = {
    wrap(x, """'""")
  }

  def wrap(x: Any, w: String): String = {
    x match {
      case v: String => """%s%s%s""".format(w, v, w)
      case _ => x.toString
    }
  }
}

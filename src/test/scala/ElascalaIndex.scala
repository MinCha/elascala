/**
 * Created by vayne on 15. 2. 16..
 */
case class ElascalaIndex(index: String)

case class ElascalaIndexType(index: String, `type`: String) {
  def uri = "/" + index + "/" + `type`
}
/**
 * Created by vayne on 15. 2. 17..
 */
case class PutPostResult(private val _index: String,
                         private val _type: String,
                         private val _id: String,
                         private val _version: Int,
                         created: Boolean) {
  def index = _index

  def id = _id

  def `type` = _type

  def version = _version
}

case class GetResult[T](private val _index: String,
                        private val _type: String,
                        private val _id: String,
                        private val _version: Int,
                        found: Boolean,
                        private val _source: T) {
  def index = _index

  def id = _id

  def `type` = _type

  def version = _version

  def source = _source
}
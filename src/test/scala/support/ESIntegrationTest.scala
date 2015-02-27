package support

import elascala.ElascalaIndexType
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.node.NodeBuilder._
import org.junit.{After, Before}
import org.scalatest.junit.JUnitSuite

/**
 * Created by vayne on 15. 2. 10..
 */
abstract class ESIntegrationTest extends JUnitSuite {
  var client: Client = _
  val url = "http://localhost:9200"
  implicit val index = ElascalaIndexType("elascala", "persons")

  @Before def startUp() {
    val settings = ImmutableSettings.settingsBuilder
      .put("script.disable_dynamic", false)
      .put("script.default_lang", "groovy")
      .build()
    val node = nodeBuilder.local(true).settings(settings).node
    client = node.client
  }

  @After def endUp() {
    client.close
  }

  case class EnsuringOption[T](optional: Option[T]) {
    def ensure: T = {
      assert(optional.isDefined)
      optional.get
    }
  }

  implicit def ensure[T](optional: Option[T]): EnsuringOption[T] = new EnsuringOption[T](optional)
}

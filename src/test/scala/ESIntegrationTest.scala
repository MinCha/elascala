import org.elasticsearch.client.Client
import org.elasticsearch.node.NodeBuilder._
import org.junit.{After, Before}
import org.scalatest.junit.JUnitSuite

/**
 * Created by vayne on 15. 2. 10..
 */
abstract class ESIntegrationTest extends JUnitSuite {
  var client: Client = _

  @Before def startUp() {
    val node = nodeBuilder.local(true).node;
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

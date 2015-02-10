import org.elasticsearch.client.Client
import org.elasticsearch.node.NodeBuilder._
import org.junit.{After, Before}
import org.scalatest.junit.JUnitSuite
import org.slf4j.LoggerFactory

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
}

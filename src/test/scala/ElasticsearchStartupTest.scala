import org.elasticsearch.action.count.CountRequest
import org.elasticsearch.action.exists.ExistsRequest
import org.elasticsearch.client.Client
import org.junit.{After, Before, Test}
import org.elasticsearch.node.NodeBuilder._
import org.elasticsearch.common.xcontent.XContentFactory._
import org.scalatest.junit.JUnitSuite

/**
 * Created by vayne on 15. 2. 9..
 */
class ElasticsearchStartupTest extends ESIntegrationTest {
  val Index = "some_index"
  val Typez = "some_type"

  @Test def sayHelloToES() {
    val result = client.prepareIndex(Index, Typez).setSource(
      jsonBuilder()
        .startObject()
        .field("user", "kimchy")
        .endObject()).execute.actionGet

    assert(result.getId.isEmpty == false)
    assert(result.getIndex == Index)
    assert(result.getType == Typez)
  }
}
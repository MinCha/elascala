package elascala

import org.junit.Test
import support.ESIntegrationTest

import scala.collection.JavaConverters._

/**
 * Created by Vayne on 2015-02-18.
 */
class HttpTest extends ESIntegrationTest {
  @Test def canPut() {
    val result = HttpClient.put(url + index.uri, Json.toJson(Map("name" -> "elascala").asJava))

    assert(result.code == 201)
    assert(result.body.contains(""""created":true"""))
  }

  @Test def canGet() {
    val result = HttpClient.get("https://github.com")

    assert(result.code == 200)
  }

  @Test(expected = classOf[ElascalaException]) def canKnowNotFound() {
    HttpClient.get("https://github.com/no-existing")
  }
}

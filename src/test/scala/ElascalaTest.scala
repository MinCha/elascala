import org.junit.Test

/**
 * Created by vayne on 15. 2. 16..
 */
class ElascalaTest extends ESIntegrationTest {
  val sut = new Elascala("localhost", 9200)
  implicit val index = ElascalaIndexType("elascala", "persons")

  @Test def canUpdate() {
    val id = sut.insert(Map("name" -> "vayne", "sex" -> "male"))
    print(id)

    sut.update("age", 35)

    val result = sut.select(id).ensure
    assert(result("name") == "vayne")
    assert(result("age") == 35)
  }
}

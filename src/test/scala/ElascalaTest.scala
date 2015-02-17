import org.junit.Test
import support.{Person, ESIntegrationTest}

/**
 * Created by vayne on 15. 2. 16..
 */
class ElascalaTest extends ESIntegrationTest {
  val sut = new Elascala("http://127.0.0.1", 9200)
  implicit val index = ElascalaIndexType("elascala", "persons")

  @Test def canUpdate() {
    val inserted = sut.insert(Map("name" -> "vayne", "sex" -> "male"))

    sut.update(inserted.id, "name", "vayne.q")

    val result = sut.select(inserted.id)
    val person = result.source(classOf[Person])
    assert(person.name == "vayne.q")
    assert(person.sex == "male")
  }
}


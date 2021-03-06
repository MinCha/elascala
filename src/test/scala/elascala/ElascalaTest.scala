package elascala

import java.util.Date

import org.junit.Test
import support.{ESIntegrationTest, Person}

/**
 * Created by vayne on 15. 2. 16..
 */
class ElascalaTest extends ESIntegrationTest {
  val sut = new Elascala("http://127.0.0.1", 9200)

  @Test def canUpdate() {
    val inserted = sut.insert(("name" -> "vayne"), ("sex" -> "male"))

    sut.update(inserted.id, "name", "vayne.q")

    val result = sut.select(inserted.id)
    val person = result.source(classOf[Person])
    assert(person.name == "vayne.q")
    assert(person.sex == "male")
  }

  @Test def canMultipleUpdates() {
    val inserted = sut.insert(("name" -> "vayne"), ("sex" -> "male"), ("age" -> 20))

    sut.update(inserted.id, ("name" -> "vayne.q"), ("sex" -> "female"), ("age" -> 35))

    val result = sut.select(inserted.id)
    val person = result.source(classOf[Person])
    assert(person.name == "vayne.q")
    assert(person.sex == "female")
    assert(person.age == 35)
  }

  @Test(expected = classOf[ElascalaException]) def shouldThrowIWhenNonExistingKey() {
    val inserted = sut.insert(("name" -> "vayne"), ("sex" -> "male"))

    sut.update(inserted.id, "age", "someValue")
  }

  @Test def canUpsert() {
    val inserted = sut.upsert("someId", ("name" -> "vayne"), ("sex" -> "male"), ("age" -> 20))

    val result = sut.select(inserted.id)
    val person = result.source(classOf[Person])
    assert(person.name == "vayne")
    assert(person.sex == "male")
    assert(person.age == 20)
  }

  @Test def canUpsertWhenAlreadyKey() {
    val inserted = sut.insert(("name" -> "vayne"), ("sex" -> "male"))

    sut.upsert(inserted.id, ("name" -> "vayne.q"))

    val result = sut.select(inserted.id)
    val person = result.source(classOf[Person])
    assert(person.name == "vayne.q")
    assert(person.sex == "male")
  }

  @Test def canRefresh() {
    sut.refresh(index.index)
  }
}
package elascala

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit

/**
 * Created by vayne on 15. 2. 16..
 */
class ElascalaIndexTypeTest extends AssertionsForJUnit {
  @Test def canConvertToUri() {
    val sut = ElascalaIndexType("elascala", "persons")

    assert(sut.uri == "/elascala/persons")
  }
}

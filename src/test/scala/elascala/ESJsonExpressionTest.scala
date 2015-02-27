package elascala

import org.junit.Test

/**
 * Created by vayne on 15. 2. 27..
 */
class ESJsonExpressionTest {
  @Test def canWrapToString() {
    assert(ESJsonExpression.wrap("ok") == "'ok'")
    assert(ESJsonExpression.wrap("ok", """"""") == """"ok"""")
    assert(ESJsonExpression.wrap2("ok") == """"ok"""")
  }
}

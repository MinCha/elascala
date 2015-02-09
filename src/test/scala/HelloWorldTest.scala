import org.junit.Test

/**
 * Created by vayne on 15. 2. 9..
 */
class HelloWorldTest {
  val sut = new HelloWorld

  @Test def sayHello() {
    assert(sut.hello == "hello")
  }
}

package example

// https://gist.github.com/paulp/4692921
object Hello extends App {
  Test.f1()
  Test.f2()
  Test.f3()
}

object Test {
  // Observe that x.companion is statically typed such that foo is callable
  def f1() = {
    val x = new Foo

    println(x)                // Foo instance
    println(x.companion)      // Foo companion
    println(x.companion.foo)  // I'm foo!
  }

  // Observe that we now have a static handle on the case class factory
  def f2() = {
    val y = Bippy(5)

    println(y)                      // Bippy(5)
    println(y.companion)            // Bippy
    println(y.companion.apply(10))  // Bippy(10)
  }

  def f3() = {
    val y = new Bar

    //  println(y.companion) // does not compile
  }
}

class Foo {
  override def toString = "Foo instance"
}
object Foo {
  def foo = "I'm foo!"
  override def toString = "Foo companion"
}

case class Bippy(x: Int)

class Bar {
  override def toString = "Bar instance"
}

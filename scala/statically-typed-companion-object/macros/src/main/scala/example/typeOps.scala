import scala.language.experimental.macros
import scala.language.implicitConversions
import scala.reflect.macros.whitebox.Context

// https://gist.github.com/paulp/4692921
package example {
  object typeOps {
    def companionImpl[T](c: Context) = {
      import c.universe._

      val CompanionOpsClass = typeOf[CompanionOps[_]].typeSymbol
      val clazzType         = c.prefix.actualType match {
        case TypeRef(_, CompanionOpsClass, arg :: Nil) => arg
        case tp                                        => c.abort(c.enclosingPosition, s"Unexpected prefix: $tp/${tp.getClass}")
      }
      val companion = clazzType.typeSymbol.companion match {
        case NoSymbol => c.abort(c.enclosingPosition, s"Instance of $clazzType has no companion object")
        case sym      => sym
      }
      def make[U: c.WeakTypeTag] = c.Expr[U](internal.gen.mkAttributedRef(companion))

      make(c.WeakTypeTag(companion.typeSignature))
    }
  }
}

package object example {
  implicit class CompanionOps[T](val clazz: T) {
    def companion: AnyRef = macro typeOps.companionImpl[T]
  }
}

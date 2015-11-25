##scala-pools

[API Docs] (http://beezee.github.io/scala-pools/#package)

Provides a small functional interface to Apache Commons Pool.

Specifically deals with GenericObjectPool.

```scala
import bz.syntax.pool._
import scala.util.Random
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class Foo(label: String)

// Create a pool of max 200 Foo instances
val p = { () => Foo(Random.nextString(4)) }.pool(200)

// Print the label of a Foo from the pool, returning to the pool when done
p.map(println(x => x.label))

/* Print the label of a Foo from the pool asynchronously, and
   return the Foo to the pool on completion.
   Be very sure that your pool is sized generously above your
   max concurrency or you will deadlock. */
p.mapAsync { (x, done) => Future { println(x.label) }.onComplete(done()) }
```

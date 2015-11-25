package bz.syntax

import bz.Pools
import org.apache.commons.pool2.impl.GenericObjectPool

object pool {

  /**
   * Enhancements for () => A
   */
  implicit class ConstructorOps[A](c: () => A) {
    /**
     * Convert this () => A to a pool with the specified
     * max size
     */
    def pool(size: Int): GenericObjectPool[A] =
      Pools.make(c)(size)
  }

  /**
   * Enhancements for GenericObjectPool
   */
  implicit class GenericObjectPoolOps[A](p: GenericObjectPool[A]) {
    /**
     * Run the given fn: A => B on this GenericObjectPool[A]
     * borrowing the A from the pool and returning it after
     * function execution completes
     */
    def map[B](fn: A => B): B = {
      val o = p.borrowObject()
      val r = fn(o)
      p.returnObject(o)
      r
    }

    type Cont = () => Unit
    /**
     * Run the given fn: (A, () => Unit) => B on this
     * GenericObjectPool[A], borrowing the A from the pool.
     *
     * The second parameter passed to the given fn will
     * return the A to the pool when called.
     * Note that if you do not call this function, or
     * if your concurrency is not capped well within
     * your max pool size, you will very likely deadlock.
     */
    def mapAsync[B](fn: (A, Cont) => B): B = {
      val o = p.borrowObject()
      val c = { () => p.returnObject(o) }
      fn(o, c)
    }
  }
}

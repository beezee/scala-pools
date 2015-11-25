package bz

import org.apache.commons.pool2.{BasePooledObjectFactory, PooledObject}
import org.apache.commons.pool2.impl.DefaultPooledObject
import org.apache.commons.pool2.impl.{GenericObjectPoolConfig, GenericObjectPool}

object Pools {

  /**
   * Returns a BasePooledObjectFactory based on the given () => A
   * constructor
   */
  def factory[A](c: () => A): BasePooledObjectFactory[A] =
    new BasePooledObjectFactory[A] {
      override def wrap(t: A): PooledObject[A] = new DefaultPooledObject(t)
      override def create(): A = c()
    }

  /**
   * Returns a new GenericObjectPool based on the given () => A
   * constructor and the specified max pool size
   */
  def make[A](c: () => A)(max: Int): GenericObjectPool[A] = {
    val conf = new GenericObjectPoolConfig
    conf.setMaxTotal(max)
    new GenericObjectPool(factory(c), conf)
  }
}

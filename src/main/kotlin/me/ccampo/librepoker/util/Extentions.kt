package me.ccampo.librepoker.util

import java.util.*


/**
 * @author Chris Campo
 */
fun <E> List<E>.random() = this[Random().nextInt(this.lastIndex)]
val <T> List<T>.tail: List<T> get() = drop(1)
val <T> List<T>.head: T get() = first()

fun <T> List<T>.combinations(n: Int): List<List<T>> {
  fun <T> combinations(l: List<T>, n: Int): List<List<T>> {
    val result = mutableListOf<List<T>>()
    when {
      n > l.size -> throw IllegalArgumentException("Value n must be less than or equal to the list size")
      n == l.size -> result.add(l)
      n == 0 -> result.add(emptyList())
      n < l.size -> result.addAll(combinations(l.tail, n) + combinations(l.tail, n - 1).map { it + l.head })
    }
    return result
  }
  return combinations(this, n)
}

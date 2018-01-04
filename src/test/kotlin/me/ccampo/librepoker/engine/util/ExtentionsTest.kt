package me.ccampo.librepoker.engine.util

import org.junit.Test


/**
 * @author Chris Campo
 */
class ExtentionsTest {

  @Test
  fun testCombinations() {
    val x = listOf('a', 'b', 'c', 'd')
    println(x.combinations(4))
  }
}

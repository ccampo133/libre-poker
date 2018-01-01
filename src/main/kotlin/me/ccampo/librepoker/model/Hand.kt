package me.ccampo.librepoker.model

import me.ccampo.librepoker.util.HandTypeAndScore
import me.ccampo.librepoker.util.evaluate

/**
 * @author Chris Campo
 */
data class Hand(val cards: List<Card>) : Comparable<Hand> {
  init {
    require(cards.distinct().size == cards.size)
  }

  val type: HandType by lazy { evaluate(this) }
  val score: Int by lazy { me.ccampo.librepoker.util.score(this) }
  val scoreString: String by lazy { Integer.toHexString(score) }

  fun toShortString() = cards.map { it.toShortString() }.toString()

  override fun compareTo(other: Hand): Int {
    return this.score.compareTo(other.score)
  }

  //TODO: description of hand (e.g. "Aces full of Kings") -ccampo 2017-12-30
}

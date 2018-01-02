package me.ccampo.librepoker.model

import me.ccampo.librepoker.util.evaluate

/**
 * @author Chris Campo
 */
data class Hand(val cards: List<Card>) : Comparable<Hand> {
  companion object {
    const val NUM_CARDS = 5
  }

  init {
    require(cards.distinct().size == cards.size && cards.size == NUM_CARDS)
  }

  //TODO: property for description of hand (e.g. "Aces full of Kings") -ccampo 2017-12-30
  val score: HandScore by lazy { evaluate(this) }

  fun toShortString() = cards.map { it.toShortString() }.toString()

  fun toUnicodeShortString() = cards.map { it.toUnicodeShortString() }.toString()

  fun isFlush() = this.cards.distinctBy { it.suit }.size == 1

  fun isStraight(): Boolean {
    val sorted = this.cards.sortedBy { it.face.weight }
    val increasing = { i: Int -> sorted[0].face.weight + i == sorted[i].face.weight }
    if (increasing(sorted.size - 1)) return true
    // Ace can be either high or low
    return sorted.last().face == Face.ACE && increasing(sorted.size - 2)
  }

  override fun compareTo(other: Hand): Int = this.score.compareTo(other.score)
}

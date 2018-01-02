package me.ccampo.librepoker.model

/**
 * @author Chris Campo
 */
data class Pocket(val cards: List<Card>) {
  companion object {
    const val MAX_NUM_CARDS = 2
  }

  init {
    require(cards.distinct().size == cards.size && cards.size <= MAX_NUM_CARDS)
  }

  fun toUnicodeShortString() = cards.map { it.toUnicodeShortString() }.toString()
}

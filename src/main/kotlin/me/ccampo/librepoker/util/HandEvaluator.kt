package me.ccampo.librepoker.util

import me.ccampo.librepoker.model.Face
import me.ccampo.librepoker.model.Hand
import me.ccampo.librepoker.model.HandType

/**
 * @author Chris Campo
 */
fun evaluate(hand: Hand): HandType {
  fun eval(hand: Hand): HandType {
    fun Hand.isFlush() = this.cards.distinctBy { it.suit }.size == 1
    fun Hand.isStraight(): Boolean {
      val sorted = this.cards.sortedBy { it.face.weight }
      val increasing = { i: Int -> sorted[0].face.weight + i == sorted[i].face.weight }
      if (increasing(sorted.size - 1)) return true
      // Ace can be either high or low
      return sorted.last().face == Face.ACE && increasing(sorted.size - 2)
    }

    val groups = hand.cards.groupBy { it.face }
    return when (groups.size) {
      2 -> if (groups.any { it.value.size == 4 }) HandType.FOUR_OF_A_KIND else HandType.FULL_HOUSE
      3 -> if (groups.any { it.value.size == 3 }) HandType.THREE_OF_A_KIND else HandType.TWO_PAIR
      4 -> HandType.PAIR
      else -> {
        val flush = hand.isFlush()
        val straight = hand.isStraight()
        when {
          flush && straight -> if (hand.cards.sumBy { it.face.weight } == 60) {
            HandType.ROYAL_FLUSH
          } else {
            HandType.STRAIGHT_FLUSH
          }
          flush -> HandType.FLUSH
          straight -> HandType.STRAIGHT
          else -> HandType.HIGH_CARD
        }
      }
    }
  }

  return hand.cards.combinations(5).map { eval(Hand(it)) }.maxBy { it.rank }!!
}

/*
 * First sort the cards in descending order, and then put any pairs/sets/quads
 * of cards in front. Example, A K 4 J 4 becomes 4 4 J K A
 *
 * See: https://stackoverflow.com/questions/42380183/algorithm-to-give-a-value-to-a-5-card-poker-hand
 */
fun score(hand: Hand): Int {
  return hand.cards
    .sortedByDescending { it.face.weight }
    .groupBy { it.face }
    .toSortedMap(compareByDescending { it.weight })
    .values
    .sortedByDescending { it.size }
    .flatten()
    .fold(hand.type.rank, { score, card -> (score shl 4) + card.face.weight })
}

data class HandTypeAndScore(val type: HandType, val score: Int)

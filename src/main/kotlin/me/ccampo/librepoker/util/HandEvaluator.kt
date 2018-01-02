package me.ccampo.librepoker.util

import me.ccampo.librepoker.model.Card
import me.ccampo.librepoker.model.Hand
import me.ccampo.librepoker.model.HandScore
import me.ccampo.librepoker.model.HandType

/**
 * @author Chris Campo
 */

/**
 * First sort the cards in descending order, and then put any pairs/sets/quads
 * of cards in front. Example, A K 4 J 4 becomes 4 4 J K A
 *
 * See: https://stackoverflow.com/questions/42380183/algorithm-to-give-a-value-to-a-5-card-poker-hand
 */
fun evaluate(hand: Hand): HandScore {
  val groups = hand.cards
    .sortedByDescending { it.face.weight }
    .groupBy { it.face }
    .toSortedMap(compareByDescending { it.weight })

  val type = when (groups.size) {
    2 -> if (groups.any { it.value.size == 4 }) HandType.FOUR_OF_A_KIND else HandType.FULL_HOUSE
    3 -> if (groups.any { it.value.size == 3 }) HandType.THREE_OF_A_KIND else HandType.TWO_PAIR
    4 -> HandType.PAIR
    else -> {
      val flush = hand.isFlush()
      val straight = hand.isStraight()
      when {
        flush && straight -> if (hand.cards.sumBy { it.face.weight } == 60) HandType.ROYAL_FLUSH else HandType.STRAIGHT_FLUSH
        flush -> HandType.FLUSH
        straight -> HandType.STRAIGHT
        else -> HandType.HIGH_CARD
      }
    }
  }

  val score = groups.values
    .sortedByDescending { it.size }
    .flatten()
    .fold(type.rank, { score, card -> (score shl 4) + card.face.weight })

  return HandScore(type, score)
}

fun getBestHand(cards: List<Card>): Hand {
  return cards.combinations(5).map { Hand(it) }.maxBy { it.score.score }!!
}

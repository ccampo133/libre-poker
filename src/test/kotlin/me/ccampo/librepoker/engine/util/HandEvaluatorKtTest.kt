package me.ccampo.librepoker.engine.util

import me.ccampo.librepoker.engine.model.Card
import me.ccampo.librepoker.engine.model.Hand
import me.ccampo.librepoker.engine.model.HandType
import me.ccampo.librepoker.engine.model.Pocket
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Chris Campo
 */
class HandEvaluatorKtTest {

  @Test
  fun `evaluate`() {
    val hand = Hand(
      listOf(
        Card.QUEEN_OF_HEARTS,
        Card.QUEEN_OF_DIAMONDS,
        Card.ACE_OF_SPADES, Card.JACK_OF_CLUBS,
        Card.TEN_OF_HEARTS
      )
    )

    assertEquals(hand.score.type, HandType.PAIR)
  }

  @Test
  fun `get best hand`() {
    val pocket = Pocket(
      listOf(
        Card.QUEEN_OF_HEARTS, Card.TEN_OF_SPADES
      )
    )
    val board = listOf(
      Card.DEUCE_OF_DIAMONDS, Card.SIX_OF_SPADES, Card.QUEEN_OF_CLUBS, Card.FIVE_OF_HEARTS, Card.FIVE_OF_SPADES
    )

    val hand = getBestHand(pocket.cards + board)
    val expected = Hand(
      listOf(
        Card.QUEEN_OF_HEARTS, Card.QUEEN_OF_CLUBS,
        Card.FIVE_OF_HEARTS, Card.FIVE_OF_DIAMONDS,
        Card.TEN_OF_SPADES
      )
    )

    assertEquals(expected.score.score, hand.score.score)
    assertEquals(expected.score.type, hand.score.type)
  }
}

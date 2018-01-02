package me.ccampo.librepoker.model

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * @author Chris Campo
 */
class HandTest {

  @Test
  fun `compare same pair and same kicker`() {
    val h1 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.KING_OF_SPADES, Card.FOUR_OF_HEARTS, Card.FIVE_OF_CLUBS)
    )

    val h2 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.KING_OF_CLUBS, Card.FOUR_OF_SPADES, Card.FIVE_OF_HEARTS)
    )

    assertEquals(h1.score.type, h2.score.type)
    assertTrue { h1.compareTo(h2) == 0 }
  }

  @Test
  fun `compare same pairs with kicker`() {
    val h1 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.KING_OF_SPADES, Card.FOUR_OF_HEARTS, Card.FIVE_OF_CLUBS)
    )

    val h2 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.QUEEN_OF_SPADES, Card.SIX_OF_CLUBS, Card.FIVE_OF_CLUBS)
    )

    assertEquals(h1.score.type, h2.score.type)
    assertTrue { h1 > h2 }
  }

  @Test
  fun `compare pair 2s and 3s`() {
    val h1 = Hand(
      listOf(Card.DEUCE_OF_CLUBS, Card.DEUCE_OF_SPADES, Card.KING_OF_SPADES, Card.QUEEN_OF_DIAMONDS, Card.ACE_OF_CLUBS)
    )

    val h2 = Hand(
      listOf(Card.DEUCE_OF_CLUBS, Card.THREE_OF_SPADES, Card.THREE_OF_CLUBS, Card.FOUR_OF_DIAMONDS, Card.SIX_OF_HEARTS)
    )

    assertEquals(h1.score.type, h2.score.type)
    assertTrue { h1 < h2 }
  }

  @Test
  fun `compare quads`() {
    val h1 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.ACE_OF_HEARTS, Card.FIVE_OF_CLUBS)
    )

    val h2 = Hand(
      listOf(Card.KING_OF_SPADES, Card.KING_OF_DIAMONDS, Card.KING_OF_CLUBS, Card.KING_OF_HEARTS, Card.FOUR_OF_HEARTS)
    )

    assertEquals(h1.score.type, h2.score.type)
    assertTrue { h1 > h2 }
  }

  @Test
  fun `compare quads with kicker`() {
    val h1 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.ACE_OF_HEARTS, Card.FIVE_OF_CLUBS)
    )

    val h2 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.ACE_OF_HEARTS, Card.SIX_OF_CLUBS)
    )

    assertEquals(h1.score.type, h2.score.type)
    assertTrue { h1 < h2 }
  }

  @Test
  fun `compare identical quads, same kicker`() {
    val h1 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.ACE_OF_HEARTS, Card.SIX_OF_HEARTS)
    )

    val h2 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.ACE_OF_HEARTS, Card.SIX_OF_CLUBS)
    )

    assertEquals(h1.score.type, h2.score.type)
    assertTrue { h1.compareTo(h2) == 0 }
  }

  @Test
  fun `compare full house, bigger trips should win`() {
    val h1 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.FOUR_OF_SPADES, Card.FOUR_OF_CLUBS)
    )

    val h2 = Hand(
      listOf(Card.KING_OF_SPADES, Card.KING_OF_DIAMONDS, Card.KING_OF_CLUBS, Card.FIVE_OF_SPADES, Card.FIVE_OF_CLUBS)
    )

    assertEquals(h1.score.type, h2.score.type)
    assertTrue { h1 > h2 }
  }

  @Test
  fun `compare full house, bigger pair should win`() {
    val h1 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.FOUR_OF_SPADES, Card.FOUR_OF_CLUBS)
    )

    val h2 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.FIVE_OF_SPADES, Card.FIVE_OF_CLUBS)
    )

    assertEquals(h1.score.type, h2.score.type)
    assertTrue { h1 < h2 }
  }

  @Test
  fun `compare identical full houses`() {
    val h1 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.FOUR_OF_SPADES, Card.FOUR_OF_CLUBS)
    )

    val h2 = Hand(
      listOf(Card.ACE_OF_SPADES, Card.ACE_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.FOUR_OF_SPADES, Card.FOUR_OF_DIAMONDS)
    )

    assertEquals(h1.score.type, h2.score.type)
    assertTrue { h1.compareTo(h2) == 0 }
  }

  @Test
  fun `compare two pair`() {
    val h1 = Hand(
      listOf(Card.KING_OF_CLUBS, Card.KING_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.FOUR_OF_SPADES, Card.FOUR_OF_CLUBS)
    )

    val h2 = Hand(
      listOf(Card.TEN_OF_CLUBS, Card.TEN_OF_DIAMONDS, Card.ACE_OF_CLUBS, Card.FOUR_OF_SPADES, Card.FOUR_OF_CLUBS)
    )

    assertNotNull(h1.score)
    assertNotNull(h2.score)

    assertEquals(h1.score.type, h2.score.type)
    assertTrue { h1 > h2 }
  }
}

package me.ccampo.librepoker.model

import me.ccampo.librepoker.util.random

/**
 * @author Chris Campo
 */
enum class Suit {
  CLUBS, DIAMONDS, HEARTS, SPADES;

  val char = this.name.first().toLowerCase()

  val unicodeChar: Char
    get() = when {
      this == CLUBS -> '\u2667'
      this == DIAMONDS -> '\u2662'
      this == HEARTS -> '\u2661'
      this == SPADES -> '\u2664'
      else -> throw IllegalStateException() // This is impossible
    }
}

enum class Face(val weight: Int) {
  DEUCE(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  TEN(10),
  JACK(11),
  QUEEN(12),
  KING(13),
  ACE(14);

  val char: Char
    get() = when {
      this == TEN -> 'T'
      this == JACK -> 'J'
      this == QUEEN -> 'Q'
      this == KING -> 'K'
      this == ACE -> 'A'
      else -> Character.forDigit(this.weight, 10)
    }
}

enum class HandType(val rank: Int) {
  ROYAL_FLUSH(9),
  STRAIGHT_FLUSH(8),
  FOUR_OF_A_KIND(7),
  FULL_HOUSE(6),
  FLUSH(5),
  STRAIGHT(4),
  THREE_OF_A_KIND(3),
  TWO_PAIR(2),
  PAIR(1),
  HIGH_CARD(0)
}

data class HandScore(val type: HandType, val score: Int) : Comparable<HandScore> {
  override fun compareTo(other: HandScore) = score.compareTo(other.score)
}

data class Card(val face: Face, val suit: Suit) {
  companion object {
    // Clubs
    val DEUCE_OF_CLUBS = Card(Face.DEUCE, Suit.CLUBS)
    val THREE_OF_CLUBS = Card(Face.THREE, Suit.CLUBS)
    val FOUR_OF_CLUBS = Card(Face.FOUR, Suit.CLUBS)
    val FIVE_OF_CLUBS = Card(Face.FIVE, Suit.CLUBS)
    val SIX_OF_CLUBS = Card(Face.SIX, Suit.CLUBS)
    val SEVEN_OF_CLUBS = Card(Face.SEVEN, Suit.CLUBS)
    val EIGHT_OF_CLUBS = Card(Face.EIGHT, Suit.CLUBS)
    val NINE_OF_CLUBS = Card(Face.NINE, Suit.CLUBS)
    val TEN_OF_CLUBS = Card(Face.TEN, Suit.CLUBS)
    val JACK_OF_CLUBS = Card(Face.JACK, Suit.CLUBS)
    val QUEEN_OF_CLUBS = Card(Face.QUEEN, Suit.CLUBS)
    val KING_OF_CLUBS = Card(Face.KING, Suit.CLUBS)
    val ACE_OF_CLUBS = Card(Face.ACE, Suit.CLUBS)

    // Diamonds
    val DEUCE_OF_DIAMONDS = Card(Face.DEUCE, Suit.DIAMONDS)
    val THREE_OF_DIAMONDS = Card(Face.THREE, Suit.DIAMONDS)
    val FOUR_OF_DIAMONDS = Card(Face.FOUR, Suit.DIAMONDS)
    val FIVE_OF_DIAMONDS = Card(Face.FIVE, Suit.DIAMONDS)
    val SIX_OF_DIAMONDS = Card(Face.SIX, Suit.DIAMONDS)
    val SEVEN_OF_DIAMONDS = Card(Face.SEVEN, Suit.DIAMONDS)
    val EIGHT_OF_DIAMONDS = Card(Face.EIGHT, Suit.DIAMONDS)
    val NINE_OF_DIAMONDS = Card(Face.NINE, Suit.DIAMONDS)
    val TEN_OF_DIAMONDS = Card(Face.TEN, Suit.DIAMONDS)
    val JACK_OF_DIAMONDS = Card(Face.JACK, Suit.DIAMONDS)
    val QUEEN_OF_DIAMONDS = Card(Face.QUEEN, Suit.DIAMONDS)
    val KING_OF_DIAMONDS = Card(Face.KING, Suit.DIAMONDS)
    val ACE_OF_DIAMONDS = Card(Face.ACE, Suit.DIAMONDS)

    // Hearts
    val DEUCE_OF_HEARTS = Card(Face.DEUCE, Suit.HEARTS)
    val THREE_OF_HEARTS = Card(Face.THREE, Suit.HEARTS)
    val FOUR_OF_HEARTS = Card(Face.FOUR, Suit.HEARTS)
    val FIVE_OF_HEARTS = Card(Face.FIVE, Suit.HEARTS)
    val SIX_OF_HEARTS = Card(Face.SIX, Suit.HEARTS)
    val SEVEN_OF_HEARTS = Card(Face.SEVEN, Suit.HEARTS)
    val EIGHT_OF_HEARTS = Card(Face.EIGHT, Suit.HEARTS)
    val NINE_OF_HEARTS = Card(Face.NINE, Suit.HEARTS)
    val TEN_OF_HEARTS = Card(Face.TEN, Suit.HEARTS)
    val JACK_OF_HEARTS = Card(Face.JACK, Suit.HEARTS)
    val QUEEN_OF_HEARTS = Card(Face.QUEEN, Suit.HEARTS)
    val KING_OF_HEARTS = Card(Face.KING, Suit.HEARTS)
    val ACE_OF_HEARTS = Card(Face.ACE, Suit.HEARTS)

    // SPADES
    val DEUCE_OF_SPADES = Card(Face.DEUCE, Suit.SPADES)
    val THREE_OF_SPADES = Card(Face.THREE, Suit.SPADES)
    val FOUR_OF_SPADES = Card(Face.FOUR, Suit.SPADES)
    val FIVE_OF_SPADES = Card(Face.FIVE, Suit.SPADES)
    val SIX_OF_SPADES = Card(Face.SIX, Suit.SPADES)
    val SEVEN_OF_SPADES = Card(Face.SEVEN, Suit.SPADES)
    val EIGHT_OF_SPADES = Card(Face.EIGHT, Suit.SPADES)
    val NINE_OF_SPADES = Card(Face.NINE, Suit.SPADES)
    val TEN_OF_SPADES = Card(Face.TEN, Suit.SPADES)
    val JACK_OF_SPADES = Card(Face.JACK, Suit.SPADES)
    val QUEEN_OF_SPADES = Card(Face.QUEEN, Suit.SPADES)
    val KING_OF_SPADES = Card(Face.KING, Suit.SPADES)
    val ACE_OF_SPADES = Card(Face.ACE, Suit.SPADES)
  }

  fun toShortString() = "${this.face.char}${this.suit.char}"
  fun toUnicodeShortString() = "${this.face.char}${this.suit.unicodeChar}"
}

class Deck {
  var cards = Suit.values().flatMap { suit -> Face.values().map { face -> Card(face, suit) } }.shuffled()
    private set

  fun draw(): Card {
    return when {
      cards.isEmpty() -> throw IllegalStateException("Deck is empty")
      else -> {
        val card = cards.random()
        cards -= card
        card
      }
    }
  }
}



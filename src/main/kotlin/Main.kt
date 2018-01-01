import me.ccampo.librepoker.model.Card
import me.ccampo.librepoker.model.Deck
import me.ccampo.librepoker.model.Hand

fun main(args: Array<String>) {
  val numPlayers = 4
  val deck = Deck()
  var muck = listOf<Card>()
  var hands = listOf<Hand>()
  var communityCards = listOf<Card>()

  (0 until numPlayers).forEach { hands += Hand(emptyList()) }

  // Deal player their hands
  (0 until 2).forEach {
    hands = hands.map { it.copy(cards = it.cards + deck.draw()) }
  }

  // Flop
  muck += deck.draw()
  communityCards += listOf(deck.draw(), deck.draw(), deck.draw())
  println("Flop")
  printCards(communityCards)
  println()

  // Turn
  muck += deck.draw()
  communityCards += deck.draw()
  println("Turn")
  printCards(communityCards)
  println()

  // River
  muck += deck.draw()
  communityCards += deck.draw()
  println("River")
  printCards(communityCards)
  println()

  hands.map { it.copy(cards = it.cards + communityCards) }
    .forEach { println("${it.toShortString()} - ${it.type}") }
}


fun printCards(cards: List<Card>) = println(cards.map { it.toShortString() })

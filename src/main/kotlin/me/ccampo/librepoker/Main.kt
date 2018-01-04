package me.ccampo.librepoker

import me.ccampo.librepoker.engine.model.Card
import me.ccampo.librepoker.engine.model.Deck
import me.ccampo.librepoker.engine.model.Hand
import me.ccampo.librepoker.engine.model.Pocket
import me.ccampo.librepoker.engine.util.getBestHand

fun main(args: Array<String>) {
  val numPlayers = 4
  val deck = Deck()
  var muck = listOf<Card>()
  var pockets = listOf<Pocket>()
  var communityCards = listOf<Card>()

  (0 until numPlayers).forEach { pockets += Pocket(emptyList()) }

  // Deal player their cards
  (0 until 2).forEach {
    pockets = pockets.map { it.copy(cards = it.cards + deck.draw()) }
  }

  // Flop
  muck += deck.draw()
  val flop = listOf(deck.draw(), deck.draw(), deck.draw())
  communityCards += flop
  println("Flop")
  printCards(flop)
  println()

  // Turn
  muck += deck.draw()
  val turn = deck.draw()
  communityCards += turn
  println("Turn")
  printCards(listOf(turn))
  println()

  // River
  muck += deck.draw()
  val river = deck.draw()
  communityCards += river
  println("River")
  printCards(listOf(river))
  println()

  // Final board
  println("Board")
  printCards(communityCards)
  println()

  var hands = emptyList<Hand>()
  for ((i, pocket) in pockets.withIndex()) {
    val hand = getBestHand(pocket.cards + communityCards)
    println("Player $i - ${pocket.toUnicodeShortString()}: ${hand.toUnicodeShortString()} - ${hand.score.type}")
    hands += hand
  }
  println()

  val (winner, winningHand) = hands.withIndex().maxBy { it.value.score }!!

  if (hands.count { it.score.score == winningHand.score.score } > 1) {
    println("Tie")
    hands.withIndex()
      .filter { it.value.score.score == winningHand.score.score }
      .forEach { println("Player ${it.index} wins! ${it.value.toUnicodeShortString()} - ${it.value.score.type}") }
  } else {
    println("Player $winner wins! ${winningHand.toUnicodeShortString()} - ${winningHand.score.type}")
  }
}


fun printCards(cards: List<Card>) = println(cards.map { it.toUnicodeShortString() })

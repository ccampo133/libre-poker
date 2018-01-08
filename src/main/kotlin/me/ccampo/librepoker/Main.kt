package me.ccampo.librepoker

import me.ccampo.librepoker.engine.model.*
import me.ccampo.librepoker.engine.util.sort

fun main(args: Array<String>) {
  println("Your name?")
  val user = Player(readLine()!!.capitalize(), chips = 5000)
  val players = listOf(user, Player("Ana", chips = 5000), Player("Evelyn", chips = 5000))

  println()
  println("${players.size} players! ${players.joinToString { "${it.name} (chips: ${it.chips})" }}")
  println()

  val game = HoldemGame(
    players = players,
    bigBlind = 100,
    onBet = betWithUser(user),
    onPreFlop = ::printPreFlop,
    onFlop = ::printFlop,
    onTurn = ::printTurn,
    onRiver = ::printRiver,
    onShowdown = ::printBoard
  )

  val winners = game.deal()

  println("The pot: ${game.pot}")
  println()

  println("Winners")
  if (winners.size > 1) {
    println("${winners.size}-way split pot")
  }

  winners.forEach {
    val share = game.pot / winners.size
    println("Share: $share")
    println(
      "${it.player.name} (chips: ${it.player.chips + share}) - ${sort(
        it.hand.cards
      ).toUnicodeShortString()} ${it.hand.score.type}"
    )
  }
}

fun printPreFlop(game: HoldemGame) {
  println("Pre-flop")
  println()
}

fun printFlop(game: HoldemGame) {
  println("Flop")
  println(game.flop?.toUnicodeShortString())
  println()
}

fun printTurn(game: HoldemGame) {
  println("Turn")
  println(listOf(game.turn?.toUnicodeShortString()))
  println()
}

fun printRiver(game: HoldemGame) {
  println("River")
  println(listOf(game.river?.toUnicodeShortString()))
  println()
}

fun printBoard(game: HoldemGame) {
  println("Board")
  println(game.communityCards.toUnicodeShortString())
  println()
  println("Showdown")
  game.players.forEach { println("${it.pocket.toUnicodeShortString()} - ${it.name}") }
  println()
}

fun betWithUser(user: Player): (HoldemGame, Player, Set<BetType>, Int) -> Bet {
  return { game, player, types, minBet ->
    if (user.name == player.name) {
      if (game.communityCards.isNotEmpty()) {
        println("The board: ${game.communityCards.toUnicodeShortString()}")
      }
      println("Your pocket: ${player.pocket.cards.toUnicodeShortString()}")
      println("The pot: ${game.pot}")
      println("Bet amount? (chips: ${player.chips})")
      val bet = Bet(BetType.RAISE, readLine()!!.toInt())
      println()
      println("${player.name} bets ${bet.value} (chips: ${player.chips - bet.value})")
      println()
      bet
    } else {
      bet(game, player, types, minBet)
    }
  }
}

fun bet(game: HoldemGame, player: Player, types: Set<BetType>, min: Int): Bet {
  println("Pot: ${game.pot}")
  val bet = Bet(BetType.RAISE, if (min == 0) game.bigBlind * 2 else min * 2)
  println("${player.name} bets ${bet.value} (chips: ${player.chips - bet.value})")
  println()
  return bet
}

fun List<Card>.toUnicodeShortString() = this.map { it.toUnicodeShortString() }

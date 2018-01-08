package me.ccampo.librepoker.engine.model

import me.ccampo.librepoker.engine.util.getBestHand
import sun.audio.AudioPlayer.player
import kotlin.math.min

/**
 * @author Chris Campo
 */
class HoldemGame(
  players: List<Player>,
  val bigBlind: Int,
  val deck: Deck = Deck(),
  val onBet: (HoldemGame, Player, Set<BetType>, Int) -> Bet,
  val onPreFlop: (HoldemGame) -> Unit = {},
  val onFlop: (HoldemGame) -> Unit = {},
  val onTurn: (HoldemGame) -> Unit = {},
  val onRiver: (HoldemGame) -> Unit = {},
  val onShowdown: (HoldemGame) -> Unit = {}
) {

  var players: List<Player> = players
    private set

  var pot: Int = 0
    private set

  var currentRound: BettingRound? = BettingRound.PRE_FLOP
    private set

  var communityCards = listOf<Card>()
  var muck = listOf<Card>()

  val smallBlind = bigBlind / 2
  var flop: List<Card>? = null
  var turn: Card? = null
  var river: Card? = null

  var completed = false
    private set

  fun deal(): List<PlayerAndHand> {
    tailrec fun deal(round: BettingRound?): List<PlayerAndHand> {
      if (round != null) {
        when (round) {
          BettingRound.PRE_FLOP -> preFlop()
          BettingRound.FLOP -> flop()
          BettingRound.TURN -> turn()
          BettingRound.RIVER -> river()
        }

        //TODO: properly handle betting here -ccampo 2018-01-07
        var minBet = 0
        players = players.map { player ->
          val types = if (minBet == 0) {
            setOf(BetType.BET, BetType.CHECK, BetType.RAISE)
          } else {
            setOf(BetType.CALL, BetType.RAISE)
          }
          val bet = onBet(this, player, types, minBet)
          minBet = bet.value
          pot += bet.value
          player.copy(chips = player.chips - bet.value)
        }

        currentRound = round.next()
        return deal(currentRound)
      }

      onShowdown(this)
      val hands = players.map { PlayerAndHand(it, getBestHand(it.pocket.cards + communityCards)) }
      val winner = hands.maxBy { it.hand.score.score }!!
      return if (hands.count { it.hand.score.score == winner.hand.score.score } > 1) {
        hands.filter { it.hand.score.score == winner.hand.score.score }
      } else {
        listOf(winner)
      }
    }

    return if (!completed) {
      completed = true
      deal(BettingRound.PRE_FLOP)
    } else {
      throw IllegalStateException("Game is completed")
    }
  }

  private fun preFlop() {
    // Deal pocket cards
    (0 until 2).forEach {
      players = players.map { it.copy(pocket = it.pocket.copy(cards = it.pocket.cards + deck.draw())) }
    }
    onPreFlop(this)
  }

  private fun flop() {
    burn()
    flop = listOf(deck.draw(), deck.draw(), deck.draw())
    communityCards += flop!!
    onFlop(this)
  }

  private fun turn() {
    burn()
    turn = deck.draw()
    communityCards += turn!!
    onTurn(this)
  }

  private fun river() {
    burn()
    river = deck.draw()
    communityCards += river!!
    onRiver(this)
  }

  private fun burn() {
    muck += deck.draw()
  }
}

data class PlayerAndHand(val player: Player, val hand: Hand)

enum class BettingRound {
  PRE_FLOP, FLOP, TURN, RIVER;

  fun next(): BettingRound? {
    if (BettingRound.RIVER == this) return null
    return BettingRound.values()[this.ordinal + 1]
  }
}

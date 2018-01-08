package me.ccampo.librepoker.engine.model

/**
 * @author Chris Campo
 */
enum class BetType {
  BET, CHECK, CALL, RAISE
}

data class Bet(val type: BetType, val value: Int)

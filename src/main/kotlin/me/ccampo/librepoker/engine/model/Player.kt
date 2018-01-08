package me.ccampo.librepoker.engine.model

/**
 * @author Chris Campo
 */
data class Player(val name: String, val pocket: Pocket = Pocket(), val chips: Int = 0)

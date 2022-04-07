/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 07.04.22, 15:06 by Carina The Latest changes made by Carina on 07.04.22, 15:06 All contents of "Game.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.game

import de.carina.thehunter.countdowns.Countdown
import de.carina.thehunter.countdowns.EndCountdown
import de.carina.thehunter.countdowns.IngameCountdown
import de.carina.thehunter.countdowns.LobbyCountdown
import de.carina.thehunter.gamestates.GameState
import de.carina.thehunter.gamestates.GameStates

class Game {

    lateinit var gameState: GameState
    lateinit var countdown: Countdown
    val countdowns = listOf(LobbyCountdown(), IngameCountdown(), EndCountdown())

    var MAX_PLAYERS: Int = 0
    var MIN_PLAYERS: Int = 0

    fun start() {
        TODO("not implemented")
    }

    fun end() {
        TODO("not implemented")
    }

    fun nextGameState() {
        gameState = GameStates.gameStates[GameStates.gameStates.indexOf(gameState) + 1]
    }

}
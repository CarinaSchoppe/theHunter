/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 07.04.22, 23:06 by Carina The Latest changes made by Carina on 07.04.22, 23:06 All contents of "LobbyState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.gamestates

import de.carina.thehunter.countdowns.Countdowns
import de.carina.thehunter.util.game.Game

class LobbyState(game: Game) : GameState(game) {
    override fun start() {
        game.countdowns[Countdowns.LOBBY_COUNTDOWN.id].start()
        TODO("implement")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override val gameStateID: Int = GameStates.LOBBY_STATE.id
}
/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 12:40 AM All contents of "LobbyState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.gamestates

import de.pixels.thehunter.countdowns.Countdowns
import de.pixels.thehunter.util.game.management.Game

class LobbyState(game: Game) : GameState(game) {
    /**
     * Starts the game countdown.
     *
     * This method assigns the desired countdown to the current countdown in the game object and starts it.
     */
    override fun start() {
        game.currentCountdown = game.countdowns[Countdowns.LOBBY_COUNTDOWN.id]
        game.currentCountdown.start()

    }

    /**
     * Stops the execution of a process.
     */
    override fun stop() {
        return
    }

    /**
     * Represents the ID of the current game state.
     */
    override val gameStateID: Int = GameStates.LOBBY_STATE.id
}

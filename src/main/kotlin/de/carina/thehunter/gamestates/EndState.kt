/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "EndState.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.gamestates

import de.carina.thehunter.TheHunter
import de.carina.thehunter.countdowns.Countdowns
import de.carina.thehunter.util.game.Game
import org.bukkit.entity.Player

class EndState(game: Game) : GameState(game) {
    override fun start() {

        userHandlerShowingAfter(game.spectators.toMutableList())
        userHandlerShowingAfter(game.players.toMutableList())

        game.currentCountdown = game.countdowns[Countdowns.END_COUNTDOWN.id]
        game.currentCountdown.start()
    }


    private fun userHandlerShowingAfter(playerList: MutableList<Player>) {
        playerList.forEach {
            it.inventory.clear()
            it.teleport(game.endLocation!!)
            for (spectator in game.spectators.toMutableList()) {
                spectator.showPlayer(TheHunter.instance, it)
                it.showPlayer(TheHunter.instance, spectator)
            }
        }
    }


    override fun stop() {
        game.mapResetter.resetMap()
        game.players.toMutableList().forEach {
            it.performCommand("thehunter leave")
        }
        game.spectators.toMutableList().forEach {
            it.performCommand("thehunter leave")
        }
    }

    override val gameStateID: Int = GameStates.END_STATE.id
}
/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:43 PM All contents of "EndState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.gamestates

import de.carina.thehunter.TheHunter
import de.carina.thehunter.countdowns.Countdowns
import de.carina.thehunter.util.game.Game
import org.bukkit.Sound
import org.bukkit.entity.Player

class EndState(game: Game) : GameState(game) {
    override fun start() {


        userHandlerShowingAfter(game.spectators.toMutableList())
        userHandlerShowingAfter(game.players.toMutableList())

        game.currentCountdown = game.countdowns[Countdowns.END_COUNTDOWN.id]
        game.currentCountdown.start()

        game.spectators.forEach { spectator ->
            game.spectators.forEach {
                spectator.showPlayer(TheHunter.instance, it)
                it.showPlayer(TheHunter.instance, spectator)
            }
        }
        game.players.forEach { player ->
            game.spectators.forEach { spectator ->
                player.showPlayer(TheHunter.instance, spectator)
            }
        }
        game.worldBoarderController.resetWorldBoarder()
    }


    private fun userHandlerShowingAfter(playerList: MutableList<Player>) {
        playerList.forEach {
            it.playSound(it.location, Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f)
            it.inventory.clear()
            it.teleport(game.endLocation!!)
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

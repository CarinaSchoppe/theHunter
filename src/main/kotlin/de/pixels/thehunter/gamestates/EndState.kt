/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:43 PM All contents of "EndState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.gamestates

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.countdowns.Countdowns
import de.pixels.thehunter.util.game.management.Game
import org.bukkit.Sound
import org.bukkit.entity.Player

class EndState(game: Game) : GameState(game) {
    /**
     * Method to start the game.
     * This method performs the necessary actions to start the game including handling players and spectators,
     * starting the countdown, and resetting the world border.
     */
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


    /**
     * Perform actions on each player in the playerList after showing the user handler.
     *
     * @param playerList The list of players to perform actions on.
     */
    private fun userHandlerShowingAfter(playerList: MutableList<Player>) {
        playerList.forEach {
            it.playSound(it.location, Sound.ENTITY_ENDER_DRAGON_DEATH, 1f, 1f)
            it.inventory.clear()
            it.teleport(game.endLocation ?: return@forEach)
        }
    }


    /**
     * Stops the game by resetting the map, removing all players and spectators.
     *
     * This method performs the following actions:
     * 1. Resets the game map using `game.mapResetter.resetMap()`.
     * 2. Removes all players from the game by executing the command "thehunter leave" on each player.
     * 3. Removes all spectators from the game by executing the command "thehunter leave" on each spectator.
     *
     * @see game.mapResetter.resetMap
     * @see Player.performCommand
     */
    override fun stop() {
        game.mapResetter.resetMap()
        game.players.toMutableList().forEach {
            it.performCommand("thehunter leave")
        }
        game.spectators.toMutableList().forEach {
            it.performCommand("thehunter leave")
        }
    }


    /**
     * The unique identifier for the current game state.
     *
     * This value represents the state of the game and is used to identify different stages or phases in the game.
     * The value is an integer that corresponds to the ID of the current game state.
     *
     * @see GameStates
     *
     * @property gameStateID The unique identifier for the current game state.
     */
    override val gameStateID: Int = GameStates.END_STATE.id
}

/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 11:07 by Carina The Latest changes made by Carina on 19.04.22, 11:07 All contents of "EndState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.gamestates

import de.carina.thehunter.TheHunter
import de.carina.thehunter.countdowns.Countdowns
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class EndState(game: Game) : GameState(game) {
    override fun start() {
        game.currentCountdown = game.countdowns[Countdowns.END_COUNTDOWN.id]
        game.currentCountdown.start()
    }

    override fun stop() {
        game.mapResetter.resetMap()
        game.players.forEach {
            it.teleport(game.backLocation!!)
            it.inventory.clear()
            showAll(it)
        }
        game.spectators.forEach {
            it.teleport(game.backLocation!!)
            it.inventory.clear()
            showAll(it)
        }

    }

    private fun showAll(user: Player) {
        Bukkit.getOnlinePlayers().forEach {
            if (it != user) {
                user.showPlayer(TheHunter.instance, it)
                it.showPlayer(TheHunter.instance, user)
            }
        }
        GamesHandler.playerInGames.keys.forEach {
            it.hidePlayer(TheHunter.instance, user)
            user.hidePlayer(TheHunter.instance, it)
        }
        GamesHandler.spectatorInGames.keys.forEach {
            it.hidePlayer(TheHunter.instance, user)
            user.hidePlayer(TheHunter.instance, it)
        }
    }

    override val gameStateID: Int = GameStates.END_STATE.id
}
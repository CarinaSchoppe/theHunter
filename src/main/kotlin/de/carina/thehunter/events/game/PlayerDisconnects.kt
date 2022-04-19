/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 10:46 by Carina The Latest changes made by Carina on 19.04.22, 10:46 All contents of "PlayerDisconnects.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.game

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerDisconnects : Listener {

    @EventHandler
    fun onPlayerDisconnect(event: PlayerQuitEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        val game = GamesHandler.playerInGames[event.player]!!
        if (game.currentGameState !is IngameState) {
            removePlayer(event.player)
            return
        } else {
            removePlayer(event.player)
            game.deathChests.createDeathChest(event.player)
            TheHunter.instance.statsSystem.playerDied(event.player)
            if (game.checkWinning())
                game.nextGameState()
        }

    }

    private fun removePlayer(player: Player) {
        val game = GamesHandler.playerInGames[player]!!
        game.spectators.remove(player)
        game.players.remove(player)
        for (players in Bukkit.getOnlinePlayers()) {
            players.showPlayer(TheHunter.instance, player)
            player.showPlayer(TheHunter.instance, players)
        }
        game.spectators.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-quit"]!!.replace("%player%", player.name))
            player.hidePlayer(TheHunter.instance, it)
        }
        game.players.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-quit"]!!.replace("%player%", player.name))
            player.hidePlayer(TheHunter.instance, it)
        }
    }
}
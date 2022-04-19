/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 10:46 by Carina The Latest changes made by Carina on 19.04.22, 10:46 All contents of "PlayerKillsOther.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerKillsOther : Listener {

    @EventHandler
    fun onPlayerKillsOther(event: PlayerDeathEvent) {
        if (event.entity.killer != null) {
            if (event.entity.killer!! !is Player) {
                addDeathToPlayer(event.player)
            } else {
                playerKilledOther(event.player, event.entity.killer!!)
            }
        } else {
            addDeathToPlayer(event.player)
        }
    }

    private fun playerKilledOther(player: Player, killer: Player) {
        if (!GamesHandler.playerInGames.containsKey(player))
            return
        if (!GamesHandler.playerInGames.containsKey(killer))
            return
        if (GamesHandler.playerInGames[player] != GamesHandler.playerInGames[killer])
            return
        val game = GamesHandler.playerInGames[player]!!
        if (game.currentGameState !is IngameState)
            return
        TheHunter.instance.statsSystem.playerKilledOtherPlayer(killer, player)
        game.deathChests.createDeathChest(player)
        game.players.remove(player)
        game.spectators.add(player)
        player.teleport(game.spectatorLocation!!)
        for (players in Bukkit.getOnlinePlayers()) {
            players.showPlayer(TheHunter.instance, player)
        }
        game.players.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-killed-by-other"]!!.replace("%player%", player.name).replace("%killer%", killer.name))
            it.hidePlayer(TheHunter.instance, player)
        }
        game.spectators.filter { it.name != player.name }.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-killed-by-other"]!!.replace("%player%", player.name).replace("%killer%", killer.name))
            it.hidePlayer(TheHunter.instance, player)
        }
        player.sendMessage(TheHunter.instance.messages.messagesMap["player-own-killed-by-other"]!!.replace("%killer%", killer.name))
        if (game.checkWinning())
            game.nextGameState()
    }

    private fun addDeathToPlayer(player: Player) {
        if (!GamesHandler.playerInGames.containsKey(player))
            return
        val game = GamesHandler.playerInGames[player]!!
        if (game.currentGameState !is IngameState)
            return
        TheHunter.instance.statsSystem.playerDied(player)
        game.deathChests.createDeathChest(player)
        game.players.remove(player)
        game.spectators.add(player)
        player.teleport(game.spectatorLocation!!)
        for (players in Bukkit.getOnlinePlayers()) {
            players.showPlayer(TheHunter.instance, player)
        }
        game.players.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-died"]!!.replace("%player%", player.name))
            it.hidePlayer(TheHunter.instance, player)
        }
        game.spectators.filter { it.name != player.name }.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-died"]!!.replace("%player%", player.name))
            it.hidePlayer(TheHunter.instance, player)
        }
        player.sendMessage(TheHunter.instance.messages.messagesMap["player-own-died"]!!)
        if (game.checkWinning())
            game.nextGameState()

    }
}
/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.04.22, 15:20 by Carina The Latest changes made by Carina on 21.04.22, 15:20 All contents of "LeaveGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.game.Team
import de.carina.thehunter.util.misc.Util
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot

class LeaveGame {
    fun leave(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, "leave", 0, "theHunter.leave"))
            return

        if (!GamesHandler.playerInGames.containsKey(sender as Player)) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["player-not-in-game"]!!.replace("%player%", sender.name))
            return
        }
        val game = GamesHandler.playerInGames[sender]!!
        if (game.currentGameState !is IngameState) {
            removePlayer(sender)
            return
        } else {
            removePlayer(sender)
            game.deathChests.createDeathChest(sender)
            TheHunter.instance.statsSystem.playerDied(sender)
            if (game.checkWinning())
                game.nextGameState()
        }


    }

    private fun removePlayer(player: Player) {
        val game = GamesHandler.playerInGames[player]!!
        game.spectators.remove(player)
        game.players.remove(player)
        GamesHandler.playerInGames.remove(player)
        GamesHandler.spectatorInGames.remove(player)
        player.teleport(game.backLocation!!)
        Util.updateGameSigns(game)
        player.inventory.clear()
        player.scoreboard.clearSlot(DisplaySlot.SIDEBAR)
        val team = game.teams.find { it.teamMembers.contains(player) }
        if (team != null) {
            Team.removePlayerFromTeam(player, player)
        }
        playerMessagesAndHiding(game, player)
    }

    private fun playerMessagesAndHiding(game: Game, player: Player) {
        val team = game.teams.find { it.teamMembers.contains(player) }
        if (team != null)
            Team.removePlayerFromTeam(player, player)
        for (players in Bukkit.getOnlinePlayers()) {
            players.showPlayer(TheHunter.instance, player)
            player.showPlayer(TheHunter.instance, players)
        }
        GamesHandler.playerInGames.keys.forEach {
            it.hidePlayer(TheHunter.instance, player)
            player.hidePlayer(TheHunter.instance, it)
        }
        GamesHandler.spectatorInGames.keys.forEach {
            it.hidePlayer(TheHunter.instance, player)
            player.hidePlayer(TheHunter.instance, it)
        }
        game.spectators.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-quit"]!!.replace("%player%", player.name))
        }
        game.players.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-quit"]!!.replace("%player%", player.name))
        }
        player.sendMessage(TheHunter.instance.messages.messagesMap["player-own-quit"]!!)
    }
}
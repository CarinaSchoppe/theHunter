/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.04.22, 15:55 by Carina The Latest changes made by Carina on 21.04.22, 15:55 All contents of "JoinGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.countdowns.LobbyCountdown
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.PlayerTeamHead
import de.carina.thehunter.util.misc.Util
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class JoinGame {

    fun join(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, "join", 1, "theHunter.join"))
            return

        val game = GamesHandler.games.find { it.name == args[0] }
        if (game == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["game-not-exists"]!!.replace("%game%", args[0]))
            return
        }
        if (!playerAddingAndMessaging(sender as Player, game))
            return
        Util.updateGameSigns(game)
        sender.inventory.clear()
        sender.inventory.setItem(8, LeaveItem.leaveItem)
        sender.inventory.setItem(5, PlayerTeamHead.createPlayerHead())
        sender.gameMode = GameMode.SURVIVAL
        if (game.currentCountdown is LobbyCountdown) {
            val countdown = game.currentCountdown as LobbyCountdown
            if (!countdown.isRunning && !countdown.isIdle)
                countdown.start()
        }
        playerHiding(sender, game)
    }

    private fun playerAddingAndMessaging(player: Player, game: Game): Boolean {
        if (GamesHandler.playerInGames.containsKey(player) || GamesHandler.spectatorInGames.containsKey(player)) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["player-already-ingame"]!!)
            return false
        }
        if (game.players.size + 1 <= game.maxPlayers) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["join-game-successfully"]!!.replace("%game%", game.name))
            game.players.forEach {
                it.sendMessage(TheHunter.instance.messages.messagesMap["player-joined-game"]!!.replace("%player%", player.name))
            }
            game.players.add(player)
            player.allowFlight = false
            GamesHandler.playerInGames[player] = game
            player.teleport(game.lobbyLocation!!)
            game.spectators.forEach {
                it.sendMessage(TheHunter.instance.messages.messagesMap["player-joined-game"]!!.replace("%player%", player.name))
            }
        } else {
            GamesHandler.spectatorInGames[player] = game
            game.spectators.add(player)
            player.allowFlight = false
            player.sendMessage(TheHunter.instance.messages.messagesMap["game-full-spectator"]!!)
            player.teleport(game.lobbyLocation!!)
        }
        return true
    }

    private fun playerHiding(player: Player, game: Game) {
        Bukkit.getOnlinePlayers().forEach {
            it.hidePlayer(TheHunter.instance, player)
            player.hidePlayer(TheHunter.instance, it)
        }
        game.players.forEach {
            if (game.players.contains(player))
                it.showPlayer(TheHunter.instance, player)
            player.showPlayer(TheHunter.instance, it)
        }
        game.spectators.forEach {
            if (game.players.contains(player))
                it.showPlayer(TheHunter.instance, player)
        }
    }
}
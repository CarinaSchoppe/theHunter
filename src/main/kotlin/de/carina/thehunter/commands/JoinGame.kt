/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 18:47 by Carina The Latest changes made by Carina on 19.04.22, 18:47 All contents of "JoinGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.countdowns.LobbyCountdown
import de.carina.thehunter.items.configurator.LeaveItem
import de.carina.thehunter.util.game.GamesHandler
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
        if (GamesHandler.playerInGames.containsKey(sender) || GamesHandler.spectatorInGames.containsKey(sender)) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["player-already-ingame"]!!)
            return
        }
        if (game.players.size + 1 <= game.maxPlayers) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["join-game-successfully"]!!.replace("%game%", game.name))
            game.players.forEach {
                it.sendMessage(TheHunter.instance.messages.messagesMap["player-joined-game"]!!.replace("%player%", sender.name))
            }
            game.players.add(sender as Player)
            GamesHandler.playerInGames[sender] = game
            sender.teleport(game.lobbyLocation!!)
            game.spectators.forEach {
                it.sendMessage(TheHunter.instance.messages.messagesMap["player-joined-game"]!!.replace("%player%", sender.name))
            }
        } else {
            GamesHandler.spectatorInGames[sender as Player] = game
            game.spectators.add(sender)
            sender.sendMessage(TheHunter.instance.messages.messagesMap["game-full-spectator"]!!)
            sender.teleport(game.lobbyLocation!!)
        }
        Util.updateGameSigns(game)
        sender.inventory.clear()
        sender.inventory.setItem(8, LeaveItem.createLeaveItem())
        sender.gameMode = GameMode.SURVIVAL
        if (game.currentCountdown is LobbyCountdown) {
            val countdown = game.currentCountdown as LobbyCountdown
            if (!countdown.isRunning && !countdown.isIdle)
                countdown.start()
        }
        Bukkit.getOnlinePlayers().forEach {
            it.hidePlayer(TheHunter.instance, sender)
            sender.hidePlayer(TheHunter.instance, it)
        }
        game.players.forEach {
            if (game.players.contains(sender))
                it.showPlayer(TheHunter.instance, sender)
            sender.showPlayer(TheHunter.instance, it)
        }
        game.spectators.forEach {
            if (game.players.contains(sender))
                it.showPlayer(TheHunter.instance, sender)
        }
    }
}
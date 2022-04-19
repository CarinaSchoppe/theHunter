/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 12:49 by Carina The Latest changes made by Carina on 19.04.22, 12:49 All contents of "JoinGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.items.configurator.LeaveItem
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Bukkit
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
        if (game.players.size + 1 <= game.maxPlayers) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["join-game-successfully"]!!.replace("%game%", game.name))
            game.players.forEach {
                it.sendMessage(TheHunter.instance.messages.messagesMap["player-joined-game"]!!.replace("%player%", sender.name))
            }
            game.players.add(sender as Player)
            game.spectators.forEach {
                it.sendMessage(TheHunter.instance.messages.messagesMap["player-joined-game"]!!.replace("%player%", sender.name))
            }
        } else {
            game.spectators.add(sender as Player)
            sender.sendMessage(TheHunter.instance.messages.messagesMap["game-full-spectator"]!!)
            sender.teleport(game.lobbyLocation!!)
        }
        sender.teleport(game.lobbyLocation!!)
        sender.inventory.clear()
        sender.inventory.setItem(8, LeaveItem.createLeaveItem())
        Bukkit.getOnlinePlayers().forEach {
            it.hidePlayer(TheHunter.instance, sender)
            sender.hidePlayer(TheHunter.instance, it)
        }
        game.players.forEach {
            if (game.players.contains(sender))
                it.showPlayer(TheHunter.instance, sender)
            sender.showPlayer(TheHunter.instance, it)
        }
    }
}
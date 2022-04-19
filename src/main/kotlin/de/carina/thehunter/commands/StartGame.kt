/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 12:49 by Carina The Latest changes made by Carina on 19.04.22, 12:49 All contents of "StartGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.LobbyState
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StartGame {

    fun start(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, "start", 0, "theHunter.start"))
            return
        if (!GamesHandler.playerInGames.containsKey(sender as Player))
            return
        val game = GamesHandler.playerInGames[sender]
        if (game!!.currentGameState !is LobbyState)
            return
        if (game.currentCountdown.duration < 5) {
            game.currentCountdown.duration = 5
            sender.sendMessage(TheHunter.instance.messages.messagesMap["game-speedup"]!!)
        } else {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["game-speedup"]!!)
        }

    }
}
/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:42 PM All contents of "StartGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.LobbyState
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.ConstantStrings
import de.carina.thehunter.util.misc.Permissions
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StartGame {

    fun start(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, ConstantStrings.START_COMMAND, 0, Permissions.START_COMMAND))
            return
        if (!GamesHandler.playerInGames.containsKey(sender as Player) && !GamesHandler.spectatorInGames.containsKey(sender))
            return
        val game = GamesHandler.playerInGames[sender] ?: GamesHandler.spectatorInGames[sender]!!
        if (game.currentGameState !is LobbyState)
            return
        if (game.currentCountdown.duration > 5) {
            game.currentCountdown.duration = 5
            sender.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.GAME_SPEEDUP]!!)
        }

    }
}

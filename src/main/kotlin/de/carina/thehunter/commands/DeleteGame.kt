/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "RemoveGame.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.GameStates
import de.carina.thehunter.util.files.BaseFile
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.Permissions
import org.bukkit.command.CommandSender
import java.io.File

class DeleteGame {
    fun remove(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, "delete", 1, Permissions.REMOVE_GAME_COMMAND))
            return
        val game = GamesHandler.games.find { it.name == args[0] }
        if (game == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["game-not-exists"]!!.replace("%game%", args[0]))
            return
        }
        game.currentGameState = game.gameStates[GameStates.END_STATE.id]
        game.nextGameState()
        game.currentGameState.stop()
        GamesHandler.games.remove(game)
        val file = File(BaseFile.gameFolder + "/arenas/${game.name}")
        if (file.deleteRecursively())
            sender.sendMessage(TheHunter.instance.messages.messagesMap["game-successfully-removed"]!!.replace("%game%", args[0]))
        else
            sender.sendMessage(TheHunter.instance.messages.messagesMap["game-could-not-delete"]!!.replace("%game%", args[0]))

    }
}
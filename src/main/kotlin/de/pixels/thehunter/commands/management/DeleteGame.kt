/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/1/22, 8:49 PM All contents of "DeleteGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.commands.management

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.commands.CommandUtil
import de.pixels.thehunter.gamestates.GameStates
import de.pixels.thehunter.util.files.BaseFile
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.File

class DeleteGame {
    fun remove(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                        sender,
                        command,
                        args,
                        ConstantStrings.DELETE_COMMAND,
                        1,
                        Permissions.REMOVE_GAME_COMMAND
                )
        )
                return

        val game = GamesHandler.games.find { it.name == args[0] }
        if (game == null) {
            TheHunter.instance.messages.messagesMap["game-not-exists"]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        args[0]
                    )
                )
            }
            return
        }

        game.currentGameState = game.gameStates[GameStates.END_STATE.id]
        game.nextGameState()
        game.currentGameState.stop()
        val player = sender as Player
        player.playSound(player.location, Sound.BLOCK_ANVIL_USE, 1f, 1f)
        GamesHandler.games.remove(game)
        val file = File(BaseFile.GAME_FOLDER + "/arenas/${game.name}")
        if (file.deleteRecursively())
            TheHunter.instance.messages.messagesMap["game-successfully-removed"]?.let {
                sender.sendMessage(
                    it
                        .replace(ConstantStrings.GAME_PERCENT, args[0])
                )
            }
        else
            TheHunter.instance.messages.messagesMap["game-could-not-delete"]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        args[0]
                    )
                )
            }
    }
}

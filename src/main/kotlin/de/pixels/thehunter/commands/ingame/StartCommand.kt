/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:42 PM All contents of "StartCommand.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.commands.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.commands.util.CommandUtil
import de.pixels.thehunter.gamestates.LobbyState
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StartCommand : CommandExecutor {

    /**
     * Executes the specified command.
     *
     * @param sender the CommandSender who executed the command
     * @param command the Command that was executed
     * @param label the alias or label used for the command
     * @param args an array of arguments passed to the command, or null if no arguments were provided
     * @return true if the command was successfully executed, false otherwise
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player || !GamesHandler.playerInGames.containsKey(sender) && !GamesHandler.spectatorInGames.containsKey(sender))
            return false
        sender.performCommand("thehunter start")
        return false
    }

    fun start(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.START_COMMAND,
                0,
                Permissions.START_COMMAND
            ) || !GamesHandler.playerInGames.containsKey(sender as Player) && !GamesHandler.spectatorInGames.containsKey(
                sender
            )
        )
            return

        val game = GamesHandler.playerInGames[sender] ?: GamesHandler.spectatorInGames[sender] ?: return
        if (game.currentGameState !is LobbyState)
            return
        if (game.currentCountdown.duration > 5) {
            game.currentCountdown.duration = 5
            TheHunter.instance.messagesFile.messagesMap[ConstantStrings.GAME_SPEEDUP]?.let { sender.sendMessage(it) }
        }

    }
}

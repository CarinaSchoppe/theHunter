/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 11:16 PM All contents of "BaseCommand.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.commands

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.commands.ingame.*
import de.pixels.thehunter.commands.management.AnvilGUI
import de.pixels.thehunter.commands.management.CreateGame
import de.pixels.thehunter.commands.management.DeleteGame
import de.pixels.thehunter.commands.management.MapReset
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class BaseCommand : CommandExecutor {


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(
                TheHunter.instance.messages.messagesMap[ConstantStrings.NOT_ENOUGH_ARGUMENTS]!!.replace(
                    ConstantStrings.ARGUMENTS_PERCENT,
                    1.toString()
                )
            )
            return false
        }

        val commandName = args[0]
        val arguments = args.slice(1 until args.size).toTypedArray()
        when (commandName.lowercase()) {
            ConstantStrings.START_COMMAND -> {
                StartGame().start(sender, commandName, arguments)
                return true
            }

            ConstantStrings.STATS_COMMAND -> {
                StatsPlayer().stats(sender, commandName, arguments)
                return true
            }

            ConstantStrings.GUI_COMMAND -> {
                AnvilGUI().create(sender, commandName, arguments)
                return true
            }

            ConstantStrings.TEAM_COMMAND -> {
                TeamCommands().team(sender, commandName, arguments)
                return true
            }

            ConstantStrings.DELETE_COMMAND -> {
                DeleteGame().remove(sender, commandName, arguments)
                return true
            }

            ConstantStrings.JOIN_COMMAND -> {
                JoinGame().join(sender, commandName, arguments)
                return true
            }

            ConstantStrings.SETUP_COMMAND -> {
                CreateGame().create(sender, commandName, arguments)
                return true
            }

            ConstantStrings.INVENTORY_COMMAND -> {
                GamesInventory().openInventory(sender, commandName, arguments)
                return true
            }

            ConstantStrings.SETTINGS_COMMAND -> {
                GamesInventory().openSettings(sender, commandName, arguments)
                return true
            }

            ConstantStrings.LEAVE_COMMAND -> {
                LeaveGame().leave(sender, commandName, arguments)
                return true
            }

            ConstantStrings.MAP_RESET_COMMAND -> {
                MapReset().reset(sender, commandName, arguments)
                return true
            }
        }

        sender.sendMessage(
            TheHunter.instance.messages.messagesMap["no-command-found"]!!.replace(
                "%command%",
                commandName
            )
        )
        return false
    }
}
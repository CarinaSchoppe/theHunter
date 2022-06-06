/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "BaseCommand.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class BaseCommand : CommandExecutor {


    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["not-enough-arguments"]!!.replace("%arguments%", 1.toString()))
            return false
        }

        val commandName = args[0]
        val arguments = args.slice(1 until args.size).toTypedArray()
        when (commandName.lowercase()) {
            "start" -> {
                StartGame().start(sender, commandName, arguments)
                return true
            }

            "stats" -> {
                StatsPlayer().stats(sender, commandName, arguments)
                return true
            }

            "gui" -> {
                AnvilGUI().create(sender, commandName, arguments)
                return true
            }

            "team" -> {
                TeamCommands().team(sender, commandName, arguments)
                return true
            }

            "remove" -> {
                RemoveGame().remove(sender, commandName, arguments)
                return true
            }

            "join" -> {
                JoinGame().join(sender, commandName, arguments)
                return true
            }

            "setup" -> {
                CreateGame().create(sender, commandName, arguments)
                return true
            }

            "inventory" -> {
                GamesInventory().openInventory(sender, commandName, arguments)
                return true
            }

            "settings" -> {
                GamesInventory().openSettings(sender, commandName, arguments)
                return true
            }

            "leave" -> {
                LeaveGame().leave(sender, commandName, arguments)
                return true
            }

            "mapreset" -> {
                MapReset().reset(sender, commandName, arguments)
                return true
            }
        }

        sender.sendMessage(TheHunter.instance.messages.messagesMap["no-command-found"]!!.replace("%command%", commandName))
        return false
    }
}
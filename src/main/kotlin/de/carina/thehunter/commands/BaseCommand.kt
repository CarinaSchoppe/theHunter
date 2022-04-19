/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 10:59 by Carina The Latest changes made by Carina on 19.04.22, 10:59 All contents of "BaseCommand.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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
            sender.sendMessage(TheHunter.instance.messages.messagesMap["not-enough-arguments"]!!)
            return false
        }

        val commandName = args[0]
        val args = args.slice(1 until args.size).toTypedArray()
        when (commandName) {
            "start" -> {
                StartGame().startGame(sender, commandName, args)
            }
        }

        sender.sendMessage(TheHunter.instance.messages.messagesMap["command-not-found"]!!)
        return false
    }
}
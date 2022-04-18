/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "CommandUtil.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

object CommandUtil {

    fun checkCommandBasics(sender: CommandSender, command: Command, args: Array<out String>, cmd: String, minArgs: Int, permissions: String): Boolean {

        if (sender !is org.bukkit.entity.Player) {
            TheHunter.instance.messages.sendMessageToConsole("not-a-player")
            return false
        }

        if (command.name != cmd)
            return false

        val player: org.bukkit.entity.Player = sender

        if (args.size < minArgs) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["not-enough-arguments"]!!.replace("%arguments%", minArgs.toString()))
            return false
        }

        if (!player.hasPermission(permissions)) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["no-permission"]!!)
            return false
        }

        return true
    }

}
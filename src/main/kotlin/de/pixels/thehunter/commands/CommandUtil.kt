/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 10:34 PM All contents of "CommandUtil.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.commands

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.command.CommandSender

object CommandUtil {

    fun checkCommandBasics(
        sender: CommandSender,
        command: String,
        args: Array<out String>,
        cmd: String,
        minArgs: Int,
        permissions: String
    ): Boolean {
        if (sender !is org.bukkit.entity.Player) {
            TheHunter.instance.messages.sendMessageToConsole(ConstantStrings.NOT_A_PLAYER)
            return false
        }

        if (command.lowercase() != cmd.lowercase())
            return false

        val player: org.bukkit.entity.Player = sender

        if (args.size < minArgs) {
            player.sendMessage(
                TheHunter.instance.messages.messagesMap["not-enough-arguments"]!!.replace(
                    ConstantStrings.ARGUMENTS_PERCENT,
                    "$minArgs"
                )
            )
            return false
        }

        if (!player.hasPermission(Permissions.PERMISSION_PREFIX + permissions)) {
            player.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.NO_PERMISSION]!!)
            return false
        }

        return true
    }

}

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 10:34 PM All contents of "CommandUtil.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.commands

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.command.CommandSender

object CommandUtil {

    /**
     * Checks if the given command meets the basic requirements.
     *
     * @param sender The command sender.
     * @param command The command that was executed.
     * @param args The arguments passed with the command.
     * @param cmd The expected command.
     * @param minArgs The minimum number of arguments required.
     * @param permissions The required permissions.
     * @return True if the command meets the basic requirements, false otherwise.
     */
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
            TheHunter.instance.messages.messagesMap["not-enough-arguments"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.ARGUMENTS_PERCENT,
                        "$minArgs"
                    )
                )
            }
            return false
        }

        if (!player.hasPermission(Permissions.PERMISSION_PREFIX + permissions)) {
            TheHunter.instance.messages.messagesMap[ConstantStrings.NO_PERMISSION]?.let { player.sendMessage(it) }
            return false
        }

        return true
    }

}

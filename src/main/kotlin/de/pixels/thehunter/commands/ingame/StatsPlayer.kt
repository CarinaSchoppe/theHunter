/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 10:47 PM All contents of "StatsPlayer.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.commands.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.commands.CommandUtil
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StatsPlayer {

    /**
     * Generates and sends statistics message for a player to the sender.
     *
     * @param sender The command sender who initiated the stats command.
     * @param command The command string used to invoke the stats command.
     * @param args The array of strings containing command arguments.
     */
    fun stats(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.STATS_COMMAND,
                0,
                Permissions.STATS_COMMAND
            )
        )
            return

        val player: Player = if (args.isEmpty())
            sender as Player
        else
            Bukkit.getPlayer(args[0]) ?: return

        TheHunter.instance.statsSystem.generateStatsMessageForPlayer(sender as Player, player)

    }

}

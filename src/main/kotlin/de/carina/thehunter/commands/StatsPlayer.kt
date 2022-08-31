/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "StatsPlayer.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.misc.ConstantStrings
import de.carina.thehunter.util.misc.Permissions
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StatsPlayer {

    fun stats(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, ConstantStrings.STATS_COMMAND, 0, Permissions.STATS_COMMAND))
            return

        val player: Player = if (args.isEmpty())
            sender as Player
        else
            Bukkit.getPlayer(args[0])!!

        TheHunter.instance.statsSystem.generateStatsMessageForPlayer(sender as Player, player)

    }

}

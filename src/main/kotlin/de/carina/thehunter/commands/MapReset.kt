/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 19:27 by Carina The Latest changes made by Carina on 19.04.22, 19:27 All contents of "MapReset.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.command.CommandSender

class MapReset {
    fun reset(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, "mapreset", 0, "theHunter.mapreset"))
            return

        if (!GamesHandler.playerInGames.containsKey(sender) && !GamesHandler.spectatorInGames.containsKey(sender))
            return

        val game = GamesHandler.playerInGames[sender] ?: GamesHandler.spectatorInGames[sender]
        game!!.mapResetter.resetMap()
    }
}
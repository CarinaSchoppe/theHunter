/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "MapReset.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.commands

import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.ConstantStrings
import de.carina.thehunter.util.misc.Permissions
import org.bukkit.command.CommandSender

class MapReset {
    fun reset(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, ConstantStrings.MAP_RESET_COMMAND, 0, Permissions.MAPRESET_COMMAND))
            return

        val game = GamesHandler.playerInGames[sender] ?: GamesHandler.spectatorInGames[sender] ?: return
        game.mapResetter.resetMap()
    }
}

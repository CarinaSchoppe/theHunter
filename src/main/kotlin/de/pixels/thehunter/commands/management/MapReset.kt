/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:41 PM All contents of "MapReset.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.commands.management

import de.pixels.thehunter.commands.CommandUtil
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.command.CommandSender

class MapReset {
    /**
     * Reset the map for a game.
     *
     * @param sender the command sender who triggered the reset command
     * @param command the command string used for the reset command
     * @param args the arguments passed with the reset command
     */
    fun reset(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.MAP_RESET_COMMAND,
                0,
                Permissions.MAPRESET_COMMAND
            )
        )
            return

        val game = GamesHandler.playerInGames[sender] ?: GamesHandler.spectatorInGames[sender] ?: return
        game.mapResetter.resetMap()
    }
}

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 10:46 PM All contents of "GamesInventory.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.commands.ingame

import de.pixels.thehunter.commands.util.CommandUtil
import de.pixels.thehunter.util.builder.Inventories
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import de.pixels.thehunter.util.misc.Util
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GamesInventory {
    /**
     * Opens the games inventory for the specified command sender.
     *
     * @param sender the command sender
     * @param command the command name
     * @param args the command arguments
     */
    fun openInventory(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.INVENTORY_COMMAND,
                0,
                Permissions.GAMES_INVENTORY
            )
        )
            return

        (sender as Player).openInventory(Inventories.gamesInventory)
    }

    /**
     * Opens the settings inventory for a given player.
     *
     * @param sender The command sender.
     * @param command The command string.
     * @param args The arguments passed to the command.
     */
    fun openSettings(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.SETTINGS_COMMAND,
                0,
                Permissions.SETTINGS_GUI
            ) || !Util.currentGameSelected.containsKey(sender)
        )
            return


        (sender as Player).openInventory(Inventories.createSettingsInventory(Util.currentGameSelected[sender] ?: return))
    }
}

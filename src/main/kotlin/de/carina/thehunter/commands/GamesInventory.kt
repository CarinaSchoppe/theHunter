/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "GamesInventory.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.commands

import de.carina.thehunter.util.builder.Inventories
import de.carina.thehunter.util.misc.ConstantStrings
import de.carina.thehunter.util.misc.Permissions
import de.carina.thehunter.util.misc.Util
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GamesInventory {
    fun openInventory(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, ConstantStrings.INVENTORY_COMMAND, 0, Permissions.GAMES_INVENTORY))
            return

        (sender as Player).openInventory(Inventories.gamesInventory)
    }

    fun openSettings(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, ConstantStrings.SETTINGS_COMMAND, 0, Permissions.SETTINGS_GUI))
            return

        if (!Util.currentGameSelected.containsKey(sender))
            return
        (sender as Player).openInventory(Inventories.createSettingsInventory(Util.currentGameSelected[sender]!!))
    }
}

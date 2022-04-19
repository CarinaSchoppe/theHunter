/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 18:19 by Carina The Latest changes made by Carina on 19.04.22, 18:19 All contents of "GamesInventory.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.util.misc.GamesInventoryList
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GamesInventory {
    fun openInv(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, "inventory", 0, "theHunter.inventory"))
            return

        (sender as Player).openInventory(GamesInventoryList.createInventory())
    }
}
/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 21:33 by Carina The Latest changes made by Carina on 19.04.22, 21:33 All contents of "LeaveItem.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.items.configurator

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class LeaveItem : Listener {
    companion object {
        val leaveItem = ItemBuilder(Material.IRON_DOOR).addDisplayName(TheHunter.prefix + "§6Leave").addLore("§7Click to leave the game").addEnchantment(Enchantment.DURABILITY, 1).build()

    }


    @EventHandler
    fun onPlayerLeavesGame(event: PlayerInteractEvent) {
        if (event.item == null) return
        if (!event.item!!.hasItemMeta())
            return
        if (event.item!!.itemMeta != leaveItem.itemMeta)
            return
        if (event.action.isLeftClick)
            return
        event.player.performCommand("thehunter leave")
    }
}


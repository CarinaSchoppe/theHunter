/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 19:15 by Carina The Latest changes made by Carina on 19.04.22, 19:15 All contents of "Healer.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.items.chest.ItemHandler
import de.carina.thehunter.util.builder.ItemBuilder
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class Healer : Listener {
    companion object {
        fun createHealerItem(): ItemStack {
            return ItemBuilder(Material.RED_DYE).addDisplayName(TheHunter.PREFIX + "§7Healer").addLore("§7Heals you!").addLore("§7Right-click to activate").build()
        }
    }

    @EventHandler
    fun onHealerItemClick(event: PlayerInteractEvent) {
        if (!ItemHandler.shouldInteractWithItem(event, createHealerItem(), "Healer"))
            return
        event.isCancelled = true
        if (event.player.health + TheHunter.instance.itemSettings.settingsMap["healer-amount"] as Int <= 20)
            event.player.health += TheHunter.instance.itemSettings.settingsMap["healer-amount"] as Int
        else
            event.player.health = 20.0
        ItemHandler.removeOneItemOfPlayer(event.player)
        event.player.sendActionBar(LegacyComponentSerializer.legacySection().deserialize(TheHunter.instance.messages.messagesMap["healer-message"]!!.replace("%hearts%", (TheHunter.instance.settings.settingsMap["healer-amount"] as Int).toString())))
    }
}
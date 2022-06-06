/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 11:42 PM by Carina The Latest changes made by Carina on 6/6/22, 11:15 PM All contents of "Healer.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.items.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.items.ItemHandler
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class Healer : Listener {
    companion object {
        fun createHealerItem(): ItemStack {
            return ItemBuilder(Material.RED_DYE).addDisplayName(TheHunter.prefix + "§7Healer").addLore("§7Heals you!").addLore("§7Right-click to activate").build()
        }
    }

    @EventHandler
    fun onHealerItemClick(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, createHealerItem(), "Healer"))
            return
        event.isCancelled = true
        if (event.player.health + GamesHandler.playerInGames[event.player]!!.gameItems.items["healer-amount"] as Int <= 20)
            event.player.health += GamesHandler.playerInGames[event.player]!!.gameItems.items["healer-amount"] as Int
        else
            event.player.health = 20.0
        ItemHandler.removeOneItemOfPlayer(event.player)
        event.player.sendActionBar(LegacyComponentSerializer.legacySection().deserialize(TheHunter.instance.messages.messagesMap["healer-message"]!!.replace("%hearts%", (GamesHandler.playerInGames[event.player]!!.gameItems.items["healer-amount"] as Int).toString())))
    }
}
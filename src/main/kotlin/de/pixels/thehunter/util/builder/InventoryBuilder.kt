/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 12:36 AM All contents of "InventoryBuilder.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.builder

import de.pixels.thehunter.util.game.management.GamesHandler
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory

class InventoryBuilder(name: String, size: Int) {
    private val inventory =
        Bukkit.createInventory(null, size, LegacyComponentSerializer.legacySection().deserialize(name))

    fun create(): Inventory {
        return inventory
    }


    fun addGamesToInventory(): InventoryBuilder {
        for (game in GamesHandler.games) {
            inventory.addItem(
                ItemBuilder(Material.ACACIA_SIGN).addDisplayName("§6${game.name}").addLore("§7Click to join the game")
                    .addLore("§7current Gamestate: §6${game.currentGameState}")
                    .addLore("§aCurrent players: §7[§6${game.players.size}§7/§6${game.maxPlayers}§7]").build()
            )
        }
        return this
    }

    fun setItem(slot: Int, item: org.bukkit.inventory.ItemStack): InventoryBuilder {
        inventory.setItem(slot, item)
        return this
    }

    fun fillInventory(item: org.bukkit.inventory.ItemStack): InventoryBuilder {
        for (i in 0 until inventory.size) {
            if (inventory.getItem(i) == null)
                inventory.setItem(i, item)
        }
        return this
    }


}
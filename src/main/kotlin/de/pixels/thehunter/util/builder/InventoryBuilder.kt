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
    /**
     * The private variable `inventory` represents a Bukkit inventory.
     *
     * @see Bukkit.createInventory
     * @see net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().deserialize
     *
     * @property inventory The Bukkit inventory object.
     */
    private val inventory =
        Bukkit.createInventory(null, size, LegacyComponentSerializer.legacySection().deserialize(name))

    /**
     * Creates an instance of the Inventory class.
     *
     * @return the created Inventory instance
     */
    fun create(): Inventory {
        return inventory
    }


    /**
     * Adds games to the inventory.
     *
     * @return The InventoryBuilder instance.
     */
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

    /**
     * Sets the item in the specified slot of the inventory.
     *
     * @param slot The slot index to set the item in.
     * @param item The item to set in the slot.
     * @return The updated InventoryBuilder instance.
     */
    fun setItem(slot: Int, item: org.bukkit.inventory.ItemStack): InventoryBuilder {
        inventory.setItem(slot, item)
        return this
    }

    /**
     * Fills the inventory with the given item.
     *
     * @param item the item to fill the inventory with.
     * @return the InventoryBuilder instance after filling the inventory.
     */
    fun fillInventory(item: org.bukkit.inventory.ItemStack): InventoryBuilder {
        for (i in 0 until inventory.size) {
            if (inventory.getItem(i) == null)
                inventory.setItem(i, item)
        }
        return this
    }


}

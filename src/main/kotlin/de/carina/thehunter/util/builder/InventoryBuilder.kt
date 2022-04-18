/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "InventoryBuilder.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.builder

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView

class InventoryBuilder(name: String, size: Int) {
    private val inventory = Bukkit.createInventory(null, size, LegacyComponentSerializer.legacySection().deserialize(name))
    fun create(): Inventory {
        return inventory
    }

    fun addItem(item: org.bukkit.inventory.ItemStack): InventoryBuilder {
        inventory.addItem(item)
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


    companion object {
        fun getInventoryName(inventory: InventoryView): String {
            return LegacyComponentSerializer.legacySection().serialize(inventory.title())
        }
    }

}
/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "Inventories.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.builder

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.Inventory

object Inventories {
    fun setupGameInventory(name: String): Inventory {
        return InventoryBuilder("§d $name Game Setup", 18).fillInventory(ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1).build()).create()
    }
}




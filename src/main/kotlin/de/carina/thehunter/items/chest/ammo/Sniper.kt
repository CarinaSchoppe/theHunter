/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "Sniper.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.items.chest.ammo

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class Sniper {

    companion object {
        fun createSniperAmmo(): ItemStack {
            return ItemBuilder(Material.ENDER_PEARL).addDisplayName(TheHunter.PREFIX + "§6Sniper-Ammo").addLore("§7Is used to shoot with the Sniper").addEnchantment(Enchantment.DURABILITY, 1).build()
        }
    }


}
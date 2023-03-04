/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:58 PM All contents of "Minigun.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.guns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.general.AmmoItems
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object Minigun : Gun() {

    override var ammo: ItemStack = AmmoItems.minigunAmmo
    override var ammoString: String = ConstantStrings.MINIGUN_AMMO
    override var gunName: String = "minigun"

    override var gun: ItemStack = ItemBuilder(Material.STONE_HOE).addDisplayName(TheHunter.prefix + "§7Minigun")
        .addEnchantment(Enchantment.DURABILITY, 1).addLore("§7Right-click to shoot").build()


}

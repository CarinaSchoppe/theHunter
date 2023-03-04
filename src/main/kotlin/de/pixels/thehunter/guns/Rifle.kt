/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 7:22 PM All contents of "Ak.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.guns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.general.AmmoItems
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object Rifle : Gun() {


    override var ammo: ItemStack = AmmoItems.rifleAmmo
    override var ammoString: String = ConstantStrings.RIFLE_AMMO
    override var gunName: String = "rifle"
    override var gun = ItemBuilder(Material.IRON_HOE).addDisplayName(TheHunter.prefix + "§7Rifle")
        .addEnchantment(Enchantment.DURABILITY, 1).addLore("§7Right-click to shoot").build()


}

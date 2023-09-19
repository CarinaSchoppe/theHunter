/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 11:01 PM All contents of "Pistol.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.guns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.util.AmmoItems
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object Pistol : Gun() {
    /**
     * Represents the current ammunition item stack.
     */
    override var ammo: ItemStack = AmmoItems.pistolAmmo

    /**
     * Represents the string value of the ammunition type for a weapon.
     *
     * @property ammoString The string value of the ammunition type.
     */
    override var ammoString: String = ConstantStrings.PISTOL_AMMO

    /**
     * The name of the gun.
     *
     * @property gunName the name of the gun
     */
    override var gunName: String = "pistol"

    /**
     * The gun variable represents a pistol ItemStack with specific properties.
     *
     * @property gun The pistol ItemStack with custom display name, enchantment, and lore.
     */
    override var gun: ItemStack = ItemBuilder(Material.WOODEN_HOE).addDisplayName(TheHunter.prefix + "§7Pistol")
        .addEnchantment(Enchantment.DURABILITY, 1).addLore("§7Right-click to shoot").build()


}

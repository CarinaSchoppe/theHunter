/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 11:02 PM All contents of "Sniper.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.guns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.general.AmmoItems
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object Sniper : Gun() {

    /**
     * Represents the ammo available for use in the game.
     *
     * @property ammo The ItemStack representing the ammo.
     */
    override var ammo: ItemStack = AmmoItems.sniperAmmo

    /**
     * The ammoString variable represents the type of ammunition for a sniper.
     *
     * @property ammoString The string representation of the sniper ammunition.
     * @see ConstantStrings.SNIPER_AMMO
     */
    override var ammoString: String = ConstantStrings.SNIPER_AMMO

    /**
     * Represents the name of the gun.
     *
     * @property gunName The name of the gun. Default value is "sniper".
     */
    override var gunName: String = "sniper"

    /**
     * Represents a sniper gun item.
     *
     * The sniper gun is a special item that can be used to shoot projectiles with right-click action.
     * It is created using an ItemBuilder and has the following properties:
     * - Material: DIAMOND_HOE
     * - Display Name: TheHunter.prefix + "§7Sniper"
     * - Enchantment: DURABILITY (level 1)
     * - Lore: "§7Right-click to shoot"
     *
     * This variable is intended to be used within a larger codebase for creating game items.
     *
     * @see ItemBuilder
     * @see org.bukkit.Material
     * @see org.bukkit.enchantments.Enchantment
     *
     * @since 1.0.0
     */
    override var gun =
        ItemBuilder(Material.DIAMOND_HOE).addDisplayName(TheHunter.prefix + "§7Sniper")
            .addEnchantment(Enchantment.DURABILITY, 1).addLore("§7Right-click to shoot").build()

}

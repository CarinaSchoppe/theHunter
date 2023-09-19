/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 7:22 PM All contents of "Ak.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.guns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.util.AmmoItems
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object Rifle : Gun() {


    /**
     * The ammo variable represents the ItemStack of rifle ammo.
     *
     * The ItemStack is initialized with the rifle ammo item defined in AmmoItems.rifleAmmo.
     */
    override var ammo: ItemStack = AmmoItems.rifleAmmo

    /**
     * Represents the ammunition type for a firearm.
     *
     * This variable stores the value of the ammunition type in the form of a string.
     * It is utilized to specify the type of ammunition used by a rifle.
     */
    override var ammoString: String = ConstantStrings.RIFLE_AMMO

    /**
     * The name of the gun.
     *
     * This variable represents a String value that specifies the name of the gun. It is used to indicate
     * the type or model of the gun.
     *
     * The default value is "rifle".
     */
    override var gunName: String = "rifle"

    /**
     * The gun variable represents a rifle item in the game.
     *
     * <p>
     * It is an override variable of the {@code ItemBuilder} class and is initialized with an iron hoe material.
     * The displayed name of the rifle is set to the prefix of TheHunter followed by the word "Rifle" in gray color.
     * It has an enchantment of durability level 1 and a lore line that states "Right-click to shoot".
     * </p>
     *
     * <p>
     * The gun variable is constructed using the {@code build()} method of the ItemBuilder class.
     * </p>
     *
     * @see ItemBuilder
     * @see Material
     * @see Enchantment
     */
    override var gun = ItemBuilder(Material.IRON_HOE).addDisplayName(TheHunter.prefix + "§7Rifle")
        .addEnchantment(Enchantment.DURABILITY, 1).addLore("§7Right-click to shoot").build()


}

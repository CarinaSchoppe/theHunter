/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "AmmoItems.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.items.util

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.builder.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

object AmmoItems {
    /**
     * Holds the data for the rifle ammo item.
     *
     * @property rifleAmmo The instance of `ItemStack` representing the rifle ammo.
     */
    val rifleAmmo = ItemBuilder(Material.FIRE_CHARGE).addDisplayName(TheHunter.prefix + "§6Rifle-Ammo")
        .addLore("§7Is used to shoot with the Rifle").addEnchantment(Enchantment.DURABILITY, 1).build()

    /**
     * Holds the Minigun Ammo item.
     *
     * @property minigunAmmo The Minigun Ammo item.
     */
    val minigunAmmo = ItemBuilder(Material.SNOWBALL).addDisplayName(TheHunter.prefix + "§6Minigun-Ammo")
        .addLore("§7Is used to shoot with the Minigun").addEnchantment(Enchantment.DURABILITY, 1).build()

    /**
     * Represents the ammunition for a pistol.
     *
     * The `pistolAmmo` variable is an instance of the `ItemStack` class, built using the `ItemBuilder` class.
     * It is used to shoot with a pistol in the game. The ammunition has a display name and lore that describe its purpose.
     * Additionally, it has an enchantment applied to it to increase its durability.
     *
     * Example usage:
     * ```
     * val pistolAmmo = ItemBuilder(Material.ARROW).addDisplayName(TheHunter.prefix + "§6Pistol-Ammo")
     *     .addLore("§7Is used to shoot with the Pistol").addEnchantment(Enchantment.DURABILITY, 1).build()
     * ```
     */
    val pistolAmmo = ItemBuilder(Material.ARROW).addDisplayName(TheHunter.prefix + "§6Pistol-Ammo")
        .addLore("§7Is used to shoot with the Pistol").addEnchantment(Enchantment.DURABILITY, 1).build()

    /**
     * Represents a sniper ammo item.
     *
     * This item is used for shooting with the sniper.
     * It has a display name with the prefix "TheHunter" and the color yellow.
     * It also has a lore describing its usage.
     * Additionally, it has an enchantment of durability level 1.
     */
    val sniperAmmo = ItemBuilder(Material.ENDER_PEARL).addDisplayName(TheHunter.prefix + "§6Sniper-Ammo")
        .addLore("§7Is used to shoot with the Sniper").addEnchantment(Enchantment.DURABILITY, 1).build()

}

package de.carina.thehunter.items.chest

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

object AmmoItems {
    val akAmmo = ItemBuilder(Material.FIRE_CHARGE).addDisplayName(TheHunter.prefix + "§6AK-Ammo").addLore("§7Is used to shoot with the Ak").addEnchantment(Enchantment.DURABILITY, 1).build()
    val minigunAmmo = ItemBuilder(Material.SNOWBALL).addDisplayName(TheHunter.prefix + "§6Minigun-Ammo").addLore("§7Is used to shoot with the Minigun").addEnchantment(Enchantment.DURABILITY, 1).build()
    val pistolAmmo = ItemBuilder(Material.ARROW).addDisplayName(TheHunter.prefix + "§6Pistol-Ammo").addLore("§7Is used to shoot with the Pistol").addEnchantment(Enchantment.DURABILITY, 1).build()
    val sniperAmmo = ItemBuilder(Material.ENDER_PEARL).addDisplayName(TheHunter.prefix + "§6Sniper-Ammo").addLore("§7Is used to shoot with the Sniper").addEnchantment(Enchantment.DURABILITY, 1).build()

}
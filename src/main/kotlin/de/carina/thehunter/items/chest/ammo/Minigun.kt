package de.carina.thehunter.items.chest.ammo

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class Minigun {
    companion object {
        fun createMinigunAmmo(): ItemStack {
            return ItemBuilder(Material.SNOWBALL).addDisplayName(TheHunter.PREFIX + "§6Minigun-Ammo").addLore("§7Is used to shoot with the Minigun").addEnchantment(Enchantment.DURABILITY, 1).build()
        }
    }


}
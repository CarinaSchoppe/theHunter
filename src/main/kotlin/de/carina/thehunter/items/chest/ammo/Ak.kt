package de.carina.thehunter.items.chest.ammo

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class Ak {
    companion object {
        fun createAKAmmo(): ItemStack {
            return ItemBuilder(Material.FIRE_CHARGE).addDisplayName(TheHunter.PREFIX + "§6AK-Ammo").addLore("§7Is used to shoot with the Ak").addEnchantment(Enchantment.DURABILITY, 1).build()
        }
    }
}
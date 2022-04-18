package de.carina.thehunter.items.chest.ammo

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class Pistol {

    companion object {
        fun createPistolAmmo(): ItemStack {
            return ItemBuilder(Material.ARROW).addDisplayName(TheHunter.PREFIX + "§6Pistol-Ammo").addLore("§7Is used to shoot with the Pistol").addEnchantment(Enchantment.DURABILITY, 1).build()
        }
    }


}
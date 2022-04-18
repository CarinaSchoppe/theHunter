package de.carina.thehunter.items.chest.ammo

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class Sniper {

    companion object {
        fun createSniperAmmo(): ItemStack {
            return ItemBuilder(Material.ENDER_PEARL).addDisplayName(TheHunter.PREFIX + "§6Sniper-Ammo").addLore("§7Is used to shoot with the Sniper").addEnchantment(Enchantment.DURABILITY, 1).build()
        }
    }


}
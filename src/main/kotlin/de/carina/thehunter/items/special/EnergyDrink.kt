/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "EnergyDrink.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.items.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.items.ItemHandler
import de.carina.thehunter.util.builder.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


class EnergyDrink : Listener {

    companion object {
        val energyDrink =
            ItemBuilder(Material.POTION).addDisplayName(TheHunter.prefix + "§6Energy-Drink").addLore("§7Energie- gives you some effects on right-clicking").addEnchantment(Enchantment.DURABILITY, 1).build()


    }

    @EventHandler
    fun onEnergyDinkDrink(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, energyDrink, "EnergyDrink"))
            return

        event.isCancelled = true
        ItemHandler.removeOneItemOfPlayer(event.player)
        event.player.addPotionEffects(createAndAddPotionEffects())
        event.player.sendMessage(TheHunter.instance.messages.messagesMap["energydrink-consumed"]!!)

    }

    private fun createAndAddPotionEffects(): MutableSet<PotionEffect> {
        val potionsEffects = mutableSetOf<PotionEffect>()
        val saturation = PotionEffect(PotionEffectType.SATURATION, 2 * 20, 2)
        val speed = PotionEffect(PotionEffectType.SPEED, 22 * 20, 1)
        val regeneration = PotionEffect(PotionEffectType.REGENERATION, 3 * 20, 1)
        val absorption = PotionEffect(PotionEffectType.ABSORPTION, 25 * 20, 1)
        val nightVision = PotionEffect(PotionEffectType.NIGHT_VISION, 35 * 20, 1)
        val jump = PotionEffect(PotionEffectType.JUMP, 25 * 20, 2)
        val dolphinsGrace = PotionEffect(PotionEffectType.DOLPHINS_GRACE, 30 * 20, 2)
        val glowing = PotionEffect(PotionEffectType.GLOWING, 25 * 20, 1)
        val waterBreathing = PotionEffect(PotionEffectType.WATER_BREATHING, 25 * 20, 1)
        potionsEffects.add(saturation)
        potionsEffects.add(speed)
        potionsEffects.add(regeneration)
        potionsEffects.add(absorption)
        potionsEffects.add(nightVision)
        potionsEffects.add(jump)
        potionsEffects.add(dolphinsGrace)
        potionsEffects.add(glowing)
        potionsEffects.add(waterBreathing)
        return potionsEffects
    }
}

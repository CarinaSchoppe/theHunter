/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "EnergyDrink.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.items.chestitems

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.util.ItemHandler
import de.pixels.thehunter.util.builder.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


class EnergyDrink : Listener {

    companion object {
        /**
         * Represents an energy drink item.
         *
         * This item provides certain effects when right-clicked with.
         * It is created using the ItemBuilder class and has a display name of "Energy-Drink" with a prefix of "TheHunter",
         * a lore describing its effect, and an enchantment of durability level 1.
         *
         * @param energyDrink  The energy drink item.
         */
        val energyDrink =
            ItemBuilder(Material.POTION).addDisplayName(TheHunter.prefix + "§6Energy-Drink")
                .addLore("§7Energie- gives you some effects on right-clicking")
                .addEnchantment(Enchantment.DURABILITY, 1).build()


    }

    /**
     * Event handler method for when a player interacts with an Energy Drink item.
     *
     * This method is responsible for handling the event of a player interacting with an Energy Drink item.
     * It checks if the interaction with the item should be allowed or not, based on the provided event.
     * If the interaction is not allowed, the method returns immediately.
     * Otherwise, it cancels the event, removes one Energy Drink item from the player's inventory,
     * applies the appropriate potion effects, and sends a consumed message to the player.
     *
     * @param event The PlayerInteractEvent object representing the interaction event.
     */
    @EventHandler
    fun onEnergyDinkDrink(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, energyDrink, "EnergyDrink"))
            return

        event.isCancelled = true
        ItemHandler.removeOneItemOfPlayer(event.player)
        event.player.addPotionEffects(createAndAddPotionEffects())
        TheHunter.instance.messagesFile.messagesMap["energydrink-consumed"]?.let { event.player.sendMessage(it) }

    }

    /**
     * Creates and adds various PotionEffects to a mutable set.
     *
     * @return The mutable set containing the created PotionEffects.
     */
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

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 11:16 PM All contents of "Knife.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.items.special

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.management.GamesHandler
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class Knife : Listener {
    companion object {
        /**
         * Represents a knife item.
         *
         * @property knife The instance of the knife item.
         *
         * @constructor Creates a knife item using the ItemBuilder class.
         */
        val knife = ItemBuilder(Material.IRON_SWORD)
            .addDisplayName(TheHunter.prefix + "§eKnife")
            .addLore(listOf("§6Knife to kill", "§eLeft-Click to use")).addEnchantment(Enchantment.DURABILITY, 1)
            .build()
    }


    /**
     * Handle the event of a player using a knife to damage another entity.
     *
     * @param event The EntityDamageByEntityEvent representing the knife usage event.
     */
    @EventHandler
    fun onKnifeUse(event: EntityDamageByEntityEvent) {

        if (event.entity !is Player || event.damager !is Player || (event.damager as Player).inventory.itemInMainHand.itemMeta != knife.itemMeta || (event.damager as Player).inventory.itemInMainHand.itemMeta != knife.itemMeta || !GamesHandler.playerInGames.containsKey(event.entity as Player))
            return
        val game = GamesHandler.playerInGames[event.entity as Player]

        if (GamesHandler.playerInGames[event.entity as Player]?.players?.contains(event.damager as Player) == false || GamesHandler.playerInGames[event.entity as Player]?.currentGameState !is IngameState || GamesHandler.playerInGames[event.damager as Player]?.gameItems?.items?.get("Knife") == false)
            return

        event.damage = game?.itemSettings?.settingsMap?.get("knife-damage") as Double
    }


}

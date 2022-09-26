/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 11:16 PM All contents of "Knife.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.items.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class Knife : Listener {
    companion object {
        val knife = ItemBuilder(Material.IRON_SWORD)
            .addDisplayName(TheHunter.prefix + "§eKnife")
            .addLore(listOf("§6Knife to kill", "§eLeft-Click to use")).addEnchantment(Enchantment.DURABILITY, 1)
            .build()
    }


    @EventHandler
    fun onKnifeUse(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player)
            return
        if (event.damager !is Player)
            return
        if ((event.damager as Player).inventory.itemInMainHand.itemMeta != knife.itemMeta)
            return
        if ((event.damager as Player).inventory.itemInMainHand.itemMeta != knife.itemMeta)
            return
        if (!GamesHandler.playerInGames.containsKey(event.entity as Player))
            return
        val game = GamesHandler.playerInGames[event.entity as Player]
        if (!GamesHandler.playerInGames[event.entity as Player]!!.players.contains(event.damager as Player))
            return

        if (GamesHandler.playerInGames[event.entity as Player]!!.currentGameState !is IngameState)
            return
        if (GamesHandler.playerInGames[event.damager as Player]!!.gameItems.items["Knife"] == false)
            return

        event.damage = game!!.itemSettings.settingsMap["knife-damage"] as Double
    }


}

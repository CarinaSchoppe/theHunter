/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 19:07 by Carina The Latest changes made by Carina on 19.04.22, 19:07 All contents of "EggBomb.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Egg
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class EggBomb : Listener {

    companion object {
        val bombs = mutableSetOf<TNTPrimed>()
        fun createEggBomb(): ItemStack {
            return ItemBuilder(Material.EGG).addDisplayName(TheHunter.PREFIX + "§eEggBomb").addAmount(Random().nextInt(1, (TheHunter.instance.itemSettings.settingsMap["egg-bomb-amount"] as Int) + 1)).addEnchantment(Enchantment.DURABILITY, 1).addLore(
                listOf(TheHunter.instance.messages.messagesMap["egg-bomb-message"]!!.replace("%power%", TheHunter.instance.itemSettings.settingsMap["egg-bomb-radius"].toString()))
            ).build()
        }
    }


    @EventHandler
    fun onEggBomb(event: ProjectileHitEvent) {
        if (event.entity !is Egg)
            return
        val egg = event.entity as Egg
        if (egg.shooter !is org.bukkit.entity.Player)
            return
        val player = egg.shooter as org.bukkit.entity.Player
        //Check if the egg is the same egg as an EggBomb item
        if (!player.hasPermission("theHunter.eggbomb"))
            return
        if (!GamesHandler.playerInGames.containsKey(player))
            return

        val game = GamesHandler.playerInGames[player]!!
        if (game.currentGameState !is IngameState)
            return
        if (!egg.item.hasItemMeta())
            return
        if (egg.item.itemMeta != createEggBomb().itemMeta)
            return
        if (GamesHandler.playerInGames[player]!!.gameItems.items["EggBomb"] == false)
            return


        Bukkit.getScheduler().runTaskLater(TheHunter.instance, Runnable {
            val tnt = event.entity.location.world.spawn(event.entity.location, org.bukkit.entity.TNTPrimed::class.java)
            tnt.fuseTicks = 0
            tnt.source = egg
            bombs.add(tnt)
            GamesHandler.entitiesInGames[tnt] = game
        }, (TheHunter.instance.itemSettings.settingsMap["egg-bomb-delay"] as Int) * 20L)
    }


}
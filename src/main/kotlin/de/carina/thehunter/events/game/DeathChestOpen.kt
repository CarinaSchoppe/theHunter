/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "DeathChest.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.events.game

import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.Inventory

class DeathChestOpen : Listener {


    private fun worldEquals(location: Location, other: Location): Boolean {
        if (location.world.name != other.world.name) return false

        if (location.x.toInt() != other.x.toInt()) return false
        if (location.y.toInt() != other.y.toInt()) return false
        return location.z.toInt() == other.z.toInt()
    }

    @EventHandler
    fun onOpenDeathChest(event: PlayerInteractEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player)) return
        if (event.clickedBlock?.type != Material.REDSTONE_LAMP) return
        val inventory: Inventory = GamesHandler.playerInGames[event.player]!!.deathChest.deathChests[GamesHandler.playerInGames[event.player]!!.deathChest.deathChests.keys.first {
            worldEquals(event.clickedBlock!!.location, it)
        }] ?: return



        println("location:" + Location(event.clickedBlock!!.location.world, event.clickedBlock!!.location.x.toInt().toDouble(), event.clickedBlock!!.location.y.toInt().toDouble(), event.clickedBlock!!.location.z.toInt().toDouble()).toString())
        println("should been:" + GamesHandler.playerInGames[event.player]!!.deathChest.deathChests.keys.toList().first())


        event.isCancelled = true

        event.player.openInventory(inventory!!)
        event.player.playSound(event.player, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
    }

}

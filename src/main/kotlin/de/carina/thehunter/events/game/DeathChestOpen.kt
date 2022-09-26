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
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class DeathChestOpen :


    Listener {


    @EventHandler
    fun onOpenDeathChest(event: PlayerInteractEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player)) return
        if (event.action.isLeftClick) return
        if (event.clickedBlock == null) return
        if (event.clickedBlock!!.type != Material.CHEST) return
        if (!GamesHandler.playerInGames[event.player]!!.deathChest.deathChests.containsKey(event.clickedBlock!!.location)) return
        event.isCancelled = true
        event.player.openInventory(GamesHandler.playerInGames[event.player]!!.deathChest.deathChests[event.clickedBlock!!.location]!!)
        event.player.playSound(event.player, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
    }

}

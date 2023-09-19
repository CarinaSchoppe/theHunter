/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:46 PM All contents of "DeathChestOpen.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.events.ingame.misc

import de.pixels.thehunter.util.game.management.GamesHandler
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.Inventory

class DeathChestHandler : Listener {

    /**
     * Checks if two locations in the same world have the same coordinates.
     *
     * @param location the first location
     * @param other the second location
     * @return true if the locations have the same world and coordinates, false otherwise
     */
    private fun worldEquals(location: Location, other: Location): Boolean {
        if (location.world.name != other.world.name || location.x.toInt() != other.x.toInt() || location.y.toInt() != other.y.toInt()) return false
        return location.z.toInt() == other.z.toInt()
    }

    /**
     * Event handler for opening a death chest when a player interacts with a redstone lamp.
     *
     * @param event The PlayerInteractEvent triggered when a player interacts with an entity or block.
     * @throws IllegalStateException If the player is not in a game or the clicked block is not a redstone lamp.
     */
    @EventHandler
    fun onOpenDeathChest(event: PlayerInteractEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player) || event.clickedBlock?.type != Material.REDSTONE_LAMP) return
        val inventory: Inventory =
            GamesHandler.playerInGames[event.player]?.deathChest?.deathChests?.get(GamesHandler.playerInGames[event.player]?.deathChest?.deathChests?.keys?.first {
                worldEquals(event.clickedBlock?.location ?: return, it)
            }) ?: return
        event.isCancelled = true

        event.player.openInventory(inventory)
        event.player.playSound(event.player, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
    }


}

package de.carina.thehunter.events.game

import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class DeathChest : Listener {


    @EventHandler
    fun onOpenDeathChest(event: PlayerInteractEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player)) return

        if (event.action.isLeftClick) return
        if (event.clickedBlock == null) return
        if (event.clickedBlock!!.type != Material.CHEST) return
        if (!GamesHandler.playerInGames[event.player]!!.deathChests.chests.containsKey(event.clickedBlock!!.location)) return
        event.player.openInventory(GamesHandler.playerInGames[event.player]!!.deathChests.chests[event.clickedBlock!!.location]!!)
    }

}
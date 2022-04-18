package de.carina.thehunter.events.game

import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class ChestHandler : Listener {
    @EventHandler
    fun onChestOpen(event: PlayerInteractEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
            return

        if (event.clickedBlock == null)
            return
        if (event.clickedBlock!!.type != org.bukkit.Material.BEACON)
            return

        val game = GamesHandler.playerInGames[event.player]!!
        if (game.gameChest.chests.containsKey(event.clickedBlock!!.location)) {
            event.player.openInventory(game.gameChest.chests[event.clickedBlock!!.location]!!)
        } else {
            val inventory = game.gameChest.createItemInventory()
            game.gameChest.chests[event.clickedBlock!!.location] = inventory
            event.player.openInventory(inventory)
        }

        event.isCancelled = true


    }
}
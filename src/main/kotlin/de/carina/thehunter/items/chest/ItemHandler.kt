package de.carina.thehunter.items.chest

import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object ItemHandler {

    fun shouldInteractWithItem(event: PlayerInteractEvent, item: ItemStack, itemString: String): Boolean {
        if (event.item == null)
            return false
        if (!event.item!!.hasItemMeta()) return false
        if (event.item!!.itemMeta != item.itemMeta)
            return false
        if (event.player.inventory.itemInMainHand.itemMeta != item.itemMeta)
            return false
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return false
        if (!event.action.isRightClick)
            return false

        if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
            return false

        if (GamesHandler.playerInGames[event.player]!!.gameItems!!.items[itemString] == false)
            return false

        event.isCancelled = true
        return true
    }
}
package de.carina.thehunter.events.misc

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class MapModify : Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player = event.player
        if (!GamesHandler.playerInGames.containsKey(player))
            return

        if (GamesHandler.playerInGames[player]!!.currentGameState !is IngameState) {
            event.isCancelled = true
            player.sendMessage(TheHunter.instance.messages.messagesMap["cant-break-block"]!!.replace("%block%", event.block.type.name))
            return
        }
        if (!GamesHandler.playerInGames[player]!!.mapModify) {
            event.isCancelled = true
            player.sendMessage(TheHunter.instance.messages.messagesMap["cant-break-block"]!!.replace("%block%", event.block.type.name))
            return
        }
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        if (!GamesHandler.playerInGames.containsKey(player))
            return

        if (GamesHandler.playerInGames[player]!!.currentGameState !is IngameState) {
            event.isCancelled = true
            player.sendMessage(TheHunter.instance.messages.messagesMap["cant-place-block"]!!.replace("%block%", event.block.type.name))
            return
        }
        if (!GamesHandler.playerInGames[player]!!.mapModify) {
            event.isCancelled = true
            player.sendMessage(TheHunter.instance.messages.messagesMap["cant-place-block"]!!.replace("%block%", event.block.type.name))
            return
        }
    }
}
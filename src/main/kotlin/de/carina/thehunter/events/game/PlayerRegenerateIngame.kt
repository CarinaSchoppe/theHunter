package de.carina.thehunter.events.game

import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityRegainHealthEvent

class PlayerRegenerateIngame : Listener {

    @EventHandler
    fun playerRegenerateIngame(event: EntityRegainHealthEvent) {
        if (event.entity !is Player)
            return
        if (!GamesHandler.playerInGames.containsKey(event.entity))
            return
        if (!GamesHandler.playerInGames[event.entity]!!.regenerate)
            event.isCancelled = true
    }
}

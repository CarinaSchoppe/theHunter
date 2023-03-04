/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 3:09 PM All contents of "PlayerRegenerateIngame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.events.player

import de.pixels.thehunter.util.game.GamesHandler
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

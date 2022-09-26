/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "PlayerJoinsServer.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.events.misc

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinsServer : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerJoinServer(event: PlayerJoinEvent) {
        event.player.inventory.clear()
        TheHunter.instance.statsSystem.generateNewStatsPlayer(event.player)
        GamesHandler.playerInGames.keys.forEach {
            it.hidePlayer(TheHunter.instance, event.player)
            event.player.hidePlayer(TheHunter.instance, it)
        }
        GamesHandler.spectatorInGames.keys.forEach {
            it.hidePlayer(TheHunter.instance, event.player)
            event.player.hidePlayer(TheHunter.instance, it)
        }

    }
}

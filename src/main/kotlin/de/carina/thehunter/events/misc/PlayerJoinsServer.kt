/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 16.04.22, 12:17 by Carina The Latest changes made by Carina on 16.04.22, 12:04 All contents of "PlayerJoinsServer.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.misc

import de.carina.thehunter.TheHunter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinsServer : Listener {

    @EventHandler
    fun onPlayerJoinServer(event: PlayerJoinEvent) {
        TheHunter.instance.statsSystem.generateNewStatsPlayer(event.player)

    }
}
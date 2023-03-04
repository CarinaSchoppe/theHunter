/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 7:20 PM All contents of "PlayerDisconnects.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.events.misc

import de.pixels.thehunter.util.misc.DeathHandler
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerDisconnectHandler : Listener {

    @EventHandler
    fun onPlayerDisconnect(event: PlayerQuitEvent) {

        DeathHandler(event.player).deathPreChecks()?.squeezeIns { run { event.quitMessage(Component.text { "" }) } }
            ?.deathMessageToAll()?.playerDiedStatsHandling()?.deathChestCreation()?.afterDeathPlayerHandling()
            ?.afterDeathChecks()
    }

}

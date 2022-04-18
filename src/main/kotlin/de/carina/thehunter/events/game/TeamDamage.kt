/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "TeamDamage.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.game

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class TeamDamage : Listener {

    @EventHandler
    fun onTeamDamage(event: EntityDamageByEntityEvent) {
        if (event.damager !is Player)
            return
        if (event.entity !is Player)
            return
        val damager = event.damager as Player
        val entity = event.entity as Player
        if (!GamesHandler.playerInGames.containsKey(damager))
            return
        if (!GamesHandler.playerInGames.containsKey(entity))
            return
        if (GamesHandler.playerInGames[damager] != GamesHandler.playerInGames[entity])
            return

        val team = GamesHandler.playerInGames[damager]!!.teams.find { it.teamMembers.contains(damager) } ?: return
        if (!team.teamMembers.contains(entity))
            return
        if (GamesHandler.playerInGames[damager]!!.teamDamage)
            return
        event.isCancelled = true
        event.damage = 0.0
        damager.sendMessage(TheHunter.instance.messages.messagesMap["cant-team-damage"]!!)
    }
}
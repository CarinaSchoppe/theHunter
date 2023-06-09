/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:46 PM All contents of "TeamDamage.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.events.player

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.game.management.GamesHandler
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class TeamDamage : Listener {

    @EventHandler
    fun onTeamDamage(event: EntityDamageByEntityEvent) {


        if (event.damager !is Player || event.entity !is Player)
            return

        val damager = event.damager as Player
        val entity = event.entity as Player
        if (!GamesHandler.playerInGames.containsKey(damager) || !GamesHandler.playerInGames.containsKey(entity) || GamesHandler.playerInGames[damager] != GamesHandler.playerInGames[entity])
            return
        val team = GamesHandler.playerInGames[damager]?.teams?.find { it.teamMembers.contains(damager) } ?: return
        if (!team.teamMembers.contains(entity) || GamesHandler.playerInGames[damager]?.teamDamage == true)
            return

        event.isCancelled = true
        event.damage = 0.0

        TheHunter.instance.messages.messagesMap["cant-team-damage"]?.let { damager.sendMessage(it) }
    }
}

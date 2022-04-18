package de.carina.thehunter.events.game

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class TeamDamage : Listener {
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
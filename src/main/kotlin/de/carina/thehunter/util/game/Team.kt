/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 14.04.22, 00:24 by Carina The Latest changes made by Carina on 14.04.22, 00:24 All contents of "Team.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.game

import de.carina.thehunter.TheHunter
import org.bukkit.entity.Player

class Team(var teamLeader: Player) {


    val invites = mutableSetOf<Player>()
    val teamMembers = mutableSetOf<Player>()
    lateinit var game: Game
    fun inviteTeamMember(playerToAdd: Player, leader: Player): Boolean {
        if (teamLeader != leader) {
            TheHunter.instance.messages.sendMessageToPlayer(leader, "team-player-not-leader")
            return false
        }
        if (teamMembers.contains(playerToAdd)) {
            TheHunter.instance.messages.sendMessageToPlayer(leader, "team-player-already-in-team")
            return false
        }

        if (!GamesHandler.playerInGames.containsKey(playerToAdd)) {
            leader.sendMessage(TheHunter.instance.messages.messagesMap["player-not-in-game"]!!.replace("%player%", playerToAdd.name))
            return false
        }

        GamesHandler.playerInGames[playerToAdd]!!.teams.forEach {
            if (it.teamMembers.contains(playerToAdd)) {
                leader.sendMessage(TheHunter.instance.messages.messagesMap["team-player-already-in-other-team"]!!.replace("%player%", playerToAdd.name))
                return false
            }
        }




        if (!leader.hasPermission("theHunter.teams.invite")) {
            TheHunter.instance.messages.sendMessageToPlayer(leader, "player-not-permissions")
            return false
        }


        if (invites.contains(playerToAdd)) {
            leader.sendMessage(TheHunter.instance.messages.messagesMap["player-already-inivited"]!!.replace("%player%", playerToAdd.name))
            return false
        }

        invites.add(playerToAdd)
        playerToAdd.sendMessage(TheHunter.instance.messages.messagesMap["player-is-invited"]!!.replace("%leader%", leader.name))
        leader.sendMessage(TheHunter.instance.messages.messagesMap["player-invited"]!!.replace("%player%", playerToAdd.name))

        return true
    }


}
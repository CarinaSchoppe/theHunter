/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:32 PM All contents of "Team.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.util.game

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.misc.ConstantStrings
import de.carina.thehunter.util.misc.Permissions
import org.bukkit.entity.Player

class Team(var teamLeader: Player) {
    val invites = mutableSetOf<Player>()
    val teamMembers = mutableSetOf<Player>()
    lateinit var game: Game
    private fun inviteTeamMember(playerToAdd: Player, leader: Player, game: Game): Boolean {
        if (!game.teamsAllowed) {
            TheHunter.instance.messages.sendMessageToPlayer(leader, "teams-not-allowed")
            return false
        }

        if (teamLeader != leader) {
            TheHunter.instance.messages.sendMessageToPlayer(leader, "team-player-not-leader")
            return false
        }
        if (teamMembers.contains(playerToAdd)) {
            TheHunter.instance.messages.sendMessageToPlayer(leader, "team-player-already-in-own-team".replace(ConstantStrings.PLAYER_PERCENT, playerToAdd.name))
            return false
        }

        if (!GamesHandler.playerInGames.containsKey(playerToAdd) && !GamesHandler.spectatorInGames.containsKey(playerToAdd)) {
            leader.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_GAME]!!.replace(ConstantStrings.PLAYER_SPAWN, playerToAdd.name))
            return false
        }

        (GamesHandler.playerInGames[playerToAdd] ?: GamesHandler.spectatorInGames[playerToAdd])!!.teams.forEach {
            if (it.teamMembers.contains(playerToAdd)) {
                leader.sendMessage(TheHunter.instance.messages.messagesMap["team-player-already-in-other-team"]!!.replace(ConstantStrings.PLAYER_PERCENT, playerToAdd.name))
                return false
            }
        }
        if (!leader.hasPermission(Permissions.TEAM_COMMAND)) {
            TheHunter.instance.messages.sendMessageToPlayer(leader, "player-not-permissions")
            return false
        }


        if (invites.contains(playerToAdd)) {
            leader.sendMessage(TheHunter.instance.messages.messagesMap["player-already-invited"]!!.replace(ConstantStrings.PLAYER_PERCENT, playerToAdd.name))
            return false
        }

        if (teamMembers.size >= game.teamMaxSize) {
            leader.sendMessage(TheHunter.instance.messages.messagesMap["team-full"]!!)
            return false
        }

        invites.add(playerToAdd)
        playerToAdd.sendMessage(TheHunter.instance.messages.messagesMap["player-is-invited"]!!.replace("%leader%", leader.name))
        leader.sendMessage(TheHunter.instance.messages.messagesMap["player-invited"]!!.replace(ConstantStrings.PLAYER_PERCENT, playerToAdd.name))

        return true
    }

    private fun promoteTeamLeader(player: Player, leader: Player) {
        if (!teamMembers.contains(player)) {
            leader.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_TEAM]!!.replace(ConstantStrings.PLAYER_PERCENT, player.name))
            return
        }
        if (teamLeader != leader) {
            leader.sendMessage(TheHunter.instance.messages.messagesMap["team-player-not-leader"]!!)
            return
        }

        teamLeader.sendMessage(TheHunter.instance.messages.messagesMap["player-promote-leader"]!!.replace(ConstantStrings.PLAYER_PERCENT, player.name))
        teamLeader = player
        teamLeader.sendMessage(TheHunter.instance.messages.messagesMap["player-new-leader"]!!)
        teamMembers.filter { it.name != player.name && it.name != leader.name }.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-new-leader-all"]!!.replace(ConstantStrings.PLAYER_PERCENT, teamLeader.name))
        }
    }


    companion object {
        fun teamsCleanUp(game: Game) {
            game.teams.forEach {
                it.invites.clear()
                if (it.teamMembers.size < 2)
                    game.teams.remove(it)
                it.teamMembers.first().performCommand("theHunter team leave")
            }
        }

        fun isTeamMember(player: Player, other: Player): Boolean {
            if ((GamesHandler.playerInGames.containsKey(player) || GamesHandler.spectatorInGames.containsKey(player)) && (GamesHandler.playerInGames[player] ?: GamesHandler.spectatorInGames[player])!!.teams.any { it.teamMembers.contains(other) }) {
                return true
            }
            return false
        }

        fun removePlayerFromTeam(player: Player, leader: Player) {

            val game = GamesHandler.playerInGames[player] ?: GamesHandler.spectatorInGames[player] ?: return
            val team = game.teams.find { it.teamMembers.contains(player) }
            if (team == null) {
                player.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_TEAM]!!)
                return
            }
            if (leader != player && team.teamLeader != leader) {
                leader.sendMessage(TheHunter.instance.messages.messagesMap["player-not-leader"]!!)
                return
            }
            team.teamMembers.remove(player)
            player.sendMessage(TheHunter.instance.messages.messagesMap["player-left-team"]!!)
            if (team.teamMembers.isEmpty()) {
                (GamesHandler.playerInGames[player] ?: GamesHandler.spectatorInGames[player])!!.teams.remove(team)
                return
            }
            if (team.teamLeader == player) {
                team.teamLeader = team.teamMembers.first()
                team.teamLeader.sendMessage(TheHunter.instance.messages.messagesMap["player-new-leader"]!!)
                team.teamMembers.forEach {
                    it.sendMessage(TheHunter.instance.messages.messagesMap["player-new-leader-all"]!!.replace(ConstantStrings.PLAYER_PERCENT, team.teamLeader.name))
                }
            } else {
                team.teamMembers.forEach {
                    it.sendMessage(TheHunter.instance.messages.messagesMap["player-left-team-all"]!!.replace(ConstantStrings.PLAYER_PERCENT, player.name))
                }
            }

        }

        fun invitePlayerToTeam(playerToInvite: Player, leader: Player) {
            val game = GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader] ?: return
            val team = game.teams.find { it.teamMembers.contains(leader) }
            if (team != null) {
                team.inviteTeamMember(playerToInvite, leader, GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader]!!)
            } else {
                (GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader])!!.teams.add(Team(leader))
                (GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader])!!.teams.last().teamMembers.add(leader)
                (GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader])!!.teams.last().inviteTeamMember(playerToInvite, leader, GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader]!!)
            }
        }

        fun promoteNewTeamLeader(player: Player, leader: Player) {
            val game = GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[player] ?: return
            val team = game.teams.find { it.teamMembers.contains(leader) }
            if (team != null) {
                team.promoteTeamLeader(player, leader)
            } else {
                player.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_TEAM]!!)
            }
        }

    }


}

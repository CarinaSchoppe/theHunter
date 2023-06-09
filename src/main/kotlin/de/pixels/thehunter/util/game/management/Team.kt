/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:32 PM All contents of "Team.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.game.management

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
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
            TheHunter.instance.messages.sendMessageToPlayer(
                leader,
                "team-player-already-in-own-team".replace(ConstantStrings.PLAYER_PERCENT, playerToAdd.name)
            )
            return false
        }

        if (!GamesHandler.playerInGames.containsKey(playerToAdd) && !GamesHandler.spectatorInGames.containsKey(
                playerToAdd
            )
        ) {
            TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_GAME]?.let {
                leader.sendMessage(
                    it.replace(
                        ConstantStrings.PLAYER_SPAWN,
                        playerToAdd.name
                    )
                )
            }
            return false
        }

        (GamesHandler.playerInGames[playerToAdd] ?: GamesHandler.spectatorInGames[playerToAdd])?.teams?.forEach {
            if (it.teamMembers.contains(playerToAdd)) {
                TheHunter.instance.messages.messagesMap["team-player-already-in-other-team"]?.let { message ->
                    leader.sendMessage(
                        message.replace(
                            ConstantStrings.PLAYER_PERCENT,
                            playerToAdd.name
                        )
                    )
                }
                return false
            }
        }
        if (!leader.hasPermission(Permissions.TEAM_COMMAND)) {
            TheHunter.instance.messages.sendMessageToPlayer(leader, "player-not-permissions")
            return false
        }


        if (invites.contains(playerToAdd)) {
            TheHunter.instance.messages.messagesMap["player-already-invited"]?.let {
                leader.sendMessage(
                    it.replace(
                        ConstantStrings.PLAYER_PERCENT,
                        playerToAdd.name
                    )
                )
            }
            return false
        }

        if (teamMembers.size >= game.teamMaxSize) {
            TheHunter.instance.messages.messagesMap["team-full"]?.let { leader.sendMessage(it) }
            return false
        }

        invites.add(playerToAdd)
        TheHunter.instance.messages.messagesMap["player-is-invited"]?.let {
            playerToAdd.sendMessage(
                it.replace(
                    "%leader%",
                    leader.name
                )
            )
        }
        TheHunter.instance.messages.messagesMap["player-invited"]?.let {
            leader.sendMessage(
                it.replace(
                    ConstantStrings.PLAYER_PERCENT,
                    playerToAdd.name
                )
            )
        }

        return true
    }

    private fun promoteTeamLeader(player: Player, leader: Player) {
        if (!teamMembers.contains(player)) {
            TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_TEAM]?.let {
                leader.sendMessage(
                    it.replace(
                        ConstantStrings.PLAYER_PERCENT,
                        player.name
                    )
                )
            }
            return
        }
        if (teamLeader != leader) {
            TheHunter.instance.messages.messagesMap["team-player-not-leader"]?.let { leader.sendMessage(it) }
            return
        }

        TheHunter.instance.messages.messagesMap["player-promote-leader"]?.let {
            teamLeader.sendMessage(
                it.replace(
                    ConstantStrings.PLAYER_PERCENT,
                    player.name
                )
            )
        }
        teamLeader = player
        TheHunter.instance.messages.messagesMap["player-new-leader"]?.let { teamLeader.sendMessage(it) }
        teamMembers.filter { it.name != player.name && it.name != leader.name }.forEach {
            TheHunter.instance.messages.messagesMap["player-new-leader-all"]?.let { message ->
                it.sendMessage(
                    message.replace(
                        ConstantStrings.PLAYER_PERCENT,
                        teamLeader.name
                    )
                )
            }
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
            return (GamesHandler.playerInGames.containsKey(player) || GamesHandler.spectatorInGames.containsKey(player)) && (GamesHandler.playerInGames[player]
                ?: GamesHandler.spectatorInGames[player])?.teams?.any { it.teamMembers.contains(other) } ?: return false
        }

        fun removePlayerFromTeam(player: Player, leader: Player) {

            val game = GamesHandler.playerInGames[player] ?: GamesHandler.spectatorInGames[player] ?: return
            val team = game.teams.find { it.teamMembers.contains(player) }
            if (team == null) {
                TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_TEAM]?.let { player.sendMessage(it) }
                return
            }
            if (leader != player && team.teamLeader != leader) {
                TheHunter.instance.messages.messagesMap["player-not-leader"]?.let { leader.sendMessage(it) }
                return
            }
            team.teamMembers.remove(player)
            TheHunter.instance.messages.messagesMap["player-left-team"]?.let { player.sendMessage(it) }
            if (team.teamMembers.isEmpty()) {
                (GamesHandler.playerInGames[player] ?: GamesHandler.spectatorInGames[player])?.teams?.remove(team)
                return
            }
            if (team.teamLeader == player) {
                team.teamLeader = team.teamMembers.first()
                TheHunter.instance.messages.messagesMap["player-new-leader"]?.let { team.teamLeader.sendMessage(it) }
                team.teamMembers.forEach {
                    TheHunter.instance.messages.messagesMap["player-new-leader-all"]?.let { message ->
                        it.sendMessage(
                            message.replace(
                                ConstantStrings.PLAYER_PERCENT,
                                team.teamLeader.name
                            )
                        )
                    }
                }
            } else {
                team.teamMembers.forEach {
                    TheHunter.instance.messages.messagesMap["player-left-team-all"]?.let { message ->
                        it.sendMessage(
                            message.replace(
                                ConstantStrings.PLAYER_PERCENT,
                                player.name
                            )
                        )
                    }
                }
            }

        }

        fun invitePlayerToTeam(playerToInvite: Player, leader: Player) {
            val game = GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader] ?: return
            val team = game.teams.find { it.teamMembers.contains(leader) }
            if (team != null) {
                (GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader])?.let {
                    team.inviteTeamMember(
                        playerToInvite,
                        leader,
                        it
                    )
                }
            } else {
                GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader]?.teams?.add(Team(leader)) ?: return
                GamesHandler.playerInGames[leader]
                    ?: GamesHandler.spectatorInGames[leader]?.teams?.last()?.teamMembers?.add(leader)
                (GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader])?.let {
                    (GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[leader])?.teams?.last()
                        ?.inviteTeamMember(
                            playerToInvite,
                            leader,
                            it
                        ) ?: return
                }
            }
        }

        fun promoteNewTeamLeader(player: Player, leader: Player) {
            val game = GamesHandler.playerInGames[leader] ?: GamesHandler.spectatorInGames[player] ?: return
            val team = game.teams.find { it.teamMembers.contains(leader) }
            if (team != null) {
                team.promoteTeamLeader(player, leader)
            } else {
                TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_TEAM]?.let { player.sendMessage(it) }
            }
        }

    }


}

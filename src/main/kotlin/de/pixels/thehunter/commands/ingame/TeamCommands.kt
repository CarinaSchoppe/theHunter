/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:28 PM All contents of "TeamCommands.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.commands.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.commands.CommandUtil
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.game.management.Team
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TeamCommands {

    /**
     * Handles the team command.
     *
     * @param sender The command sender.
     * @param command The command name.
     * @param args The command arguments.
     */
    fun team(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.TEAM_COMMAND,
                1,
                Permissions.TEAM_COMMAND
            )
        )
            return

        if (!GamesHandler.playerInGames.containsKey(sender) && !GamesHandler.spectatorInGames.containsKey(sender)) {
            TheHunter.instance.messages.messagesMap["player-own-not-in-game"]?.let { sender.sendMessage(it) }
            return
        }
        when {
            args[0].lowercase() == ConstantStrings.LEAVE_COMMAND -> {
                Team.removePlayerFromTeam(sender as Player, sender)
            }

            args[0].lowercase() == ConstantStrings.INVITE_COMMAND -> {
                invite(sender as Player, args)
            }

            args[0].lowercase() == ConstantStrings.REMOVE_COMMAND -> {
                remove(sender as Player, args)
            }

            args[0].lowercase() == ConstantStrings.ACCEPT_COMMAND -> {
                accept(sender as Player, args)
            }

            args[0].lowercase() == ConstantStrings.PROMOTE_COMMAND -> {
                promote(sender as Player, args)
            }
        }
    }

    /**
     * Promotes a player to be the new team leader.
     *
     * @param sender the player who is executing the command
     * @param args the command arguments
     */
    private fun promote(sender: Player, args: Array<out String>) {
        val player = Bukkit.getPlayer(args[1])
        if (player == null) {
            TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_GAME]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.PLAYER_PERCENT,
                        args[1]
                    )
                )
            }
            return
        }
        Team.promoteNewTeamLeader(player, sender)
    }

    /**
     * Checks the arguments passed to the method to ensure they are valid.
     *
     * @param sender The player who executed the command.
     * @param args The arguments passed to the method.
     * @return true if the arguments are invalid, false otherwise.
     */
    private fun argumentChecker(sender: Player, args: Array<out String>): Boolean {
        if (args.size <= 1) {
            TheHunter.instance.messages.messagesMap[ConstantStrings.NOT_ENOUGH_ARGUMENTS]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.ARGUMENTS_PERCENT,
                        2.toString()
                    )
                )
            }
            return true
        }
        val player = Bukkit.getPlayer(args[1])
        if (player == null) {
            TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_GAME]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.PLAYER_PERCENT,
                        args[1]
                    )
                )
            }
            return true
        }

        return false
    }

    /**
     * Removes a player from a team.
     *
     * @param sender the player who executed the command
     * @param args the command arguments. The second argument is expected to be the target player name
     */
    private fun remove(sender: Player, args: Array<out String>) {
        if (argumentChecker(sender, args)) return
        Bukkit.getPlayer(args[1])?.let { Team.removePlayerFromTeam(it, sender) }
    }


    /**
     * Accepts an invitation to join a team in a game.
     *
     * @param sender the player who wants to accept the invitation
     * @param args the command arguments passed by the player
     */
    private fun accept(sender: Player, args: Array<out String>) {
        val game = GamesHandler.playerInGames[sender] ?: GamesHandler.spectatorInGames[sender] ?: return
        if (game.teams.find { it.teamMembers.contains(sender) } != null) {
            TheHunter.instance.messages.messagesMap["player-already-in-team"]?.let { sender.sendMessage(it) }
            return
        }



        if (args.size <= 1) {
            TheHunter.instance.messages.messagesMap[ConstantStrings.NOT_ENOUGH_ARGUMENTS]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.ARGUMENTS_PERCENT,
                        2.toString()
                    )
                )
            }
            return
        }
        val player = Bukkit.getPlayer(args[1])
        if (player == null) {
            TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_GAME]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.PLAYER_PERCENT,
                        args[1]
                    )
                )
            }
            return
        }

        val team = (GamesHandler.playerInGames[sender]
            ?: GamesHandler.spectatorInGames[sender])?.teams?.find { it.teamLeader.name == player.name }
        if (team == null) {
            TheHunter.instance.messages.messagesMap[ConstantStrings.PLAYER_NOT_IN_GAME]?.let { sender.sendMessage(it) }
            return
        }

        if (!team.invites.contains(sender)) {
            TheHunter.instance.messages.messagesMap["player-own-not-invited"]?.let { sender.sendMessage(it) }
            return
        }

        team.invites.remove(sender)
        team.teamMembers.forEach {
            TheHunter.instance.messages.messagesMap["player-joined-team-all"]?.let { str ->
                it.sendMessage(
                    str.replace(
                        ConstantStrings.PLAYER_PERCENT,
                        sender.name
                    )
                )
            }
        }
        team.teamMembers.add(sender)
        TheHunter.instance.messages.messagesMap["player-joined-team"]?.let { sender.sendMessage(it) }
    }


    /**
     * Invites a player to a team.
     *
     * @param sender the player sending the invite
     * @param args   the command arguments; the first argument is the command name, the second argument is the player to invite
     */
    private fun invite(sender: Player, args: Array<out String>) {
        if (argumentChecker(sender, args)) return
        Bukkit.getPlayer(args[1])?.let { Team.invitePlayerToTeam(it, sender) }
    }

}

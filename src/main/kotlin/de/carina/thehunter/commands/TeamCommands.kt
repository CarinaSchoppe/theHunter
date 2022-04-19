/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 13:09 by Carina The Latest changes made by Carina on 19.04.22, 13:09 All contents of "TeamCommands.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.game.Team
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class TeamCommands {

    fun team(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, "team", 1, "theHunter.team"))
            return

        if (!GamesHandler.playerInGames.containsKey(sender as Player))
            return
        if (args[0].lowercase() == "leave") {
            Team.removePlayerFromTeam(sender, sender)
        } else if (args[0].lowercase() == "invite") {
            invite(sender, args)
        } else if (args[0].lowercase() == "remove") {
            remove(sender, args)
        } else if (args[0].lowercase() == "accept") {
            accept(sender, args)
        } else if (args[0].lowercase() == "promote") {
            promote(sender, args)
        }
    }

    private fun promote(sender: Player, args: Array<out String>) {
        val player = Bukkit.getPlayer(args[1])
        if (player == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["player-not-in-game"]!!.replace("%player%", args[1]))
            return
        }
        Team.promoteNewTeamLeader(player, sender)
    }

    private fun remove(sender: Player, args: Array<out String>) {
        if (args.size == 1) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["not-enough-arguments"]!!)
            return
        }
        val player = Bukkit.getPlayer(args[1])
        if (player == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["player-not-in-game"]!!.replace("%player%", args[1]))
            return
        }
        Team.removePlayerFromTeam(player, sender)
    }


    private fun accept(sender: Player, args: Array<out String>) {
        if (GamesHandler.playerInGames[sender]!!.teams.find { it.teamMembers.contains(sender) } != null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["player-already-in-team"]!!)
            return
        }

        if (args.size == 1) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["not-enough-arguments"]!!)
            return
        }
        val player = Bukkit.getPlayer(args[1])
        if (player == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["player-not-in-game"]!!.replace("%player%", args[1]))
            return
        }

        val team = GamesHandler.playerInGames[sender]!!.teams.find { it.teamLeader.name == player.name }
        if (team == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["player-not-in-game"]!!)
            return
        }

        if (!team.invites.contains(sender)) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["player-own-not-invited"]!!)
            return
        }

        team.invites.remove(sender)
        team.teamMembers.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-joined-team-all"]!!.replace("%player%", sender.name))
        }
        team.teamMembers.add(sender)
        sender.sendMessage(TheHunter.instance.messages.messagesMap["player-joined-team"]!!)
    }


    private fun invite(sender: Player, args: Array<out String>) {
        if (args.size == 1) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["not-enough-arguments"]!!)
            return
        }
        val player = Bukkit.getPlayer(args[1])
        if (player == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["player-not-in-game"]!!.replace("%player%", args[1]))
            return
        }
        Team.invitePlayerToTeam(player, sender)
    }

}

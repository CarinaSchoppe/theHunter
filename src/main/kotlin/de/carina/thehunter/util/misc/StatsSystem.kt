/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 16.04.22, 12:02 by Carina The Latest changes made by Carina on 16.04.22, 12:02 All contents of "StatsSystem.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.misc

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.files.BaseFile
import org.bukkit.entity.Player
import java.util.*


data class StatsPlayer(var kills: Int, var deaths: Int, var points: Int, var KDR: Double, var wins: Int, var loses: Int, var games: Int)


class StatsSystem : BaseFile("stats.yml") {

    companion object {
        val playerStats = mutableMapOf<UUID, StatsPlayer>()
        fun saveAllStatsPlayerToFiles() {
            for (player in playerStats.keys) {
                TheHunter.instance.statsSystem.yml.set("$player.Kills", playerStats[player]!!.kills)
                TheHunter.instance.statsSystem.yml.set("$player.Deaths", playerStats[player]!!.deaths)
                TheHunter.instance.statsSystem.yml.set("$player.KDR", playerStats[player]!!.KDR)
                TheHunter.instance.statsSystem.yml.set("$player.Wins", playerStats[player]!!.wins)
                TheHunter.instance.statsSystem.yml.set("$player.Loses", playerStats[player]!!.loses)
                TheHunter.instance.statsSystem.yml.set("$player.Points", playerStats[player]!!.points)
                TheHunter.instance.statsSystem.yml.set("$player.Games", playerStats[player]!!.games)
                TheHunter.instance.statsSystem.saveFile()
                TheHunter.instance.messages.sendMessageToConsole("stats-system-saved")
            }
        }
    }

    fun generateNewStatsPlayer(player: Player) {
        yml.set(player.uniqueId.toString() + ".Kills", 0)
        yml.set(player.uniqueId.toString() + ".Deaths", 0)
        yml.set(player.uniqueId.toString() + ".KDR", 0.0)
        yml.set(player.uniqueId.toString() + ".Wins", 0)
        yml.set(player.uniqueId.toString() + ".Loses", 0)
        yml.set(player.uniqueId.toString() + ".Points", 0)
        yml.set(player.uniqueId.toString() + ".Games", 0)
        playerStats[player.uniqueId] = StatsPlayer(0, 0, 0, 0.0, 0, 0, 0)
        saveFile()
    }


    fun playerKilledOtherPlayer(killer: Player, dead: Player) {
        removePointsIfPossible(dead)
        playerStats[killer.uniqueId]!!.kills += 1
        playerStats[killer.uniqueId]!!.points += 5
        playerStats[killer.uniqueId]!!.KDR = playerStats[killer.uniqueId]!!.kills.toDouble() / playerStats[killer.uniqueId]!!.deaths.toDouble()
        playerStats[dead.uniqueId]!!.deaths += 1
        playerStats[dead.uniqueId]!!.KDR = playerStats[dead.uniqueId]!!.kills.toDouble() / playerStats[dead.uniqueId]!!.deaths.toDouble()

        saveFile()
    }

    fun playerWon(player: Player) {
        yml.set(player.uniqueId.toString() + ".Wins", yml.getInt(player.uniqueId.toString() + ".Wins") + 1)
        yml.set(player.uniqueId.toString() + ".Points", yml.getInt(player.uniqueId.toString() + ".Points") + 15)
        playerStats[player.uniqueId]!!.wins += 1
        playerStats[player.uniqueId]!!.points += 15
        saveFile()
    }

    fun playerDied(player: Player) {
        removePointsIfPossible(player)
        playerStats[player.uniqueId]!!.deaths += 1
        playerStats[player.uniqueId]!!.KDR = playerStats[player.uniqueId]!!.kills.toDouble() / playerStats[player.uniqueId]!!.deaths.toDouble()
        saveFile()
    }

    private fun removePointsIfPossible(player: Player) {
        if (playerStats[player.uniqueId]!!.points >= 5) {
            playerStats[player.uniqueId]!!.points -= 5
        }
    }

    fun playerPlaysGame(player: Player) {
        playerStats[player.uniqueId]!!.games += 1
        saveFile()
    }

    fun generateStatsMessageForPlayer(sender: Player, player: Player): Boolean {
        if (playerStats[player.uniqueId] == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["stats-not-found"]!!.replace("%player%", player.name).replace("%player%", player.name))
            return false
        }

        if (sender.uniqueId == player.uniqueId)
            sender.sendMessage(
                TheHunter.instance.messages.messagesMap["stats-message-own"]!!.replace("%kills%", playerStats[player.uniqueId]!!.kills.toString())
                    .replace("%deaths%", playerStats[player.uniqueId]!!.deaths.toString()).replace("%wins%", playerStats[player.uniqueId]!!.wins.toString())
                    .replace("%games%", playerStats[player.uniqueId]!!.games.toString()).replace("%points%", playerStats[player.uniqueId]!!.points.toString())
                    .replace("%kdr%", playerStats[player.uniqueId]!!.KDR.toString())
                    .replace("%losses%", playerStats[player.uniqueId]!!.loses.toString())
            )
        else
            sender.sendMessage(
                TheHunter.instance.messages.messagesMap["stats-message-other"]!!
                    .replace("%kills%", playerStats[player.uniqueId]!!.kills.toString())
                    .replace("%deaths%", playerStats[player.uniqueId]!!.deaths.toString())
                    .replace("%wins%", playerStats[player.uniqueId]!!.wins.toString())
                    .replace("%games%", playerStats[player.uniqueId]!!.games.toString())
                    .replace("%points%", playerStats[player.uniqueId]!!.points.toString())
                    .replace("%kdr%", playerStats[player.uniqueId]!!.KDR.toString())
                    .replace("%losses%", playerStats[player.uniqueId]!!.loses.toString())
                    .replace("%player%", player.name)
            )


        return true
    }

}
/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 16.04.22, 12:24 by Carina The Latest changes made by Carina on 16.04.22, 12:24 All contents of "StatsSystem.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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
                TheHunter.instance.statsSystem.addData()
                TheHunter.instance.messages.sendMessageToConsole("stats-system-saved")
            }
        }

        fun loadStatsPlayersFromFile() {
            for (uuid in TheHunter.instance.statsSystem.yml.getKeys(false)) {
                val player = UUID.fromString(uuid)
                if (player != null) {
                    playerStats[player] = StatsPlayer(
                        TheHunter.instance.statsSystem.yml.getInt("$uuid.Kills"),
                        TheHunter.instance.statsSystem.yml.getInt("$uuid.Deaths"),
                        TheHunter.instance.statsSystem.yml.getInt("$uuid.Points"),
                        TheHunter.instance.statsSystem.yml.getDouble("$uuid.KDR"),
                        TheHunter.instance.statsSystem.yml.getInt("$uuid.Wins"),
                        TheHunter.instance.statsSystem.yml.getInt("$uuid.Loses"),
                        TheHunter.instance.statsSystem.yml.getInt("$uuid.Games")
                    )
                }
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
        super.addData()

    }


    fun playerKilledOtherPlayer(killer: Player, dead: Player) {
        removePointsIfPossible(dead)
        playerStats[killer.uniqueId]!!.kills += 1
        playerStats[killer.uniqueId]!!.points += 5
        playerStats[killer.uniqueId]!!.KDR = playerStats[killer.uniqueId]!!.kills.toDouble() / playerStats[killer.uniqueId]!!.deaths.toDouble()
        playerStats[dead.uniqueId]!!.deaths += 1
        playerStats[dead.uniqueId]!!.KDR = playerStats[dead.uniqueId]!!.kills.toDouble() / playerStats[dead.uniqueId]!!.deaths.toDouble()

        super.addData()

    }

    fun playerWon(player: Player) {
        yml.set(player.uniqueId.toString() + ".Wins", yml.getInt(player.uniqueId.toString() + ".Wins") + 1)
        yml.set(player.uniqueId.toString() + ".Points", yml.getInt(player.uniqueId.toString() + ".Points") + 15)
        playerStats[player.uniqueId]!!.wins += 1
        playerStats[player.uniqueId]!!.points += 15
        super.addData()

    }

    fun playerDied(player: Player) {
        removePointsIfPossible(player)
        playerStats[player.uniqueId]!!.deaths += 1
        playerStats[player.uniqueId]!!.KDR = playerStats[player.uniqueId]!!.kills.toDouble() / playerStats[player.uniqueId]!!.deaths.toDouble()
        super.addData()
    }

    private fun removePointsIfPossible(player: Player) {
        if (playerStats[player.uniqueId]!!.points >= 5) {
            playerStats[player.uniqueId]!!.points -= 5
        }
    }

    fun playerPlaysGame(player: Player) {
        playerStats[player.uniqueId]!!.games += 1
        super.addData()
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
/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 16.04.22, 11:27 by Carina The Latest changes made by Carina on 16.04.22, 11:27 All contents of "StatsSystem.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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
        yml.set(killer.uniqueId.toString() + ".Kills", yml.getInt(killer.uniqueId.toString() + ".Kills") + 1)
        yml.set(dead.uniqueId.toString() + ".Deaths", yml.getInt(dead.uniqueId.toString() + ".Deaths") + 1)
        yml.set(killer.uniqueId.toString() + ".KDR", yml.getInt(killer.uniqueId.toString() + ".Kills") / yml.getInt(killer.uniqueId.toString() + ".Deaths"))
        yml.set(dead.uniqueId.toString() + ".KDR", yml.getInt(dead.uniqueId.toString() + ".Kills") / yml.getInt(dead.uniqueId.toString() + ".Deaths"))
        yml.set(killer.uniqueId.toString() + ".Points", yml.getInt(killer.uniqueId.toString() + ".Points") + 5)
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
        yml.set(player.uniqueId.toString() + ".Loses", yml.getInt(player.uniqueId.toString() + ".Loses") + 1)
        yml.set(player.uniqueId.toString() + ".KDR", yml.getInt(player.uniqueId.toString() + ".Kills") / yml.getInt(player.uniqueId.toString() + ".Deaths"))
        removePointsIfPossible(player)
        playerStats[player.uniqueId]!!.deaths += 1
        playerStats[player.uniqueId]!!.KDR = playerStats[player.uniqueId]!!.kills.toDouble() / playerStats[player.uniqueId]!!.deaths.toDouble()
        saveFile()
    }

    private fun removePointsIfPossible(player: Player) {
        if (yml.getInt(player.uniqueId.toString() + ".Points") >= 5) {
            yml.set(player.uniqueId.toString() + ".Points", yml.getInt(player.uniqueId.toString() + ".Points") - 5)
            playerStats[player.uniqueId]!!.points -= 5
        }
    }

    fun playerPlaysGame(player: Player) {
        yml.set(player.uniqueId.toString() + ".Games", yml.getInt(player.uniqueId.toString() + ".Games") + 1)
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
                TheHunter.instance.messages.messagesMap["stats-message-own"]!!.replace("%kills%", playerStats[player.uniqueId]!!.kills.toString()).replace("%deaths%", playerStats[player.uniqueId]!!.deaths.toString()).replace("%wins%", playerStats[player.uniqueId]!!.wins.toString()).replace("%games%", playerStats[player.uniqueId]!!.games.toString()).replace("%points%", playerStats[player.uniqueId]!!.points.toString()).replace(
                    "%kdr%", playerStats[player.uniqueId]!!.KDR.toString
                        ()
                ).replace("%losses%", playerStats[player.uniqueId]!!.loses.toString())
            )
        else
            sender.sendMessage(
                TheHunter.instance.messages.messagesMap["stats-message-other"]!!.replace("%kills%", playerStats[player.uniqueId]!!.kills.toString()).replace("%deaths%", playerStats[player.uniqueId]!!.deaths.toString()).replace("%wins%", playerStats[player.uniqueId]!!.wins.toString()).replace("%games%", playerStats[player.uniqueId]!!.games.toString()).replace("%points%", playerStats[player.uniqueId]!!.points.toString()).replace(
                    "%kdr%", playerStats[player.uniqueId]!!.KDR.toString
                        ()
                ).replace("%losses%", playerStats[player.uniqueId]!!.loses.toString()).replace("%player%", player.name)
            )


        return true
    }

}
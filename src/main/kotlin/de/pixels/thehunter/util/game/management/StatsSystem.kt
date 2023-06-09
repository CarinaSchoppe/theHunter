/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 11:06 PM All contents of "StatsSystem.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.game.management

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.database.DatabaseHandler
import de.pixels.thehunter.util.files.BaseFile
import de.pixels.thehunter.util.misc.ConstantStrings
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*


data class StatsPlayer(
    var kills: Int,
    var deaths: Int,
    var points: Int,
    var kdr: Double,
    var wins: Int,
    var loses: Int,
    var games: Int
)


class StatsSystem : BaseFile("stats.yml") {


    init {
        DatabaseHandler()
    }

    companion object {
        val playerStats = mutableMapOf<UUID, StatsPlayer>()
        fun saveAllStatsPlayerToFiles() {
            for (player in playerStats.keys) {
                if (TheHunter.instance.settings.settingsMap["mysql"] as Boolean) {
                    DatabaseHandler.connection.prepareStatement(
                        "REPLACE INTO statsPlayer(uuid, kills, deaths, points, kdr, wins,loses,games) VALUES ('$player', '${playerStats[player]?.kills}', '${playerStats[player]?.deaths}', '${playerStats[player]?.points}', '${playerStats[player]?.kdr}', '${playerStats[player]?.wins}', '${playerStats[player]?.loses}', '${playerStats[player]?.games}')"
                    )?.executeUpdate()
                } else {
                    TheHunter.instance.statsSystem.yml["$player.Kills", playerStats[player]?.kills]
                    TheHunter.instance.statsSystem.yml[
                        "$player${ConstantStrings.DOT_DEATHS}",
                        playerStats[player]?.deaths
                    ]
                    TheHunter.instance.statsSystem.yml["$player.KDR", playerStats[player]?.kdr]
                    TheHunter.instance.statsSystem.yml["$player.Wins", playerStats[player]?.wins]
                    TheHunter.instance.statsSystem.yml["$player.Loses", playerStats[player]?.loses]
                    TheHunter.instance.statsSystem.yml[
                        "$player${ConstantStrings.DOT_POINTS}",
                        playerStats[player]?.points
                    ]
                    TheHunter.instance.statsSystem.yml["$player.Games", playerStats[player]?.games]
                    TheHunter.instance.statsSystem.addData()
                    TheHunter.instance.messages.sendMessageToConsole("stats-system-saved")
                }
            }
        }

        fun loadStatsPlayersFromFile() {
            if (TheHunter.instance.settings.settingsMap["mysql"] as Boolean) {
                val resultSet = DatabaseHandler.connection.prepareStatement("SELECT * FROM statsPlayer")?.executeQuery()
                while (resultSet?.next() ?: return) {
                    val uuid = UUID.fromString(resultSet.getString("uuid"))
                    val kills = resultSet.getInt("kills")
                    val deaths = resultSet.getInt("deaths")
                    val points = resultSet.getInt("points")
                    val kdr = resultSet.getDouble("kdr")
                    val wins = resultSet.getInt("wins")
                    val loses = resultSet.getInt("loses")
                    val games = resultSet.getInt("games")
                    playerStats[uuid] = StatsPlayer(kills, deaths, points, kdr, wins, loses, games)
                }
            } else {
                for (uuid in TheHunter.instance.statsSystem.yml.getKeys(false)) {
                    val player = UUID.fromString(uuid)
                    if (player != null) {
                        playerStats[player] = StatsPlayer(
                            TheHunter.instance.statsSystem.yml.getInt("$uuid.Kills"),
                            TheHunter.instance.statsSystem.yml.getInt("$uuid${ConstantStrings.DOT_DEATHS}"),
                            TheHunter.instance.statsSystem.yml.getInt("$uuid${ConstantStrings.DOT_POINTS}"),
                            TheHunter.instance.statsSystem.yml.getDouble("$uuid.KDR"),
                            TheHunter.instance.statsSystem.yml.getInt("$uuid.Wins"),
                            TheHunter.instance.statsSystem.yml.getInt("$uuid.Loses"),
                            TheHunter.instance.statsSystem.yml.getInt("$uuid.Games")
                        )
                    }
                }
            }
            Bukkit.getConsoleSender()
                .sendMessage(LegacyComponentSerializer.legacySection().deserialize(TheHunter.prefix + "§aStats loaded"))

        }
    }

    fun generateNewStatsPlayer(player: Player) {
        if (TheHunter.instance.settings.settingsMap["mysql"] as Boolean) {
            //check if player is not allready in database

            if (!DatabaseHandler.connection.prepareStatement("SELECT * FROM statsPLayer WHERE uuid = '${player.uniqueId}' LIMIT 1")
                    .executeQuery().next()
            )
                DatabaseHandler.connection.prepareStatement(
                    "INSERT INTO statsPlayer(uuid, kills, deaths, points, kdr, wins,loses,games) VALUES ('${player.uniqueId}', 0, 0, 0,0.0,0,0,0)"
                )?.execute()
        } else {
            yml[player.uniqueId.toString() + ".Kills", 0]
            yml[player.uniqueId.toString() + ConstantStrings.DOT_DEATHS, 0]
            yml[player.uniqueId.toString() + ".KDR", 0.0]
            yml[player.uniqueId.toString() + ".Wins", 0]
            yml[player.uniqueId.toString() + ".Loses", 0]
            yml[player.uniqueId.toString() + ConstantStrings.DOT_POINTS, 0]
            yml[player.uniqueId.toString() + ".Games", 0]
            super.addData()
        }
        playerStats[player.uniqueId] = StatsPlayer(0, 0, 0, 0.0, 0, 0, 0)
    }


    fun playerKilledOtherPlayer(killer: Player, dead: Player) {
        removePointsIfPossible(dead)
        playerStats[killer.uniqueId]?.kills = playerStats[killer.uniqueId]?.kills?.plus(1) ?: return
        playerStats[killer.uniqueId]?.points = playerStats[killer.uniqueId]?.points?.plus(5) ?: return
        playerStats[killer.uniqueId]?.kdr =
            (playerStats[killer.uniqueId]?.kills?.toDouble() ?: 1.0) / (playerStats[killer.uniqueId]?.deaths?.toDouble() ?: 1.0)
        playerStats[dead.uniqueId]?.deaths = playerStats[dead.uniqueId]?.deaths?.plus(1) ?: return
        playerStats[dead.uniqueId]?.kdr =
            (playerStats[dead.uniqueId]?.kills?.toDouble() ?: 1.0) / (playerStats[dead.uniqueId]?.deaths?.toDouble() ?: 1.0)


        if (TheHunter.instance.settings.settingsMap["mysql"] as Boolean) {
            DatabaseHandler.connection.prepareStatement(
                "UPDATE statsPlayer SET kills = '${playerStats[killer.uniqueId]?.kills}',  points = '${playerStats[killer.uniqueId]?.points}', kdr = '${playerStats[killer.uniqueId]?.kdr}' WHERE uuid = '${killer.uniqueId}'"
            )?.executeUpdate()
            DatabaseHandler.connection.prepareStatement(
                "UPDATE statsPlayer SET deaths = '${playerStats[dead.uniqueId]?.deaths}', points = '${playerStats[dead.uniqueId]?.points}', kdr = '${playerStats[dead.uniqueId]?.kdr}' WHERE uuid = '${dead.uniqueId}'"
            )?.executeUpdate()
        } else {
            yml[killer.uniqueId.toString() + ".Kills", playerStats[killer.uniqueId]?.kills]
            yml[killer.uniqueId.toString() + ConstantStrings.DOT_POINTS, playerStats[killer.uniqueId]?.points]
            yml[dead.uniqueId.toString() + ConstantStrings.DOT_POINTS, playerStats[dead.uniqueId]?.points]
            yml[killer.uniqueId.toString() + ".KDR", playerStats[killer.uniqueId]?.kdr]
            yml[dead.uniqueId.toString() + ConstantStrings.DOT_DEATHS, playerStats[dead.uniqueId]?.deaths]
            yml[dead.uniqueId.toString() + ".KDR", playerStats[dead.uniqueId]?.kdr]
            super.addData()
        }

    }

    fun playerWon(player: Player) {
        playerStats[player.uniqueId]?.wins = playerStats[player.uniqueId]?.wins?.plus(1) ?: return
        playerStats[player.uniqueId]?.points = playerStats[player.uniqueId]?.points?.plus(15) ?: return
        if (TheHunter.instance.settings.settingsMap["mysql"] as Boolean) {
            DatabaseHandler.connection.prepareStatement(
                "UPDATE statsPlayer SET wins='${playerStats[player.uniqueId]?.wins}', points='${playerStats[player.uniqueId]?.points}' WHERE uuid='${player.uniqueId}'"
            )?.executeUpdate()
        } else {
            yml[player.uniqueId.toString() + ".Wins", playerStats[player.uniqueId]?.wins]
            yml[player.uniqueId.toString() + ConstantStrings.DOT_POINTS, playerStats[player.uniqueId]?.points]
            super.addData()
        }
    }

    fun playerDied(player: Player) {
        removePointsIfPossible(player)
        playerStats[player.uniqueId]?.deaths = playerStats[player.uniqueId]?.deaths?.plus(1) ?: return
        playerStats[player.uniqueId]?.kdr =
            (playerStats[player.uniqueId]?.kills?.toDouble() ?: 1.0) / (playerStats[player.uniqueId]?.deaths?.toDouble() ?: 1.0)
        if (TheHunter.instance.settings.settingsMap["mysql"] as Boolean) {
            DatabaseHandler.connection.prepareStatement(
                "UPDATE statsPlayer SET deaths = '${playerStats[player.uniqueId]?.deaths}',points='${playerStats[player.uniqueId]?.points}', kdr = '${playerStats[player.uniqueId]?.kdr}' WHERE uuid = '${player.uniqueId}'"
            )?.executeUpdate()
        } else {
            yml[player.uniqueId.toString() + ConstantStrings.DOT_DEATHS, playerStats[player.uniqueId]?.deaths]
            yml[player.uniqueId.toString() + ".KDR", playerStats[player.uniqueId]?.kdr]
            yml[player.uniqueId.toString() + ConstantStrings.DOT_POINTS, playerStats[player.uniqueId]?.points]
            super.addData()
        }

    }

    private fun removePointsIfPossible(player: Player) {
        if (5 <= playerStats[player.uniqueId]?.points ?: return) {
            playerStats[player.uniqueId]?.points = playerStats[player.uniqueId]?.points?.minus(5) ?: return
        }
    }

    fun playerPlaysGame(player: Player) {
        playerStats[player.uniqueId]?.games = playerStats[player.uniqueId]?.games?.plus(1) ?: return
        if (TheHunter.instance.settings.settingsMap["mysql"] as Boolean) {
            DatabaseHandler.connection.prepareStatement("UPDATE statsPlayer SET games='${playerStats[player.uniqueId]?.games}' WHERE uuid='${player.uniqueId}'")
                ?.executeUpdate()
        } else {
            yml[player.uniqueId.toString() + ".Games", playerStats[player.uniqueId]?.games]
            super.addData()
        }
    }

    fun generateStatsMessageForPlayer(sender: Player, player: Player): Boolean {
        if (playerStats[player.uniqueId] == null) {
            TheHunter.instance.messages.messagesMap["stats-not-found"]?.replace(
                ConstantStrings.PLAYER_SPAWN,
                player.name
            )?.let {
                sender.sendMessage(
                    it.replace(ConstantStrings.PLAYER_PERCENT, player.name)
                )
            }
            return false
        }

        if (sender.uniqueId == player.uniqueId)

            TheHunter.instance.messages.messagesMap["stats-message-own"].let {
                it?.replace(
                    "%kills%",
                    playerStats[player.uniqueId]?.kills.toString()
                )?.replace("%deaths%", playerStats[player.uniqueId]?.deaths.toString())
                    ?.replace("%wins%", playerStats[player.uniqueId]?.wins.toString())
                    ?.replace("%games%", playerStats[player.uniqueId]?.games.toString())
                    ?.replace("%points%", playerStats[player.uniqueId]?.points.toString())
                    ?.replace("%kd%", playerStats[player.uniqueId]?.kdr.toString())
                    ?.replace("%losses%", playerStats[player.uniqueId]?.loses.toString())?.let { message ->
                        sender.sendMessage(
                            message
                        )
                    }
            }
        else
            TheHunter.instance.messages.messagesMap["stats-message-other"]?.let {
                sender.sendMessage(
                    it
                        .replace("%kills%", playerStats[player.uniqueId]?.kills.toString())
                        .replace("%deaths%", playerStats[player.uniqueId]?.deaths.toString())
                        .replace("%wins%", playerStats[player.uniqueId]?.wins.toString())
                        .replace("%games%", playerStats[player.uniqueId]?.games.toString())
                        .replace("%points%", playerStats[player.uniqueId]?.points.toString())
                        .replace("%kd%", playerStats[player.uniqueId]?.kdr.toString())
                        .replace("%losses%", playerStats[player.uniqueId]?.loses.toString())
                        .replace(ConstantStrings.PLAYER_PERCENT, player.name)
                )
            }

        return true
    }

}

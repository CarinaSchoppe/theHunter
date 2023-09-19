/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:42 PM All contents of "Scoreboard.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.game.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.game.management.Game
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Score

class Scoreboard(val game: Game) {
    /**
     * Represents the translated server name.
     *
     * This variable holds the translated server name retrieved from
     * the settings map of TheHunter.instance. The server name is
     * obtained as a string from the settings map and then translated
     * using ChatColor.translateAlternateColorCodes('&') function.
     * The translated server name is stored in this variable for further use.
     *
     * @property serverName The translated server name.
     */
    private val serverName =
        ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settingsFile.settingsMap["server-name"] as String)

    /**
     * Private variable representing the tsIP.
     * The tsIP is a translated string obtained from the "ts-ip" value in the settings map.
     */
    private val tsIP =
        ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settingsFile.settingsMap["ts-ip"] as String)

    /**
     * Creates a new scoreboard for the specified player.
     *
     * @param player The player for whom the scoreboard is created.
     */
    fun createNewScoreboard(player: Player) {
        player.scoreboard.clearSlot(DisplaySlot.SIDEBAR)
        val scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective: Objective = scoreboard.registerNewObjective("aaa", Criteria.DUMMY, Component.text(serverName))
        objective.displaySlot = DisplaySlot.SIDEBAR

        val six = objective.getScore("§fKills: §d${game.currentGameKills.getOrDefault(player, 0)}")
        val five = objective.getScore("§aAlive Players:")
        val four = objective.getScore("§f" + game.players.size)
        val three = objective.getScore("§eMap Name:")
        val two = objective.getScore("§f" + game.name)
        val one = objective.getScore("§cTeamspeak: ")
        val zero: Score = objective.getScore(tsIP)
        four.score = 4
        six.score = 6
        three.score = 3
        two.score = 2
        one.score = 1
        zero.score = 0
        five.score = 5
        player.scoreboard = scoreboard
    }

}

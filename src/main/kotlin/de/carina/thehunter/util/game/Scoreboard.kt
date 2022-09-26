/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 8/22/22, 2:30 PM by Carina The Latest changes made by Carina on 8/22/22, 2:30 PM All contents of "Scoreboard.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.util.game

import de.carina.thehunter.TheHunter
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Score

class Scoreboard(val game: Game) {
    private val serverName = ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["server-name"] as String)
    private val tsIP = ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["ts-ip"] as String)

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

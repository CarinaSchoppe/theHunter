/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "Scoreboard.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.game

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.misc.StatsSystem
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Score

class Scoreboard(val game: Game) {
    //TDOO: Dies testen
    private val serverName = ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["server-name"] as String)
    private val scoreboard = Bukkit.getScoreboardManager().newScoreboard
    private val objective: Objective = scoreboard.registerNewObjective("aaa", "bbb", Component.text(serverName))
    private val tsIP = ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["ts-ip"] as String)

    fun createNewScoreboard(player: Player) {
        scoreboard.clearSlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR)
        objective.displaySlot = DisplaySlot.SIDEBAR
        val six = objective.getScore("§fKills: §d${StatsSystem.playerStats[player.uniqueId]!!.kills}")
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
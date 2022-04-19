/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 10:46 by Carina The Latest changes made by Carina on 19.04.22, 10:46 All contents of "EndCountdown.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.countdowns

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.Game
import org.bukkit.Bukkit

class EndCountdown(game: Game) : Countdown(game) {

    override var duration: Int = 60

    private var taskID = 0

    override fun start() {
        duration = TheHunter.instance.settings.settingsMap["duration-endcountdown"] as Int
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(TheHunter.instance, {
            duration--
            if (game.players.isEmpty() && game.spectators.isEmpty()) {
                stop()
                Bukkit.getScheduler().cancelTask(taskID)
                return@scheduleSyncRepeatingTask
            }
            when (duration) {
                in 0..10 -> {
                    for (player in game.players) {
                        player.sendMessage(TheHunter.instance.messages.messagesMap["endcountdown-message"]!!)
                    }
                    for (player in game.spectators) {
                        player.sendMessage(TheHunter.instance.messages.messagesMap["endcountdown-message"]!!)
                    }
                }
                0 -> {
                    stop()
                }
            }
        }, 20L, 20L)
    }

    override fun idle() {
        //Will not be implemented
        return
    }

    override fun stop() {
        game.nextGameState()
    }

    override var isIdle: Boolean = false
    override val id: Int = Countdowns.END_COUNTDOWN.id

}
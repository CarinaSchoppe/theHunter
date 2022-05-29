/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 19:45 by Carina The Latest changes made by Carina on 19.04.22, 19:45 All contents of "EndCountdown.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.countdowns

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.Game
import org.bukkit.Bukkit
import org.bukkit.Sound
import java.util.function.Consumer

class EndCountdown(game: Game) : Countdown(game) {

    override var duration: Int = 10


    override fun start() {
        duration = TheHunter.instance.settings.settingsMap["duration-endcountdown"] as Int
        Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Consumer {
            if (game.players.isEmpty() && game.spectators.isEmpty()) {
                stop()
                it.cancel()
            }
            when (duration) {
                0 -> {
                    stop()
                }

                in 1 until 10 -> {
                    for (player in game.players) {
                        player.sendMessage(TheHunter.instance.messages.messagesMap["endcountdown-message"]!!.replace("%time%", duration.toString()))
                        player.level = duration
                        player.playSound(player.location, Sound.BLOCK_LAVA_POP, 1F, 1F)
                    }
                    for (spectator in game.spectators) {
                        spectator.sendMessage(TheHunter.instance.messages.messagesMap["endcountdown-message"]!!.replace("%time%", duration.toString()))
                        spectator.level = duration
                        spectator.playSound(spectator.location, Sound.BLOCK_LAVA_POP, 1F, 1F)
                    }
                }

            }
            duration -= 1


        }, 20L, 20L)
    }

    override fun idle() {
        return
    }

    override fun stop() {
        game.nextGameState()
    }

    override var isIdle: Boolean = false
    override val id: Int = Countdowns.END_COUNTDOWN.id

}
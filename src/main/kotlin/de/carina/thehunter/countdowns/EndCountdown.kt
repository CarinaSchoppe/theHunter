/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 10:53 PM All contents of "EndCountdown.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.countdowns

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.misc.ConstantStrings
import org.bukkit.Bukkit
import org.bukkit.Sound
import java.util.function.Consumer

class EndCountdown(game: Game) : Countdown(game) {

    override var duration: Int = 10


    override fun start() {
        duration = TheHunter.instance.settings.settingsMap[ConstantStrings.ENDCOUNTDOWN_DURATION] as Int
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
                        player.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.ENDCOUNTDOWN_MESSAGE]!!.replace("%time%", duration.toString()))
                        player.level = duration
                        player.playSound(player.location, Sound.BLOCK_LAVA_POP, 1F, 1F)
                    }
                    for (spectator in game.spectators) {
                        spectator.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.ENDCOUNTDOWN_MESSAGE]!!.replace("%time%", duration.toString()))
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

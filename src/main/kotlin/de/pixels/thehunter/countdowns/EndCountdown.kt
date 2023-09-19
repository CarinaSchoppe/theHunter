/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 10:53 PM All contents of "EndCountdown.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.countdowns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Bukkit
import org.bukkit.Sound
import java.util.function.Consumer

class EndCountdown(game: Game) : Countdown(game) {

    /**
     * Represents the duration of a certain event or process, measured in seconds.
     *
     * The duration is specified by an integer value in seconds.
     * This variable can be overridden and used in classes or objects that need to track or set the duration of something.
     *
     * @property duration The duration value in seconds.
     */
    override var duration: Int = 10


    /**
     * Starts the end countdown.
     * The duration of the countdown is set in the settings under the key "ENDCOUNTDOWN_DURATION".
     * The countdown updates every second and sends messages and plays sounds to all players and spectators.
     * The countdown stops if there are no players or spectators left, or if the duration reaches 0.
     * Once the countdown stops, it cannot be restarted.
     *
     * @see TheHunter
     * @see TheHunter.instance.settings.settingsMap
     * @see ConstantStrings.ENDCOUNTDOWN_DURATION
     * @see Bukkit.getScheduler().runTaskTimer
     * @see Consumer
     * @see game
     * @see game.players
     * @see game.spectators
     * @see TheHunter.instance.messages.messagesMap
     * @see ConstantStrings.ENDCOUNTDOWN_MESSAGE
     * @see duration
     */
    override fun start() {
        duration = TheHunter.instance.settingsFile.settingsMap[ConstantStrings.ENDCOUNTDOWN_DURATION] as Int
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
                    this.game.players.forEach { player ->
                        TheHunter.instance.messagesFile.messagesMap[ConstantStrings.ENDCOUNTDOWN_MESSAGE]?.let { message ->
                            player.sendMessage(
                                message.replace(
                                    "%time%",
                                    duration.toString()
                                )
                            )
                        }
                        player.level = duration
                        player.playSound(player.location, Sound.BLOCK_LAVA_POP, 1F, 1F)
                    }
                    game.spectators.forEach { spectator ->
                        TheHunter.instance.messagesFile.messagesMap[ConstantStrings.ENDCOUNTDOWN_MESSAGE]?.let { message ->
                            spectator.sendMessage(
                                message.replace(
                                    "%time%",
                                    duration.toString()
                                )
                            )
                        }
                        spectator.level = duration
                        spectator.playSound(spectator.location, Sound.BLOCK_LAVA_POP, 1F, 1F)
                    }
                }

            }
            duration -= 1


        }, 20L, 20L)
    }

    /**
     * Performs an idle operation.
     *
     * This method does not perform any specific action and is intended to be overridden in sub-classes
     * to provide a custom implementation.
     */
    override fun idle() {
        return
    }

    /**
     * Stops the game by transitioning to the next game state.
     */
    override fun stop() {
        game.nextGameState()
    }

    /**
     * Indicates whether the object is idle or not.
     *
     * @property isIdle The state of the object. True if idle, false otherwise.
     */
    override var isIdle: Boolean = false

    /**
     * Represents the unique identifier of an end countdown.
     */
    override val id: Int = Countdowns.END_COUNTDOWN.id

}

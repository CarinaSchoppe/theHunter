/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 10:57 PM All contents of "LobbyCountdown.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.countdowns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.misc.ConstantStrings
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.function.Consumer


class LobbyCountdown(game: Game) : Countdown(game) {

    /**
     * The duration of an event or process, in seconds.
     *
     * @property duration The duration of the event or process, measured in seconds.
     */
    override var duration: Int = 60


    /**
     * Sets the game to idle state.
     * During idle state, the game is waiting for players to join and reach the minimum required number of players to start.
     * If the game has no players and spectators, it cancels the idle state.
     * If the game has enough players, it starts the game.
     * If the idle duration reaches zero, it sends a waiting message to the players and resets the duration.
     */
    override fun idle() {
        isIdle = true
        isRunning = false
        duration = 10
        Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Consumer {
            if (game.players.isEmpty() && game.spectators.isEmpty()) {
                isIdle = false
                it.cancel()
                return@Consumer
            }
            if (game.players.size >= game.minPlayers) {
                isIdle = false
                start()
                return@Consumer
            }
            if (duration == 0) {
                duration = TheHunter.instance.settingsFile.settingsMap["duration-idle"] as Int
                game.players.forEach(Consumer { player ->
                    TheHunter.instance.messagesFile.messagesMap["game-waiting-for-players"]?.replace(
                        "%current%",
                        game.players.size.toString()
                    )?.let { message ->
                        player.sendMessage(
                            message.replace("%max%", game.maxPlayers.toString())
                        )
                    }
                })
            }
            duration -= 1
        }, 20L, 20L)
    }

    /**
     * Starts the lobby countdown for the game.
     */
    override fun start() {
        isRunning = true
        isIdle = false
        duration = TheHunter.instance.settingsFile.settingsMap["duration-lobby"] as Int
        if (game.players.size < game.minPlayers) {
            idle()
            return
        }

        Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Consumer {
            if (game.players.size < game.minPlayers) {
                idle()
                it.cancel()
                return@Consumer
            }
            if (duration <= 0) {
                game.players.forEach(Consumer { player ->
                    TheHunter.instance.messagesFile.messagesMap[ConstantStrings.GAME_STARTING]?.let { message ->
                        player.sendMessage(
                            message.replace(
                                ConstantStrings.TIME_PERCENT,
                                duration.toString()
                            )
                        )
                    }
                })
                game.spectators.forEach(Consumer { player ->
                    TheHunter.instance.messagesFile.messagesMap[ConstantStrings.GAME_STARTING]?.let { message ->
                        player.sendMessage(
                            message.replace(
                                ConstantStrings.TIME_PERCENT,
                                duration.toString()
                            )
                        )
                    }
                })
                game.nextGameState()
                it.cancel()
                return@Consumer
            }
            lobbyCountdown()
            duration--

        }, 20, 20)
    }

    /**
     * Sends a message to all players and spectators in the game,
     * using the playerHandler method.
     */
    private fun sendMessageTime() {
        playerHandler(game.players)
        playerHandler(game.spectators)
    }

    /**
     * Handles the players in the game.
     *
     * @param user The set of players to handle.
     */
    private fun playerHandler(user: MutableSet<Player>) {
        user.forEach(Consumer { player ->
            TheHunter.instance.messagesFile.messagesMap[ConstantStrings.GAME_STARTING_IN]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.TIME_PERCENT,
                        duration.toString()
                    )
                )
            }
            player.level = duration
            player.playSound(player.location, Sound.BLOCK_LAVA_POP, 1F, 1F)
        })
    }


    /**
     * Handles the countdown logic for the lobby before the game starts.
     */
    private fun lobbyCountdown() {
        when (duration) {
            in 1..TheHunter.instance.settingsFile.settingsMap[ConstantStrings.DURATION_SPEEDUP] as Int -> {
                game.players.forEach(Consumer { player ->
                    player.showTitle(
                        Title.title(
                            LegacyComponentSerializer.legacySection().deserialize("§6$duration"),
                            LegacyComponentSerializer.legacySection().deserialize(
                                TheHunter.instance.messagesFile.messagesMap[ConstantStrings.GAME_STARTING_IN]?.replace(
                                    ConstantStrings.TIME_PERCENT,
                                    duration.toString()
                                ) ?: ""
                            )
                        )
                    )
                })
                game.spectators.forEach(Consumer { spectator ->
                    spectator.showTitle(
                        Title.title(
                            LegacyComponentSerializer.legacySection().deserialize("§6$duration"),
                            LegacyComponentSerializer.legacySection().deserialize(
                                TheHunter.instance.messagesFile.messagesMap[ConstantStrings.GAME_STARTING_IN]?.replace(
                                    ConstantStrings.TIME_PERCENT,
                                    duration.toString()
                                ) ?: ""
                            )
                        )
                    )
                })
                sendMessageTime()
            }

            60 -> {
                sendMessageTime()
            }

            30 -> {
                sendMessageTime()
            }

            20 -> {
                sendMessageTime()
            }

            in 5..10 -> {
                sendMessageTime()
            }

            else -> {
                game.players.forEach {
                    it.level = duration
                }
                game.spectators.forEach {
                    it.level = duration
                }
            }
        }
    }

    /**
     * Stops the execution of the program.
     */
    override fun stop() {
        return
    }

    /**
     * Indicates whether the object is currently idle or not.
     *
     * The `isIdle` property is a boolean variable that represents the idle state of an object.
     * When `isIdle` is set to `true`, it means the object is in an idle state or not currently active.
     * When `isIdle` is set to `false`, it means the object is not idle and currently active or performing some task.
     *
     * By default, the `isIdle` property is set to `false`.
     *
     * @see [Object]
     */
    override var isIdle: Boolean = false

    /**
     * Variable indicating whether the program is currently running.
     *
     * @type {boolean}
     */
    var isRunning = false

    /**
     * Represents the identifier for a countdown.
     *
     * @property id The identifier value.
     */
    override val id: Int = Countdowns.LOBBY_COUNTDOWN.id

}

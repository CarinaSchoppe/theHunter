/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 10:27 by Carina The Latest changes made by Carina on 19.04.22, 10:27 All contents of "LobbyCountdown.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.countdowns

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.Game
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import java.util.function.Consumer

class LobbyCountdown(game: Game) : Countdown(game) {

    override var duration: Int = 60


    private lateinit var taskIDRun: BukkitTask
    private lateinit var idleID: BukkitTask

    override fun idle() {
        isIdle = true
        isRunning = false
        duration = 10
        idleID = Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Runnable {

            if (game.players.isEmpty() && game.spectators.isEmpty()) {
                idleID.cancel()
                taskIDRun.cancel()
                isIdle = false
                isRunning = false
                return@Runnable
            }
            if (game.players.size >= game.minPlayers) {
                isIdle = false
                start()
                return@Runnable
            }
            if (duration == 0) {
                duration = TheHunter.instance.settings.settingsMap["duration-idle"] as Int
                game.players.forEach(Consumer { player ->
                    player.sendMessage(TheHunter.instance.messages.messagesMap["game-waiting-for-players"]!!.replace("%current%", game.players.size.toString()).replace("%max%", game.maxPlayers.toString()))
                })
            }
            duration--
        }, 20, 20)
    }

    override fun start() {
        isRunning = true
        isIdle = false
        duration = TheHunter.instance.settings.settingsMap["duration-lobby"] as Int
        if (game.players.size < game.minPlayers) {
            idle()
            return
        }
        idleID.cancel()
        taskIDRun = Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Runnable {


            if (game.players.size < game.minPlayers) {
                idle()
                return@Runnable
            }
            if (duration <= 0) {
                game.players.forEach(Consumer { player ->
                    player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting"]!!.replace("%time%", duration.toString()))
                })
                game.nextGameState()
                return@Runnable
            }

            when (duration) {
                in 1..TheHunter.instance.settings.settingsMap["duration-speedup"] as Int -> {
                    game.players.forEach(Consumer { player ->
                        player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting-in"]!!.replace("%time%", duration.toString()))
                    })
                }
                in 60..60 -> {
                    game.players.forEach(Consumer { player ->
                        player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting-in"]!!.replace("%time%", duration.toString()))
                    })
                }
                in 30..30 -> {
                    game.players.forEach(Consumer { player ->
                        player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting-in"]!!.replace("%time%", duration.toString()))
                    })

                }
                in 20..20 -> {
                    game.players.forEach(Consumer { player ->
                        player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting-in"]!!.replace("%time%", duration.toString()))
                    })

                }
                in 10..10 -> {
                    game.players.forEach(Consumer { player ->
                        player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting-in"]!!.replace("%time%", duration.toString()))
                    })
                }
            }

            duration--
        }, 20, 20)
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override var isIdle: Boolean = false
    var isRunning = false

    override val id: Int = Countdowns.LOBBY_COUNTDOWN.id

}
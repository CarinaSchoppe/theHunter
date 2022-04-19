/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 17:24 by Carina The Latest changes made by Carina on 19.04.22, 17:24 All contents of "LobbyCountdown.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.countdowns

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.Game
import org.bukkit.Bukkit
import java.util.function.Consumer

class LobbyCountdown(game: Game) : Countdown(game) {

    override var duration: Int = 60


    private var taskIDRun: Int? = null
    private var idleID: Int? = null

    override fun idle() {
        isIdle = true
        isRunning = false
        duration = 10
        idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(TheHunter.instance, Runnable {
            if (game.players.isEmpty() && game.spectators.isEmpty()) {
                isIdle = false
                if (taskIDRun != null) {
                    Bukkit.getScheduler().cancelTask(taskIDRun!!)
                    println("deactivete2")
                }
                if (idleID != null) {
                    Bukkit.getScheduler().cancelTask(idleID!!)
                    println("deactivete1")
                }
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
            duration -= 1
        }, 20L, 20L)
    }

    override fun start() {
        isRunning = true
        isIdle = false
        duration = TheHunter.instance.settings.settingsMap["duration-lobby"] as Int
        if (game.players.size < game.minPlayers) {
            idle()
            return
        }
        if (idleID != null) {
            Bukkit.getScheduler().cancelTask(idleID!!)
            println("deactivete3")
        }
        taskIDRun = Bukkit.getScheduler().scheduleSyncRepeatingTask(TheHunter.instance, Runnable {
            if (game.players.size < game.minPlayers) {
                idle()
                return@Runnable
            }
            if (duration <= 0) {
                println("size: ${game.players.size}")
                game.players.forEach(Consumer { player ->
                    player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting"]!!.replace("%time%", duration.toString()))
                })
                game.spectators.forEach(Consumer { player ->
                    player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting"]!!.replace("%time%", duration.toString()))
                })
                game.nextGameState()
                return@Runnable
            }

            when (duration) {
                in 1..TheHunter.instance.settings.settingsMap["duration-speedup"] as Int -> {
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
                    sendMessageTime()
                    game.players.forEach {
                        it.level = duration
                    }
                    game.spectators.forEach {
                        it.level = duration
                    }
                }
            }
            duration--

        }, 20, 20)
    }

    private fun sendMessageTime() {
        game.players.forEach(Consumer { player ->
            player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting-in"]!!.replace("%time%", duration.toString()))
            player.level = duration
        })
        game.spectators.forEach(Consumer { spectator ->
            spectator.sendMessage(TheHunter.instance.messages.messagesMap["game-starting-in"]!!.replace("%time%", duration.toString()))
            spectator.level = duration
        })
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override var isIdle: Boolean = false
    var isRunning = false

    override val id: Int = Countdowns.LOBBY_COUNTDOWN.id

}
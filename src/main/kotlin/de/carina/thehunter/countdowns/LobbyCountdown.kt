/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 13.04.22, 22:09 by Carina The Latest changes made by Carina on 13.04.22, 22:09 All contents of "LobbyCountdown.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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


    override fun idle() {
        isIdle = true
        duration = 10
        Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Runnable {
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
        duration = TheHunter.instance.settings.settingsMap["duration-lobby"] as Int
        if (game.players.size < game.minPlayers) {
            idle()
            return
        }
        Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Runnable {
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

    override val id: Int = Countdowns.LOBBY_COUNTDOWN.id

}
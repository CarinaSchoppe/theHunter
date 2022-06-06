/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "LobbyCountdown.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.countdowns

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.Game
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.function.Consumer


class LobbyCountdown(game: Game) : Countdown(game) {

    override var duration: Int = 60


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

        Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Consumer {
            if (game.players.size < game.minPlayers) {
                idle()
                it.cancel()
                return@Consumer
            }
            if (duration <= 0) {
                game.players.forEach(Consumer { player ->
                    player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting"]!!.replace("%time%", duration.toString()))
                })
                game.spectators.forEach(Consumer { player ->
                    player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting"]!!.replace("%time%", duration.toString()))
                })
                game.nextGameState()
                it.cancel()
                return@Consumer
            }
            lobbyCountdown()
            duration--

        }, 20, 20)
    }

    private fun sendMessageTime() {
        playerHandler(game.players)
        playerHandler(game.spectators)
    }

    private fun playerHandler(user: MutableSet<Player>) {
        user.forEach(Consumer { player ->
            player.sendMessage(TheHunter.instance.messages.messagesMap["game-starting-in"]!!.replace("%time%", duration.toString()))
            player.level = duration
            player.playSound(player.location, Sound.BLOCK_LAVA_POP, 1F, 1F)
        })
    }


    private fun lobbyCountdown() {
        when (duration) {
            in 1..TheHunter.instance.settings.settingsMap["duration-speedup"] as Int -> {
                game.players.forEach(Consumer { player ->
                    player.showTitle(Title.title(LegacyComponentSerializer.legacySection().deserialize("§6$duration"), LegacyComponentSerializer.legacySection().deserialize(TheHunter.instance.messages.messagesMap["game-starting-in"]!!.replace("%time%", duration.toString()))))
                })
                game.spectators.forEach(Consumer { spectator ->
                    spectator.showTitle(Title.title(LegacyComponentSerializer.legacySection().deserialize("§6$duration"), LegacyComponentSerializer.legacySection().deserialize(TheHunter.instance.messages.messagesMap["game-starting-in"]!!.replace("%time%", duration.toString()))))
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

    override fun stop() {
        TODO("Not yet implemented")
    }

    override var isIdle: Boolean = false
    var isRunning = false

    override val id: Int = Countdowns.LOBBY_COUNTDOWN.id

}
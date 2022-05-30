/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.04.22, 15:59 by Carina The Latest changes made by Carina on 21.04.22, 15:59 All contents of "IngameState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.gamestates

import de.carina.thehunter.TheHunter
import de.carina.thehunter.guns.Ak
import de.carina.thehunter.guns.Minigun
import de.carina.thehunter.guns.Pistol
import de.carina.thehunter.guns.Sniper
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.misc.PlayerDropping
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.function.Consumer

class IngameState(game: Game) : GameState(game) {
    override fun start() {
        if (!game.randomPlayerDrop) PlayerDropping.dropPlayers(game) //TODO: Hier
        forEachPlayer()
        allPlayerStuffHiding()
        if (game.checkWinning()) {
            game.nextGameState()
            return
        } else {
            givePlayerStartItems()
            startImmunityCounter()
            game.worldBoarderController.shrinkWorld()
            game.gameChest.makeChestsFall()
        }
    }

    private fun forEachPlayer() {
        for ((index, player) in game.players.withIndex()) {
            if (!game.randomPlayerDrop) //TODO: Hier
                player.teleport(game.playerSpawns[index])
            player.isInvulnerable = true
            TheHunter.instance.statsSystem.playerPlaysGame(player)
            game.scoreBoard.createNewScoreboard(player)
            player.showTitle(Title.title(LegacyComponentSerializer.legacySection().deserialize(TheHunter.prefix), LegacyComponentSerializer.legacySection().deserialize("§6Lets Play!")))
            playerHiding(player)
        }
    }

    private fun playerHiding(player: Player) {
        Bukkit.getOnlinePlayers().forEach {
            player.hidePlayer(TheHunter.instance, it)
            it.hidePlayer(TheHunter.instance, player)
        }
        game.players.forEach {
            it.showPlayer(TheHunter.instance, player)
        }
        game.spectators.forEach(Consumer { spectator ->
            spectator.showPlayer(TheHunter.instance, player)
            spectator.allowFlight = true
        })
    }

    private fun allPlayerStuffHiding() {
        Bukkit.getOnlinePlayers().forEach {
            game.spectators.forEach(Consumer { spectator ->
                if (!game.players.contains(it))
                    spectator.hidePlayer(TheHunter.instance, it)
                it.hidePlayer(TheHunter.instance, spectator)
            })
        }
    }

    private fun startImmunityCounter() {
        Bukkit.getScheduler().runTaskTimer(TheHunter.instance, { task ->
            if (game.currentGameState !is IngameState)
                task.cancel()

            when (game.immunity) {
                0 -> {
                    game.players.forEach {
                        it.isInvulnerable = false
                        it.sendMessage(TheHunter.instance.messages.messagesMap["immunity-off"]!!)
                        task.cancel()
                    }
                }

                else -> {
                    game.players.forEach {
                        it.isInvulnerable = true
                        it.sendMessage(TheHunter.instance.messages.messagesMap["immunity-message"]!!.replace("%time%", game.immunity.toString()))
                    }
                }
            }
            game.immunity -= 1
        }, 20L, 20L)
    }


    override fun stop() {
        for (player in game.players) {
            player.activePotionEffects.clear()
            player.inventory.clear()
            player.teleport(game.endLocation!!)
            player.inventory.setItem(8, LeaveItem.leaveItem)
            player.level = 0
            player.allowFlight = false
        }
        for (spectator in game.spectators) {
            spectator.allowFlight = false
            spectator.activePotionEffects.clear()
            spectator.inventory.clear()
            spectator.teleport(game.endLocation!!)
            spectator.level = 0
        }
        game.worldBoarderController.resetWorldBoarder()
    }

    private fun givePlayerStartItems() {
        game.players.forEach {
            it.inventory.clear()
            it.inventory.addItem(Ak.createAkGunItem())
            it.inventory.addItem(Minigun.createMiniGunItem())
            it.inventory.addItem(Pistol.createPistolGunItem())
            it.inventory.addItem(Sniper.createSniperGunItem())
        }
    }

    override val gameStateID: Int = GameStates.INGAME_STATE.id
}
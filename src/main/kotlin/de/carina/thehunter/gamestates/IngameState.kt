/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 17:24 by Carina The Latest changes made by Carina on 19.04.22, 17:24 All contents of "IngameState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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
import de.carina.thehunter.items.configurator.LeaveItem
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.misc.PlayerDropping
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import java.util.function.Consumer

class IngameState(game: Game) : GameState(game) {
    override fun start() {
        if (game.randomPlayerDrop) PlayerDropping.dropPlayers(game)
        else {
            for ((index, player) in game.players.withIndex()) {
                player.isInvulnerable = true
                TheHunter.instance.statsSystem.playerPlaysGame(player)
                game.scoreBoard.createNewScoreboard(player)
                player.showTitle(Title.title(LegacyComponentSerializer.legacySection().deserialize(TheHunter.PREFIX), LegacyComponentSerializer.legacySection().deserialize("§Lets Play!")))
                player.teleport(game.playerSpawns[index])
                //Create a for each loop with game.spectators with the Consumer spectator
                Bukkit.getOnlinePlayers().forEach {
                    player.hidePlayer(TheHunter.instance, it)
                    it.hidePlayer(TheHunter.instance, player)
                }
                game.players.forEach {
                    it.showPlayer(TheHunter.instance, player)
                }
                game.spectators.forEach(Consumer { spectator ->
                    spectator.showPlayer(TheHunter.instance, player)
                })
            }
            Bukkit.getOnlinePlayers().forEach {
                game.spectators.forEach(Consumer { spectator ->
                    spectator.hidePlayer(TheHunter.instance, it)
                    it.hidePlayer(TheHunter.instance, spectator)
                })
            }
        }

        givePlayerStartItems()
        startImmunityCounter()
        game.gameChest.makeChestsFall()
        game.worldBoarderController.shrinkWorld()
        if (game.checkWinning())
            game.nextGameState()
    }


    private fun startImmunityCounter() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(TheHunter.instance, {
            game.immunity -= 1
            when (game.immunity) {
                0 -> {
                    game.players.forEach {
                        it.isInvulnerable = false
                        it.sendMessage(TheHunter.instance.messages.messagesMap["immunity-off"]!!)
                    }
                }
                else -> {
                    game.players.forEach {
                        it.isInvulnerable = true
                        it.sendMessage(TheHunter.instance.messages.messagesMap["immunity-message"]!!.replace("%time%", game.immunity.toString()))
                    }
                }
            }
        }, 20L, 20L)
    }


    override fun stop() {
        for (player in game.players) {
            player.activePotionEffects.clear()
            player.inventory.clear()
            player.teleport(game.endLocation!!)
            player.inventory.setItem(8, LeaveItem.createLeaveItem())
            player.level = 0
        }
        for (spectator in game.spectators) {
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
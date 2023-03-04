/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:37 PM All contents of "IngameState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.gamestates

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.guns.Minigun
import de.pixels.thehunter.guns.Pistol
import de.pixels.thehunter.guns.Rifle
import de.pixels.thehunter.guns.Sniper
import de.pixels.thehunter.items.special.Knife
import de.pixels.thehunter.util.builder.Items
import de.pixels.thehunter.util.game.ingame.PlayerDropping
import de.pixels.thehunter.util.game.ingame.PlayerHiding
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.misc.ConstantStrings
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player

class IngameState(game: Game) : GameState(game) {
    override fun start() {
        if (game.randomPlayerDrop) PlayerDropping.dropPlayers(game)
        forEachPlayer()
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
            player.level = 0
            if (!game.randomPlayerDrop)
                player.teleport(game.playerSpawns[index])

            player.isInvulnerable = true
            TheHunter.instance.statsSystem.playerPlaysGame(player)
            game.scoreBoard.createNewScoreboard(player)
            player.showTitle(
                Title.title(
                    LegacyComponentSerializer.legacySection().deserialize(TheHunter.prefix),
                    LegacyComponentSerializer.legacySection().deserialize("§6Lets Play!")
                )
            )

            PlayerHiding.showPlayerToOnlyGamePlayingPlayers(player)
            PlayerHiding.showOnlyActiveGamePlayingPlayersToPlayer(player)
        }

        for (spectator in game.spectators) {
            game.spectatorLocation?.let { spectator.teleport(it) }
            spectator.showTitle(
                Title.title(
                    LegacyComponentSerializer.legacySection().deserialize(TheHunter.prefix),
                    LegacyComponentSerializer.legacySection().deserialize("§6Lets Watch!")
                )
            )
            PlayerHiding.hidePlayerToAll(spectator)
            PlayerHiding.showOnlyActiveGamePlayingPlayersToPlayer(spectator)
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
                        it.playSound(it.location, Sound.ENTITY_ENDER_DRAGON_GROWL, 1F, 1F)

                        it.sendMessage(TheHunter.instance.messages.messagesMap["immunity-off"]!!)
                        task.cancel()
                    }
                }

                else -> {
                    game.players.forEach {
                        it.isInvulnerable = true
                        it.playSound(it.location, Sound.BLOCK_LAVA_POP, 1F, 1F)
                        it.sendMessage(
                            TheHunter.instance.messages.messagesMap["immunity-message"]!!.replace(
                                ConstantStrings.TIME_PERCENT,
                                game.immunity.toString()
                            )
                        )
                    }
                }
            }
            game.immunity -= 1
        }, 20L, 20L)
    }


    override fun stop() {
        for (player in game.players) {
            playerHandlingAfterGame(player)
        }
        for (spectator in game.spectators) {
            playerHandlingAfterGame(spectator)
        }
        game.worldBoarderController.resetWorldBoarder()
    }

    private fun playerHandlingAfterGame(player: Player) {
        player.activePotionEffects.clear()
        player.inventory.clear()
        player.teleport(game.endLocation!!)
        player.inventory.setItem(8, Items.leaveItem)
        player.level = 0
        player.allowFlight = false
        PlayerHiding.showPlayerToOnlyGamePlayingPlayers(player)
        PlayerHiding.showGamePlayingPlayersToPlayer(player)
    }

    private fun givePlayerStartItems() {
        game.players.forEach {
            it.inventory.clear()
            it.inventory.addItem(Rifle.gun)
            it.inventory.addItem(Minigun.gun)
            it.inventory.addItem(Pistol.gun)
            it.inventory.addItem(Sniper.gun)
            it.inventory.setItem(8, Knife.knife)
        }
    }

    override val gameStateID: Int = GameStates.INGAME_STATE.id
}

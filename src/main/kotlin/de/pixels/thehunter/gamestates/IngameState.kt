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
import de.pixels.thehunter.util.game.ingame.general.PlayerDropping
import de.pixels.thehunter.util.game.ingame.general.PlayerHiding
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.misc.ConstantStrings
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player

class IngameState(game: Game) : GameState(game) {
    /**
     * Starts the game.
     * If randomPlayerDrop is true, drops players randomly on the game board.
     * Executes forEachPlayer() method.
     * If the game has a winning condition, advances to the next game state and returns.
     * Otherwise, gives players starting items, starts the immunity counter, shrinks the world border, and makes chests fall.
     */
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

    /**
     * Iterates over each player in the game and performs necessary actions.
     * This method sets the player's level to 0, teleports them to the appropriate spawn point (unless randomPlayerDrop is disabled),
     * makes them invulnerable, updates player statistics, creates a new scoreboard for the player, shows a title to the player,
     * reveals the player to only other players actively participating in the game, and shows only active playing players to the player.
     * Additionally, it handles spectators by teleporting them to the spectator location (if available), showing them a title,
     * hiding them from all players, and showing only active playing players to the spectator.
     */
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


    /**
     * Starts the immunity counter for the game.
     * During the immunity period, players are invulnerable to attacks.
     * The immunity counter decreases over time until it reaches 0.
     * Once the counter reaches 0, all players are no longer invulnerable.
     *
     * @param game The game object representing the current game state.
     */
    private fun startImmunityCounter() {
        Bukkit.getScheduler().runTaskTimer(TheHunter.instance, { task ->
            if (game.currentGameState !is IngameState)
                task.cancel()

            when (game.immunity) {
                0 -> {
                    game.players.forEach {
                        it.isInvulnerable = false
                        it.playSound(it.location, Sound.ENTITY_ENDER_DRAGON_GROWL, 1F, 1F)

                        TheHunter.instance.messagesFile.messagesMap["immunity-off"]?.let { str -> it.sendMessage(str) }
                        task.cancel()
                    }
                }

                else -> {
                    game.players.forEach {
                        it.isInvulnerable = true
                        it.playSound(it.location, Sound.BLOCK_LAVA_POP, 1F, 1F)
                        TheHunter.instance.messagesFile.messagesMap["immunity-message"]?.let { str ->
                            it.sendMessage(
                                str.replace(
                                    ConstantStrings.TIME_PERCENT,
                                    game.immunity.toString()
                                )
                            )
                        }
                    }
                }
            }
            game.immunity -= 1
        }, 20L, 20L)
    }


    /**
     * Stops the game and performs necessary actions for each player and spectator involved
     * Also resets the world boarder controller.
     */
    override fun stop() {
        for (player in game.players) {
            playerHandlingAfterGame(player)
        }
        for (spectator in game.spectators) {
            playerHandlingAfterGame(spectator)
        }
        game.worldBoarderController.resetWorldBoarder()
    }

    /**
     * Handles the player after the game has ended.
     *
     * @param player the player to handle
     */
    private fun playerHandlingAfterGame(player: Player) {
        player.activePotionEffects.clear()
        player.inventory.clear()
        player.teleport(game.endLocation ?: return)
        player.inventory.setItem(8, Items.leaveItem)
        player.level = 0
        player.allowFlight = false
        PlayerHiding.showPlayerToOnlyGamePlayingPlayers(player)
        PlayerHiding.showGamePlayingPlayersToPlayer(player)
    }

    /**
     * Clears the inventory of each player in the [game].
     * Adds the start items to each player's inventory.
     *
     * The start items include:
     *  - Rifle.gun
     *  - Minigun.gun
     *  - Pistol.gun
     *  - Sniper.gun
     *  - Knife.knife (placed in slot 8)
     *
     * @param game the game object containing the players
     */
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

    /**
     * The unique identifier for the current game state.
     *
     * This variable represents the identifier of the current game state, which is used
     * to differentiate between different states of the game. The value is an integer
     * corresponding to the identifier of the in-game state.
     */
    override val gameStateID: Int = GameStates.INGAME_STATE.id
}

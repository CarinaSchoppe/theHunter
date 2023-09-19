/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:10 PM All contents of "JoinGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.commands.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.commands.CommandUtil
import de.pixels.thehunter.countdowns.LobbyCountdown
import de.pixels.thehunter.util.builder.Items
import de.pixels.thehunter.util.game.ingame.GameSigns
import de.pixels.thehunter.util.game.ingame.PlayerHiding
import de.pixels.thehunter.util.game.ingame.PlayerTeamHead
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class JoinGame {

    /**
     * Joins a game for the specified sender.
     *
     * @param sender The CommandSender who wants to join the game.
     * @param command The name of the command used to join the game.
     * @param args The arguments passed with the command.
     */
    fun join(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.JOIN_COMMAND,
                1,
                Permissions.JOIN_COMMAND
            )
        )
            return

        val game = GamesHandler.games.find { it.name == args[0] }
        if (game == null) {
            TheHunter.instance.messagesFile.messagesMap["game-not-exists"]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        args[0]
                    )
                )
            }
            return
        }
        if (!playerAddingAndMessaging(sender as Player, game))
            return
        GameSigns.updateGameSigns(game)
        sender.inventory.clear()
        sender.inventory.setItem(8, Items.leaveItem)
        sender.inventory.setItem(5, PlayerTeamHead.createPlayerHead)
        sender.gameMode = GameMode.SURVIVAL
        sender.playSound(sender, Sound.BLOCK_LEVER_CLICK, 1f, 1f)
        if (game.currentCountdown is LobbyCountdown) {
            val countdown = game.currentCountdown as LobbyCountdown
            if (!countdown.isRunning && !countdown.isIdle)
                countdown.start()
        }
        PlayerHiding.showPlayerToOnlyGamePlayingPlayers(sender)
        PlayerHiding.showOnlyActiveGamePlayingPlayersToPlayer(sender)
    }

    /**
     * Adds a player to a game and sends appropriate messages.
     *
     * @param player The player to add to the game.
     * @param game The game to add the player to.
     *
     * @return Returns true if the player was successfully added to the game, false otherwise.
     */
    private fun playerAddingAndMessaging(player: Player, game: Game): Boolean {
        if (GamesHandler.playerInGames.containsKey(player) || GamesHandler.spectatorInGames.containsKey(player)) {
            TheHunter.instance.messagesFile.messagesMap["player-already-in-game"]?.let { player.sendMessage(it) }
            return false
        }
        if (game.players.size + 1 <= game.maxPlayers) {
            TheHunter.instance.messagesFile.messagesMap["join-game-successfully"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        game.name
                    )
                )
            }
            game.players.forEach {
                TheHunter.instance.messagesFile.messagesMap["player-joined-game"]?.let { message ->
                    it.sendMessage(
                        message.replace(
                            ConstantStrings.PLAYER_PERCENT,
                            player.name
                        )
                    )
                }
            }
            game.players.add(player)
            player.allowFlight = false
            GamesHandler.playerInGames[player] = game
            game.lobbyLocation?.let { player.teleport(it) }
            game.spectators.forEach {
                TheHunter.instance.messagesFile.messagesMap["player-joined-game"]?.let { message ->
                    it.sendMessage(
                        message.replace(
                            ConstantStrings.PLAYER_PERCENT,
                            player.name
                        )
                    )
                }
            }
        } else {
            GamesHandler.spectatorInGames[player] = game
            game.spectators.add(player)
            player.allowFlight = false
            TheHunter.instance.messagesFile.messagesMap["game-full-spectator"]?.let { player.sendMessage(it) }
            game.lobbyLocation?.let { player.teleport(it) }
        }
        return true
    }

}

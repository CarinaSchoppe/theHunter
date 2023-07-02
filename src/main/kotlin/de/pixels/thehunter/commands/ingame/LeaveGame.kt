/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:45 PM All contents of "LeaveGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.commands.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.commands.CommandUtil
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.util.game.ingame.GameSigns
import de.pixels.thehunter.util.game.ingame.PlayerHiding
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.game.management.Team
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot

class LeaveGame {
    /**
     * Allows a player or spectator to leave the current game or spectating session.
     *
     * @param sender The command sender.
     * @param command The command being executed.
     * @param args The command arguments.
     */
    fun leave(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.LEAVE_COMMAND,
                0,
                Permissions.LEAVE_COMMAND
            )
        )
            return

        if (!GamesHandler.playerInGames.containsKey(sender as Player) && !GamesHandler.spectatorInGames.containsKey(
                sender
            )
        ) {
            TheHunter.instance.messages.messagesMap["player-not-in-game"]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.PLAYER_PERCENT,
                        sender.name
                    )
                )
            }
            return
        }
        val game = GamesHandler.playerInGames[sender] ?: GamesHandler.spectatorInGames[sender] ?: return
        //player is in game


        if (game.currentGameState !is IngameState) {
            removePlayer(game, sender)
            return
        } else {
            game.deathChest.createDeathChest(sender)
            removePlayer(game, sender)
            TheHunter.instance.statsSystem.playerDied(sender)
            if (game.checkWinning())
                game.nextGameState()
        }
    }

    /**
     * Removes a player from the specified game.
     *
     * @param game The game from which the player should be removed.
     * @param player The player to remove from the game.
     */
    private fun removePlayer(game: Game, player: Player) {
        val team = game.teams.find { it.teamMembers.contains(player) }
        if (team != null) 
            Team.removePlayerFromTeam(player, player)

        game.spectators.remove(player)
        game.players.remove(player)
        GamesHandler.playerInGames.remove(player)
        GamesHandler.spectatorInGames.remove(player)
        game.backLocation?.let { player.teleport(it) }
        GameSigns.updateGameSigns(game)
        player.inventory.clear()
        player.level = 0
        player.scoreboard.clearSlot(DisplaySlot.SIDEBAR)

        playerMessagesAndHiding(game, player)
    }

    /**
     * Updates player messages and hiding for a specified player in the game.
     *
     * @param game The current game instance.
     * @param player The player whose messages and hiding need to be updated.
     */
    private fun playerMessagesAndHiding(game: Game, player: Player) {
        val team = game.teams.find { it.teamMembers.contains(player) }
        if (team != null)
            Team.removePlayerFromTeam(player, player)

        TheHunter.instance.messages.messagesMap["player-own-quit"]?.let { player.sendMessage(it) }

        PlayerHiding.showOnlyNonPlayingPlayersToPlayer(player)
        PlayerHiding.showPlayerOnlyToNonPlayingPlayers(player)
    }
}

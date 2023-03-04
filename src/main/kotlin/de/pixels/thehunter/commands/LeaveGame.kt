/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:45 PM All contents of "LeaveGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.commands

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.util.game.Game
import de.pixels.thehunter.util.game.GamesHandler
import de.pixels.thehunter.util.game.Team
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import de.pixels.thehunter.util.misc.Util
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot

class LeaveGame {
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
            sender.sendMessage(
                TheHunter.instance.messages.messagesMap["player-not-in-game"]!!.replace(
                    ConstantStrings.PLAYER_PERCENT,
                    sender.name
                )
            )
            return
        }
        val game = GamesHandler.playerInGames[sender] ?: GamesHandler.spectatorInGames[sender] ?: return
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

    private fun removePlayer(game: Game, player: Player) {
        val team = game.teams.find { it.teamMembers.contains(player) }
        if (team != null) {
            Team.removePlayerFromTeam(player, player)
        }
        game.spectators.remove(player)
        game.players.remove(player)
        GamesHandler.playerInGames.remove(player)
        GamesHandler.spectatorInGames.remove(player)
        player.teleport(game.backLocation!!)
        Util.updateGameSigns(game)
        player.inventory.clear()
        player.level = 0
        player.scoreboard.clearSlot(DisplaySlot.SIDEBAR)

        playerMessagesAndHiding(game, player)
    }

    private fun playerMessagesAndHiding(game: Game, player: Player) {
        val team = game.teams.find { it.teamMembers.contains(player) }
        if (team != null)
            Team.removePlayerFromTeam(player, player)
        Util.playerHiding(game, player)

        player.sendMessage(TheHunter.instance.messages.messagesMap["player-own-quit"]!!)
    }
}

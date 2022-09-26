/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "LeaveGame.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.game.Team
import de.carina.thehunter.util.misc.ConstantStrings
import de.carina.thehunter.util.misc.Permissions
import de.carina.thehunter.util.misc.Util
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot

class LeaveGame {
    fun leave(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, ConstantStrings.LEAVE_COMMAND, 0, Permissions.LEAVE_COMMAND))
            return

        if (!GamesHandler.playerInGames.containsKey(sender as Player)) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["player-not-in-game"]!!.replace(ConstantStrings.PLAYER_PERCENT, sender.name))
            return
        }
        val game = GamesHandler.playerInGames[sender]!!
        if (game.currentGameState !is IngameState) {
            removePlayer(sender)
            return
        } else {
            game.deathChest.createDeathChest(sender)
            removePlayer(sender)
            TheHunter.instance.statsSystem.playerDied(sender)
            if (game.checkWinning())
                game.nextGameState()
        }


    }

    private fun removePlayer(player: Player) {
        val game = GamesHandler.playerInGames[player]!!
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

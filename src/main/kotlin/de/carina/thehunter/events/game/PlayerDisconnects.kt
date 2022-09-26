/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "PlayerDisconnects.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.events.game

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.ConstantStrings
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerDisconnects : Listener {

    @EventHandler
    fun onPlayerDisconnect(event: PlayerQuitEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        val game = GamesHandler.playerInGames[event.player]!!

        if (game.currentGameState !is IngameState) {
            handlePlayer(event.player)
            return
        } else {
            handlePlayer(event.player)

        }
    }


    private fun handlePlayer(player: Player) {
        if (!GamesHandler.playerInGames.containsKey(player))
            return
        val game = GamesHandler.playerInGames[player]!!
        if (game.currentGameState !is IngameState)
            return
        TheHunter.instance.statsSystem.playerDied(player)
        player.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f)
        game.deathChest.createDeathChest(player)

        PlayerDies.generalHandling(player, game)

        game.players.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-died"]!!.replace(ConstantStrings.PLAYER_PERCENT, player.name))
        }
        game.spectators.filter { it.name != player.name }.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-died"]!!.replace(ConstantStrings.PLAYER_PERCENT, player.name))
        }
        if (game.checkWinning())
            game.nextGameState()

    }
}

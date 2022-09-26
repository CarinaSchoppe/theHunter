/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "PlayerKillsOtherOrDies.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.game

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.builder.Items
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.ConstantStrings
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import java.util.function.Consumer

class PlayerDies : Listener {

    @EventHandler
    fun onPlayerKillsOther(event: PlayerDeathEvent) {
        if (event.entity.killer != null) {
            if (event.entity.killer !is Player) {
                addDeathToPlayer(event, event.player)
            } else {
                playerKilledOther(event, event.player, event.entity.killer!!)
            }
        } else {
            addDeathToPlayer(event, event.player)
        }
    }

    private fun playerKilledOther(event: PlayerDeathEvent, player: Player, killer: Player) {
        if (!GamesHandler.playerInGames.containsKey(player))
            return
        if (!GamesHandler.playerInGames.containsKey(killer))
            return
        if (GamesHandler.playerInGames[player] != GamesHandler.playerInGames[killer])
            return
        val game = GamesHandler.playerInGames[player]!!
        if (game.currentGameState !is IngameState)
            return
        println("player killed other")
        killer.playSound(killer, Sound.ITEM_GOAT_HORN_SOUND_7, 1f, 1f)
        TheHunter.instance.statsSystem.playerKilledOtherPlayer(killer, player)
        game.deathChest.createDeathChest(player)

        Bukkit.getScheduler().runTaskLater(TheHunter.instance, Consumer {
            player.spigot().respawn()
            generalHandling(player, game)
        }, 1)
        event.deathMessage(Component.text(""))
        playerHiding(player, game, killer)
        player.sendMessage(TheHunter.instance.messages.messagesMap["player-own-killed-by-other"]!!.replace(ConstantStrings.PLAYER_PERCENT, killer.name))
        if (game.checkWinning())
            game.nextGameState()
    }

    private fun playerHiding(player: Player, game: Game, killer: Player) {
        game.players.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-killed-by-other"]!!.replace(ConstantStrings.PLAYER_PERCENT, player.name).replace(ConstantStrings.KILLER_PERCENT, killer.name))
            it.hidePlayer(TheHunter.instance, player)
            player.hidePlayer(TheHunter.instance, it)
        }
        game.spectators.filter { it.name != player.name }.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-killed-by-other"]!!.replace(ConstantStrings.PLAYER_PERCENT, player.name).replace(ConstantStrings.KILLER_PERCENT, killer.name))
            it.hidePlayer(TheHunter.instance, player)
            player.hidePlayer(TheHunter.instance, it)
        }
    }

    private fun addDeathToPlayer(event: PlayerDeathEvent, player: Player) {
        if (!GamesHandler.playerInGames.containsKey(player))
            return
        val game = GamesHandler.playerInGames[player]!!
        if (game.currentGameState !is IngameState)
            return

        TheHunter.instance.statsSystem.playerDied(player)
        player.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f)
        game.deathChest.createDeathChest(player)
        generalHandling(player, game)
        Bukkit.getScheduler().runTaskLater(TheHunter.instance, Consumer {
            player.spigot().respawn()
        }, 1)
        event.deathMessage(Component.text(""))
        game.players.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-died"]!!.replace(ConstantStrings.PLAYER_PERCENT, player.name))
        }
        game.spectators.filter { it.name != player.name }.forEach {
            it.sendMessage(TheHunter.instance.messages.messagesMap["player-died"]!!.replace(ConstantStrings.PLAYER_PERCENT, player.name))
        }
        player.sendMessage(TheHunter.instance.messages.messagesMap["player-own-died"]!!)
        if (game.checkWinning())
            game.nextGameState()

    }

    companion object {
        fun generalHandling(player: Player, game: Game) {
            game.players.remove(player)
            game.spectators.add(player)
            GamesHandler.playerInGames.remove(player)
            GamesHandler.spectatorInGames[player] = game
            player.inventory.clear()
            player.inventory.setItem(8, Items.leaveItem)
            player.inventory.setItem(9, Items.leaveItem)
            player.teleport(game.spectatorLocation!!)
            player.allowFlight = true
            Bukkit.getOnlinePlayers().forEach {
                it.hidePlayer(TheHunter.instance, player)
                if (!game.players.contains(it))
                    player.hidePlayer(TheHunter.instance, it)
                if (game.players.contains(it) || game.spectators.contains(it))
                    game.scoreBoard.createNewScoreboard(it)
            }


        }
    }

}

/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 19:33 by Carina The Latest changes made by Carina on 19.04.22, 19:33 All contents of "LobbyInteraction.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.misc

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.gamestates.LobbyState
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerDropItemEvent

class LobbyInteraction : Listener {

    @EventHandler
    fun onDropInLobby(event: PlayerDropItemEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player) && !GamesHandler.spectatorInGames.containsKey(event.player))
            return
        if (GamesHandler.playerInGames.containsKey(event.player)) {
            if (GamesHandler.playerInGames[event.player]!!.currentGameState is LobbyState)
                event.isCancelled = true
            return
        }
        if (GamesHandler.spectatorInGames.containsKey(event.player)) {
            if (GamesHandler.spectatorInGames[event.player]!!.currentGameState is LobbyState)
                event.isCancelled = true
            return
        }
    }

    @EventHandler
    fun onLobbyDamage(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            if (GamesHandler.playerInGames.containsKey(event.damager)) {
                if (GamesHandler.playerInGames[event.damager]!!.currentGameState is LobbyState)
                    event.isCancelled = true
                event.damage = 0.0
                event.damager.sendMessage(TheHunter.instance.messages.messagesMap["no-lobby-damage"]!!)

                return
            } else if (GamesHandler.spectatorInGames.containsKey(event.damager)) {
                if (GamesHandler.spectatorInGames[event.damager]!!.currentGameState is LobbyState)
                    event.isCancelled = true
                event.damage = 0.0
                event.damager.sendMessage(TheHunter.instance.messages.messagesMap["no-lobby-damage"]!!)
                return
            }
        } else {
            if (event.entity is Player) {
                if (GamesHandler.playerInGames.containsKey(event.entity)) {
                    if (GamesHandler.playerInGames[event.entity]!!.currentGameState is LobbyState)
                        event.isCancelled = true
                    event.damage = 0.0
                    return
                } else if (GamesHandler.spectatorInGames.containsKey(event.entity)) {
                    if (GamesHandler.spectatorInGames[event.entity]!!.currentGameState is LobbyState)
                        event.isCancelled = true
                    event.damage = 0.0
                    return
                }
            }
        }
    }

    @EventHandler
    fun onLobbyDamageRandom(event: EntityDamageEvent) {
        if (event.entity !is Player)
            return
        if (GamesHandler.playerInGames.containsKey(event.entity)) {
            if (GamesHandler.playerInGames[event.entity]!!.currentGameState !is IngameState) {
                event.isCancelled = true
                event.damage = 0.0
                return
            }

        } else if (GamesHandler.spectatorInGames.containsKey(event.entity)) {
            if (GamesHandler.spectatorInGames[event.entity]!!.currentGameState !is IngameState) {
                event.isCancelled = true
                event.damage = 0.0
                return
            }

        }
    }
}

/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "LobbyInteraction.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.misc

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.builder.Items
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.PlayerTeamHead
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent

class LobbyInteraction : Listener {

    @EventHandler
    fun onDropInLobby(event: PlayerDropItemEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player) && !GamesHandler.spectatorInGames.containsKey(event.player))
            return
        if (GamesHandler.playerInGames.containsKey(event.player)) {
            if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
                event.isCancelled = true
            return
        }
        if (GamesHandler.spectatorInGames.containsKey(event.player)) {
            if (GamesHandler.spectatorInGames[event.player]!!.currentGameState !is IngameState)
                event.isCancelled = true
            return
        }
    }


    private fun lobbyDamagePlayerDamager(event: EntityDamageByEntityEvent) {
        if (GamesHandler.playerInGames.containsKey(event.damager) || GamesHandler.spectatorInGames.containsKey(event.damager)) {
            if (GamesHandler.spectatorInGames[event.damager]!!.currentGameState !is IngameState) {
                event.isCancelled = true
                event.damage = 0.0
                if ((event.damager as Player).inventory.itemInMainHand.itemMeta != Items.leaveItem.itemMeta && (event.damager as Player).inventory.itemInMainHand.itemMeta != PlayerTeamHead.createPlayerHead().itemMeta)
                    event.damager.sendMessage(TheHunter.instance.messages.messagesMap["no-lobby-damage"]!!)

            }
        }
    }

    @EventHandler
    fun onPlayerLeavesGame(event: PlayerInteractEvent) {
        if (event.item == null) return
        if (!event.item!!.hasItemMeta())
            return
        if (event.item!!.itemMeta != Items.leaveItem.itemMeta)
            return
        if (event.action.isLeftClick)
            return
        event.player.performCommand("thehunter leave")
    }

    private fun lobbyDamagePLayer(event: EntityDamageByEntityEvent) {
        if (event.entity is Player) {
            if (GamesHandler.playerInGames.containsKey(event.entity) || GamesHandler.spectatorInGames.containsKey(event.entity)) {
                if (GamesHandler.playerInGames[event.entity]!!.currentGameState !is IngameState) {
                    event.isCancelled = true
                    event.damage = 0.0
                }
            }
        }
    }

    @EventHandler
    fun onLobbyDamage(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            lobbyDamagePlayerDamager(event)
        } else {
            lobbyDamagePLayer(event)
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


    @EventHandler
    fun onLobbyInventoryClick(event: InventoryClickEvent) {
        if (event.whoClicked !is Player)
            return
        if (GamesHandler.playerInGames.containsKey(event.whoClicked) || GamesHandler.spectatorInGames.containsKey(event.whoClicked)) {
            val game = if (GamesHandler.playerInGames.containsKey(event.whoClicked)) GamesHandler.playerInGames[event.whoClicked] else GamesHandler.spectatorInGames[event.whoClicked]
            if (game!!.currentGameState !is IngameState) {
                event.isCancelled = true
                (event.whoClicked as Player).updateInventory()
                return
            } else {
                if (GamesHandler.spectatorInGames.containsKey(event.whoClicked)) {
                    event.isCancelled = true
                    (event.whoClicked as Player).updateInventory()
                    return
                }
            }
        }
    }
}

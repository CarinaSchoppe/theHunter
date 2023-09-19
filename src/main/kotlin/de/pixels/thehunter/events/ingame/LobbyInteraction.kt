/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:50 PM All contents of "LobbyInteraction.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.events.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.util.builder.Items
import de.pixels.thehunter.util.game.ingame.PlayerTeamHead
import de.pixels.thehunter.util.game.management.GamesHandler
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent

class LobbyInteraction : Listener {

    /**
     * Event handler for when a player drops an item in the lobby.
     *
     * @param event The PlayerDropItemEvent object.
     */
    @EventHandler
    fun onDropInLobby(event: PlayerDropItemEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player) && !GamesHandler.spectatorInGames.containsKey(event.player))
            return
        if (GamesHandler.playerInGames.containsKey(event.player) || GamesHandler.spectatorInGames.containsKey(event.player) && (GamesHandler.playerInGames[event.player]
                ?: GamesHandler.spectatorInGames[event.player])?.currentGameState !is IngameState
        )
            event.isCancelled = true

    }


    /**
     * Applies lobby damage to the damager.
     *
     * @param event The EntityDamageByEntityEvent representing the damage event.
     */
    private fun lobbyDamagePlayerDamager(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player) return
        if (GamesHandler.playerInGames.containsKey(event.damager) || GamesHandler.spectatorInGames.containsKey(event.damager)) {
            val game = GamesHandler.spectatorInGames[event.damager] ?: GamesHandler.playerInGames[event.damager]
            if (game?.currentGameState !is IngameState) {
                event.isCancelled = true
                event.damage = 0.0

                if ((event.damager as Player).inventory.itemInMainHand.itemMeta != Items.leaveItem.itemMeta && (event.damager as Player).inventory.itemInMainHand.itemMeta != PlayerTeamHead.createPlayerHead.itemMeta)
                    TheHunter.instance.messagesFile.messagesMap["no-lobby-damage"]?.let { event.damager.sendMessage(it) }

            }
        }
    }

    /**
     * This method handles the event when a player leaves the game.
     *
     * @param event The PlayerInteractEvent representing the player leaving the game.
     */
    @EventHandler
    fun onPlayerLeavesGame(event: PlayerInteractEvent) {
        if (event.item == null || event.item?.hasItemMeta() == false || event.item?.itemMeta != Items.leaveItem.itemMeta || event.action.isLeftClick) return
  
        event.player.performCommand("thehunter leave")
    }

    /**
     * Cancels damage to players in the lobby.
     *
     * @param event The EntityDamageByEntityEvent object representing the damage event.
     */
    private fun lobbyDamagePLayer(event: EntityDamageByEntityEvent) {
        if (event.entity is Player && (GamesHandler.playerInGames.containsKey(event.entity) || GamesHandler.spectatorInGames.containsKey(
                event.entity
            )) && GamesHandler.playerInGames[event.entity]?.currentGameState !is IngameState
        ) {
            event.isCancelled = true
            event.damage = 0.0

        }
    }

    /**
     * Handles lobby damage events.
     *
     * @param event The EntityDamageByEntityEvent representing the lobby damage event.
     */
    @EventHandler
    fun onLobbyDamage(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            lobbyDamagePlayerDamager(event)
        } else {
            lobbyDamagePLayer(event)
        }
    }

    /**
     * EventHandler for handling EntityDamageEvent in the lobby.
     *
     * If the event entity is not a Player, the method returns without any action.
     * If the player is not currently in an ongoing game or a spectator, the event is cancelled and the damage is set to 0.0.
     *
     * @param event The EntityDamageEvent that is being handled.
     */
    @EventHandler
    fun onLobbyDamageRandom(event: EntityDamageEvent) {
        if (event.entity !is Player)
            return
        if ((GamesHandler.playerInGames[event.entity]
                ?: GamesHandler.spectatorInGames[event.entity])?.currentGameState !is IngameState
        ) {
            event.isCancelled = true
            event.damage = 0.0
        }
    }


    /**
     * Handler method for lobby inventory click events.
     *
     * This method is responsible for handling inventory click events in the lobby.
     * It checks if the click event was triggered by a player, and if the player is currently in a game or a spectator.
     * If the player is in a game, the method checks if the current game state is IngameState. If not, the event is cancelled.
     * Additionally, if the player is a spectator, the event is cancelled.
     *
     * @param event The InventoryClickEvent triggered by the player.
     */
    @EventHandler
    fun onLobbyInventoryClick(event: InventoryClickEvent) {
        if (event.whoClicked !is Player)
            return
        if (GamesHandler.playerInGames.containsKey(event.whoClicked) || GamesHandler.spectatorInGames.containsKey(event.whoClicked)) {
            val game =
                if (GamesHandler.playerInGames.containsKey(event.whoClicked)) GamesHandler.playerInGames[event.whoClicked] else GamesHandler.spectatorInGames[event.whoClicked]
            if (game?.currentGameState !is IngameState) {
                event.isCancelled = true
                return
            } else {
                if (GamesHandler.spectatorInGames.containsKey(event.whoClicked)) {
                    event.isCancelled = true
                    return
                }
            }
        }
    }
}

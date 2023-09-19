/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 7:38 PM All contents of "MapModify.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.events.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class MapModification : Listener {

    /**
     * EventHandler for the BlockBreakEvent.
     * Cancels the event and sends a message to the player if they are not in a game or if the game is not in the IngameState.
     * Cancels the event if mapModify is set to false for the player's current game.
     * Adds the broken block to the mapResetter's block list for the player's current game.
     *
     * @param event The BlockBreakEvent to handle.
     */
    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player = event.player
        if (!GamesHandler.playerInGames.containsKey(player))
            return

        if (GamesHandler.playerInGames[player]?.currentGameState !is IngameState) {
            event.isCancelled = true
            TheHunter.instance.messagesFile.messagesMap["cant-break-block"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.BLOCK_PERCENT,
                        event.block.type.name
                    )
                )
            }
            return
        }
        if (GamesHandler.playerInGames[player]?.mapModify == false) {
            event.isCancelled = true
            TheHunter.instance.messagesFile.messagesMap["cant-break-block"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.BLOCK_PERCENT,
                        event.block.type.name
                    )
                )
            }
            return
        }

        GamesHandler.playerInGames[event.player]?.mapResetter?.addBlockToList(event.block)
    }

    /**
     * Event handler method called when a block is placed.
     * This method checks if the player is in a game and if the game state is currently in progress.
     * If the player is not in a game or the game state is not in progress, the block place event is cancelled
     * and an appropriate message is sent to the player.
     * If the player is allowed to modify the map, the placed block is added to the map resetter block list.
     *
     * @param event The BlockPlaceEvent object representing the block place event.
     */
    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        if (!GamesHandler.playerInGames.containsKey(player))
            return

        if (GamesHandler.playerInGames[player]?.currentGameState !is IngameState) {
            event.isCancelled = true
            TheHunter.instance.messagesFile.messagesMap["cant-place-block"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.BLOCK_PERCENT,
                        event.block.type.name
                    )
                )
            }
            return
        }
        if (GamesHandler.playerInGames[player]?.mapModify == false) {
            event.isCancelled = true
            TheHunter.instance.messagesFile.messagesMap["cant-place-block"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.BLOCK_PERCENT,
                        event.block.type.name
                    )
                )
            }
            return
        }
        GamesHandler.playerInGames[event.player]?.mapResetter?.addBlockToList(event.block)
    }
}

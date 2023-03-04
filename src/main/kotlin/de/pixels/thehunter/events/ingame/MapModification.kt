/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 7:38 PM All contents of "MapModify.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.events.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.util.game.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class MapModification : Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player = event.player
        if (!GamesHandler.playerInGames.containsKey(player))
            return

        if (GamesHandler.playerInGames[player]!!.currentGameState !is IngameState) {
            event.isCancelled = true
            player.sendMessage(
                TheHunter.instance.messages.messagesMap["cant-break-block"]!!.replace(
                    ConstantStrings.BLOCK_PERCENT,
                    event.block.type.name
                )
            )
            return
        }
        if (!GamesHandler.playerInGames[player]!!.mapModify) {
            event.isCancelled = true
            player.sendMessage(
                TheHunter.instance.messages.messagesMap["cant-break-block"]!!.replace(
                    ConstantStrings.BLOCK_PERCENT,
                    event.block.type.name
                )
            )
            return
        }

        GamesHandler.playerInGames[event.player]!!.mapResetter.addBlockToList(event.block)
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        if (!GamesHandler.playerInGames.containsKey(player))
            return

        if (GamesHandler.playerInGames[player]!!.currentGameState !is IngameState) {
            event.isCancelled = true
            player.sendMessage(
                TheHunter.instance.messages.messagesMap["cant-place-block"]!!.replace(
                    ConstantStrings.BLOCK_PERCENT,
                    event.block.type.name
                )
            )
            return
        }
        if (!GamesHandler.playerInGames[player]!!.mapModify) {
            event.isCancelled = true
            player.sendMessage(
                TheHunter.instance.messages.messagesMap["cant-place-block"]!!.replace(
                    ConstantStrings.BLOCK_PERCENT,
                    event.block.type.name
                )
            )
            return
        }
        GamesHandler.playerInGames[event.player]!!.mapResetter.addBlockToList(event.block)
    }
}

/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 4/18/22, 11:29 PM All contents of "MapModify.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.misc

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

class MapModify : Listener {

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val player = event.player
        if (!GamesHandler.playerInGames.containsKey(player))
            return

        if (GamesHandler.playerInGames[player]!!.currentGameState !is IngameState) {
            event.isCancelled = true
            player.sendMessage(TheHunter.instance.messages.messagesMap["cant-break-block"]!!.replace("%block%", event.block.type.name))
            return
        }
        if (!GamesHandler.playerInGames[player]!!.mapModify) {
            event.isCancelled = true
            player.sendMessage(TheHunter.instance.messages.messagesMap["cant-break-block"]!!.replace("%block%", event.block.type.name))
            return
        }
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        if (!GamesHandler.playerInGames.containsKey(player))
            return

        if (GamesHandler.playerInGames[player]!!.currentGameState !is IngameState) {
            event.isCancelled = true
            player.sendMessage(TheHunter.instance.messages.messagesMap["cant-place-block"]!!.replace("%block%", event.block.type.name))
            return
        }
        if (!GamesHandler.playerInGames[player]!!.mapModify) {
            event.isCancelled = true
            player.sendMessage(TheHunter.instance.messages.messagesMap["cant-place-block"]!!.replace("%block%", event.block.type.name))
            return
        }
    }
}
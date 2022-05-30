/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 19:17 by Carina The Latest changes made by Carina on 19.04.22, 19:17 All contents of "ItemHandler.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.items

import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object ItemHandler {

    fun shouldInteractWithItem(event: PlayerInteractEvent, item: ItemStack, itemString: String): Boolean {
        if (event.item == null)
            return false
        if (!event.player.hasPermission("theHunter.$itemString"))
            return false
        if (!event.item!!.hasItemMeta()) return false
        if (event.item!!.itemMeta != item.itemMeta)
            return false
        if (event.player.inventory.itemInMainHand == null)
            return false
        if (!event.player.inventory.itemInMainHand.hasItemMeta())
            return false
        if (event.player.inventory.itemInMainHand.itemMeta != item.itemMeta)
            return false
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return false
        if (!event.action.isRightClick)
            return false

        if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
            return false

        if (GamesHandler.playerInGames[event.player]!!.gameItems.items[itemString] == false)
            return false
        event.isCancelled = true
        return true
    }

    fun removeOneItemOfPlayer(player: Player) {
        val item = player.inventory.itemInMainHand
        if (item.amount == 1) {
            player.inventory.setItemInMainHand(null)
            return
        } else {
            item.amount -= 1
            player.inventory.setItemInMainHand(item)
        }

    }
}
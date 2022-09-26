/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "ItemHandler.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.items

import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.Permissions
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object ItemHandler {

    fun shouldNotInteractWithItem(event: PlayerInteractEvent, item: ItemStack, itemString: String): Boolean {
        if (event.item == null)
            return true
        if (!event.player.hasPermission("${Permissions.PERMISSION_PREFIX}.$itemString"))
            return true
        if (!event.item!!.hasItemMeta()) return true
        if (event.item!!.itemMeta != item.itemMeta)
            return true
        if (!event.player.inventory.itemInMainHand.hasItemMeta())
            return true
        if (event.player.inventory.itemInMainHand.itemMeta != item.itemMeta)
            return true
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return true
        if (!event.action.isRightClick)
            return true

        if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
            return true

        if (GamesHandler.playerInGames[event.player]!!.gameItems.items[itemString] == false)
            return true
        event.isCancelled = true
        return false
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

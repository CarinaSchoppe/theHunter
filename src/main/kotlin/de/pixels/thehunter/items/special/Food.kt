/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 11:08 PM All contents of "Food.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.items.special

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.items.ItemHandler
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerInteractEvent

class Food : Listener {
    companion object {
        val food =
            ItemBuilder(Material.BEETROOT_SOUP).addDisplayName(TheHunter.prefix + "§6Food")
                .addLore("§7Click to regenerate food!").addLore("§7Right-click to activate")
                .addEnchantment(Enchantment.DURABILITY, 1).build()
    }


    @EventHandler
    fun onPlayerFood(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, food, "Food"))
            return

        if (event.player.foodLevel + GamesHandler.playerInGames[event.player]!!.gameItems.items[ConstantStrings.FOOD_RECHARGE] as Int <= 20)
            event.player.foodLevel += GamesHandler.playerInGames[event.player]!!.gameItems.items[ConstantStrings.FOOD_RECHARGE] as Int
        else if (event.player.foodLevel == 20)
            return
        else
            event.player.foodLevel = 20
        ItemHandler.removeOneItemOfPlayer(event.player)
        event.player.sendMessage(
            TheHunter.instance.messages.messagesMap[ConstantStrings.FOOD_RECHARGE]!!.replace(
                "%recharge%",
                (GamesHandler.playerInGames[event.player]!!.gameItems.items[ConstantStrings.FOOD_RECHARGE] as Int).toString()
            )
        )
    }

    @EventHandler
    fun onPlayerFoodLoose(event: FoodLevelChangeEvent) {
        if (event.entity !is Player)
            return
        val player = event.entity as Player
        if (!GamesHandler.playerInGames.containsKey(player))
            return
        if (GamesHandler.playerInGames[player]!!.currentGameState !is IngameState)
            return
        if (GamesHandler.playerInGames[player]!!.gameItems.items["Food"] != false)
            return

        player.foodLevel = 20
        event.isCancelled = true

    }
}

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 11:08 PM All contents of "Food.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.items.special

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.items.util.ItemHandler
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.management.GamesHandler
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
        /**
         * A variable representing a food item used for regeneration.
         *
         * The {@code food} variable is an instance of the {@link org.bukkit.inventory.ItemStack} class,
         * specifically an item of type {@link org.bukkit.Material#BEETROOT_SOUP}.
         *
         * This food item is created using the {@link ItemBuilder} class, with additional properties set:
         * - A display name with the value of {@link TheHunter#prefix} concatenated with "§6Food".
         * - Lore lines: "§7Click to regenerate food!" and "§7Right-click to activate".
         * - An enchantment of {@link Enchantment#DURABILITY} with level 1.
         *
         * To obtain this food item, call the {@link ItemBuilder#build()} method on the {@code food} variable.
         */
        val food =
            ItemBuilder(Material.BEETROOT_SOUP).addDisplayName(TheHunter.prefix + "§6Food")
                .addLore("§7Click to regenerate food!").addLore("§7Right-click to activate")
                .addEnchantment(Enchantment.DURABILITY, 1).build()
    }


    /**
     * Event handler for replenishing player's food level.
     *
     * @param event The PlayerInteractEvent triggering the replenishment.
     */
    @EventHandler
    fun onPlayerFood(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, food, "Food"))
            return

        if (event.player.foodLevel + GamesHandler.playerInGames[event.player]?.gameItems?.items?.get(ConstantStrings.FOOD_RECHARGE) as Int <= 20)
            event.player.foodLevel += GamesHandler.playerInGames[event.player]?.gameItems?.items?.get(ConstantStrings.FOOD_RECHARGE) as Int
        else if (event.player.foodLevel == 20)
            return
        else
            event.player.foodLevel = 20
        ItemHandler.removeOneItemOfPlayer(event.player)
        TheHunter.instance.messages.messagesMap[ConstantStrings.FOOD_RECHARGE]?.let {
            event.player.sendMessage(
                it.replace(
                    "%recharge%",
                    (GamesHandler.playerInGames[event.player]?.gameItems?.items?.get(ConstantStrings.FOOD_RECHARGE) as Int).toString()
                )
            )
        }
    }

    /**
     * An event handler method to handle player food level decreasing event.
     *
     * @param event The FoodLevelChangeEvent object representing the event.
     *              event.entity must be an instance of Player.
     * @throws IllegalArgumentException if event.entity is not an instance of Player.
     */
    @EventHandler
    fun onPlayerFoodLoose(event: FoodLevelChangeEvent) {
        if (event.entity !is Player)
            return
        val player = event.entity as Player
        if (!GamesHandler.playerInGames.containsKey(player) || GamesHandler.playerInGames[player]?.currentGameState !is IngameState || GamesHandler.playerInGames[player]?.gameItems?.items?.get("Food") != false)
            return


        player.foodLevel = 20
        event.isCancelled = true

    }
}

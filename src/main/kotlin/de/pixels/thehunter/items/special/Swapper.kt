/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 3:03 PM All contents of "Swapper.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.items.special

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.util.ItemHandler
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class Swapper : Listener {
    companion object {

        /**
         * This variable represents a swapper item that can be used in a game.
         *
         * It is built by using the ItemBuilder class to customize the item's properties such as material, display name,
         * lore, enchantments, etc.
         *
         * Usage:
         *     To use the swapper, simply right-click on it to activate the swapping effect.
         *     When activated, the swapper will swap positions with the player it is used on.
         *
         * Example:
         *     val swapper = ItemBuilder(Material.TNT)
         *                     .addDisplayName(TheHunter.prefix + "§6Swapper")
         *                     .addLore("§7Click to swap with a player!")
         *                     .addLore("§7Right-click to activate")
         *                     .addEnchantment(Enchantment.DURABILITY, 1)
         *                     .build()
         */
        val swapper = ItemBuilder(Material.TNT).addDisplayName(TheHunter.prefix + "§6Swapper")
            .addLore("§7Click to swap with a player!").addLore("§7Right-click to activate")
            .addEnchantment(Enchantment.DURABILITY, 1).build()

    }

    /**
     * Handles the player swap event triggered by a player interacting with an item.
     *
     * @param event The PlayerInteractEvent instance.
     */
    @EventHandler
    fun onPlayerSwap(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, swapper, "Swapper"))
            return
        event.isCancelled = true

        val targets = GamesHandler.playerInGames[event.player]?.players?.filter { it != event.player }
        if (targets?.isEmpty() == true)
            return
        val target = targets?.random()
        ItemHandler.removeOneItemOfPlayer(event.player)
        val targetLocation = target?.location
        target?.teleport(event.player.location)
        event.player.teleport(targetLocation ?: return)
        event.player.playSound(event.player, Sound.BLOCK_BIG_DRIPLEAF_BREAK, 1f, 1f)
        target.playSound(target, Sound.BLOCK_BIG_DRIPLEAF_BREAK, 1f, 1f)
        TheHunter.instance.messages.messagesMap["player-swapped"]?.let {
            target.sendMessage(
                it.replace(
                    ConstantStrings.PLAYER_PERCENT,
                    event.player.name
                )
            )
        }
        TheHunter.instance.messages.messagesMap["player-swapped"]?.let {
            event.player.sendMessage(
                it.replace(
                    ConstantStrings.PLAYER_PERCENT,
                    target.name
                )
            )
        }
    }
}

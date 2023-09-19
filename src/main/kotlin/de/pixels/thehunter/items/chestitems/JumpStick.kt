/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/2/22, 2:15 PM All contents of "JumpStick.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.items.chestitems

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.util.ItemHandler
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.management.GamesHandler
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class JumpStick : Listener {

    companion object {
        /**
         * This variable represents a mutable map that associates a player with an integer value.
         * It is used to keep track of the number of times each player is used in a game.
         *
         * The keys of the map are instances of the `Player` class, and the values are integers that represent the number of times a player is used.
         *
         * This map can be modified by adding new entries, updating existing entries, or removing entries.
         * The map allows duplicate keys and null values.
         *
         * Example usage:
         * ```
         * val player1 = Player("Alice")
         * val player2 = Player("Bob")
         *
         * uses[player1] = 2       // Adds an entry: player1 is used 2 times
         * uses[player2] = 3       // Adds an entry: player2 is used 3 times
         * uses[player1] = 4       // Updates an entry: player1 is now used 4 times
         * uses.remove(player2)    // Removes an entry: player2 is no longer used
         *
         * println(uses[player1])  // Prints: 4
         * ```
         *
         * @see Player
         */
        val uses = mutableMapOf<Player, Int>()

        /**
         * The JumpStick variable is an instance of the ItemBuilder class that represents a jump stick item.
         * It is used in the game The Hunter.
         *
         * The jump stick allows players to jump into a specific direction with a certain power when clicked.
         * It can be activated by right-clicking.
         *
         * The jump stick is created using the Material.STICK as the base material.
         * It also has a display name that consists of The Hunter prefix followed by the string "JumpStick" in a gold color.
         * The lore of the jump stick includes a description of its functionality and instructions for activation.
         * Additionally, the jump stick is enchanted with the Durability enchantment at level 1.
         */
        val jumpStick = ItemBuilder(Material.STICK).addDisplayName(TheHunter.prefix + "§6JumpStick")
            .addLore("§aClick to jump into an direction with power").addLore("§7Right-click to activate")
            .addEnchantment(Enchantment.DURABILITY, 1).build()
    }


    /**
     * Handles the event when a player uses a jump stick item.
     *
     * @param event The PlayerInteractEvent being triggered.
     */
    @EventHandler
    fun onJumpStickUse(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, jumpStick, "JumpStrick"))
            return
        val player = event.player
        event.isCancelled = true
        player.playSound(player, Sound.BLOCK_POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON, 1f, 1f)
        if (uses.containsKey(player) && GamesHandler.playerInGames[player]?.gameItems?.items?.get("jump-stick-uses") as Int <= (uses[player] ?: return)) {
            TheHunter.instance.messagesFile.messagesMap["jump-stick-broke"]?.let { player.sendMessage(it) }
            ItemHandler.removeOneItemOfPlayer(event.player)
            player.playSound(player, Sound.BLOCK_ANVIL_BREAK, 1f, 1f)
            uses[player] = 0
            return
        }

        player.velocity =
            player.eyeLocation.direction.multiply(GamesHandler.playerInGames[player]?.gameItems?.items?.get("jump-stick-power") as Int)
        if (!uses.containsKey(player))
            uses[player] = 1
        else
            uses[player] = uses[player]?.plus(1) ?: return
    }

}

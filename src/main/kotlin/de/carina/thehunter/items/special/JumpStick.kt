/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/7/22, 3:33 AM by Carina The Latest changes made by Carina on 6/7/22, 3:25 AM All contents of "JumpStick.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.items.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.items.ItemHandler
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class JumpStick : Listener {

    companion object {
        val uses = mutableMapOf<Player, Int>()
        val jumpStick = ItemBuilder(Material.STICK).addDisplayName(TheHunter.prefix + "§6JumpStick").addLore("§aClick to jump into an direction with power").addLore("§7Right-click to activate").addEnchantment(Enchantment.DURABILITY, 1).build()


    }


    @EventHandler
    fun onJumpStickUse(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, jumpStick, "JumpStrick"))
            return
        val player = event.player
        event.isCancelled = true
        if (uses.containsKey(player) && uses[player]!! >= GamesHandler.playerInGames[player]!!.gameItems.items["jump-stick-uses"] as Int) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["jump-stick-broke"]!!)
            ItemHandler.removeOneItemOfPlayer(event.player)
            uses[player] = 0
            return
        }

        player.velocity = player.eyeLocation.direction.multiply(GamesHandler.playerInGames[player]!!.gameItems.items["jump-stick-power"] as Int)
        if (!uses.containsKey(player))
            uses[player] = 1
        else
            uses[player] = uses[player]!! + 1
    }

}

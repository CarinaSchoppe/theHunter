/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/2/22, 2:15 PM All contents of "JumpStick.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.items.special

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
        val uses = mutableMapOf<Player, Int>()
        val jumpStick = ItemBuilder(Material.STICK).addDisplayName(TheHunter.prefix + "§6JumpStick")
            .addLore("§aClick to jump into an direction with power").addLore("§7Right-click to activate")
            .addEnchantment(Enchantment.DURABILITY, 1).build()
    }


    @EventHandler
    fun onJumpStickUse(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, jumpStick, "JumpStrick"))
            return
        val player = event.player
        event.isCancelled = true
        player.playSound(player, Sound.BLOCK_POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON, 1f, 1f)
        if (uses.containsKey(player) && GamesHandler.playerInGames[player]?.gameItems?.items?.get("jump-stick-uses") as Int <= (uses[player] ?: return)) {
            TheHunter.instance.messages.messagesMap["jump-stick-broke"]?.let { player.sendMessage(it) }
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

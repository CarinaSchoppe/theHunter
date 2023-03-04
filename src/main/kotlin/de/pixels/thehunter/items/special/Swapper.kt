/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 3:03 PM All contents of "Swapper.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.items.special

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.ItemHandler
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class Swapper : Listener {
    companion object {

        val swapper = ItemBuilder(Material.TNT).addDisplayName(TheHunter.prefix + "§6Swapper")
            .addLore("§7Click to swap with a player!").addLore("§7Right-click to activate")
            .addEnchantment(Enchantment.DURABILITY, 1).build()

    }

    @EventHandler
    fun onPlayerSwap(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, swapper, "Swapper"))
            return
        event.isCancelled = true

        val targets = GamesHandler.playerInGames[event.player]!!.players.filter { it != event.player }
        if (targets.isEmpty())
            return
        val target = targets.random()
        ItemHandler.removeOneItemOfPlayer(event.player)
        val targetLocation = target.location
        target.teleport(event.player.location)
        event.player.teleport(targetLocation)
        event.player.playSound(event.player, Sound.BLOCK_BIG_DRIPLEAF_BREAK, 1f, 1f)
        target.playSound(target, Sound.BLOCK_BIG_DRIPLEAF_BREAK, 1f, 1f)
        target.sendMessage(
            TheHunter.instance.messages.messagesMap["player-swapped"]!!.replace(
                ConstantStrings.PLAYER_PERCENT,
                event.player.name
            )
        )
        event.player.sendMessage(
            TheHunter.instance.messages.messagesMap["player-swapped"]!!.replace(
                ConstantStrings.PLAYER_PERCENT,
                target.name
            )
        )
    }
}

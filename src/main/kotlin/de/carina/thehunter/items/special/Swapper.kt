/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/7/22, 3:33 AM by Carina The Latest changes made by Carina on 6/7/22, 3:33 AM All contents of "Swapper.kt" are protected by copyright.
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
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class Swapper : Listener {
    companion object {

        val swapper = ItemBuilder(Material.TNT).addDisplayName(TheHunter.prefix + "§6Swapper").addLore("§7Click to swap with a player!").addLore("§7Right-click to activate").addEnchantment(Enchantment.DURABILITY, 1).build()

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
        target.sendMessage(TheHunter.instance.messages.messagesMap["player-swapped"]!!.replace("%player%", event.player.name))
        event.player.sendMessage(TheHunter.instance.messages.messagesMap["player-swapped"]!!.replace("%player%", target.name))
    }
}
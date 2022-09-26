/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/7/22, 3:33 AM by Carina The Latest changes made by Carina on 6/7/22, 3:33 AM All contents of "Tracker.kt" are protected by copyright.
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
import de.carina.thehunter.util.misc.ConstantStrings
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class Tracker : Listener {
    companion object {

        val tracker = ItemBuilder(Material.COMPASS).addDisplayName(TheHunter.prefix + "§6Tracker").addLore("§7Click to track a player!").addLore("§7Right-click to activate").addEnchantment(Enchantment.DURABILITY, 1).build()


    }

    @EventHandler
    fun onPlayerTrack(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, tracker, "Tracker"))
            return
        event.isCancelled = true
        //get the closest Player to the event.player based on the distance of their location
        val closestPlayers = GamesHandler.playerInGames[event.player]!!.players.filter { it.uniqueId != event.player.uniqueId }
        if (closestPlayers.isEmpty())
            return
        var closest = closestPlayers.first()
        for (players in closestPlayers) {
            if (event.player.location.distance(players.location) < event.player.location.distance(closest.location))
                closest = players
        }

        event.player.compassTarget = closest.location
        event.player.sendMessage(TheHunter.instance.messages.messagesMap["tracker-distance"]!!.replace(ConstantStrings.PLAYER_PERCENT, closest.name).replace("%distance%", event.player.location.distance(closest.location).toInt().toString()))


    }
}

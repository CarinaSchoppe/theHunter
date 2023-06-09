/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 7:17 PM All contents of "Tracker.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.items.special

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.util.ItemHandler
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class Tracker : Listener {
    companion object {

        val tracker = ItemBuilder(Material.COMPASS).addDisplayName(TheHunter.prefix + "§6Tracker")
            .addLore("§7Click to track a player!").addLore("§7Right-click to activate")
            .addEnchantment(Enchantment.DURABILITY, 1).build()


    }

    @EventHandler
    fun onPlayerTrack(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, tracker, "Tracker"))
            return
        event.isCancelled = true
        //get the closest Player to the event.player based on the distance of their location
        val closestPlayers =
            GamesHandler.playerInGames[event.player]?.players?.filter { it.uniqueId != event.player.uniqueId }
        if (closestPlayers?.isEmpty() == true)
            return
        var closest = closestPlayers?.first() ?: return
        for (players in closestPlayers) {
            if (event.player.location.distance(players.location) < event.player.location.distance(closest.location))
                closest = players
        }

        event.player.compassTarget = closest.location
        TheHunter.instance.messages.messagesMap["tracker-distance"]?.replace(
            ConstantStrings.PLAYER_PERCENT,
            closest.name
        )?.let {
            event.player.sendMessage(
                it.replace("%distance%", event.player.location.distance(closest.location).toInt().toString())
            )
        }


    }
}

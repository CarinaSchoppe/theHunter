/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 3:25 PM All contents of "Healer.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.items.special

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.util.ItemHandler
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class Healer : Listener {
    companion object {
        val healer =
            ItemBuilder(Material.RED_DYE).addDisplayName(TheHunter.prefix + "§7Healer").addLore("§7Heals you!")
                .addLore("§7Right-click to activate").build()
    }


    @EventHandler
    fun onHealerItemClick(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, healer, "Healer"))
            return
        event.isCancelled = true
        if (event.player.health == 20.0)
            return
        if (event.player.health + GamesHandler.playerInGames[event.player]?.gameItems?.items?.get(ConstantStrings.HEALER_AMOUNT) as Int <= 20)
            event.player.health += GamesHandler.playerInGames[event.player]?.gameItems?.items?.get(ConstantStrings.HEALER_AMOUNT) as Int
        else
            event.player.health = 20.0
        ItemHandler.removeOneItemOfPlayer(event.player)

        TheHunter.instance.messages.messagesMap["healer-message"]?.let {
            LegacyComponentSerializer.legacySection().deserialize(
                it.replace(
                    "%heal%",
                    (GamesHandler.playerInGames[event.player]?.gameItems?.items?.get(ConstantStrings.HEALER_AMOUNT) as Int).toString()
                )
            )
        }.let { event.player.sendActionBar(it ?: return) }
        
    }
}

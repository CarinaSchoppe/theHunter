/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:29 PM All contents of "GamesInventoryList.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.game.ingame

import de.pixels.thehunter.TheHunter
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class GamesInventoryList : Listener {


    @EventHandler
    fun onInventoryJoin(event: InventoryClickEvent) {
        if (PlainTextComponentSerializer.plainText()
                .serialize(event.view.title()) != PlainTextComponentSerializer.plainText()
                .serialize(LegacyComponentSerializer.legacySection().deserialize(TheHunter.prefix + "§6Games"))
            || event.currentItem == null || event.currentItem?.type != Material.ACACIA_SIGN
        ) {
            return
        }
        event.isCancelled = true
        val arenaName = PlainTextComponentSerializer.plainText().serialize(event.currentItem?.itemMeta?.displayName() ?: return)
        if (event.whoClicked !is Player)
            return
        (event.whoClicked as Player).performCommand("thehunter join $arenaName")
    }
}

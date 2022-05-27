/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 21:33 by Carina The Latest changes made by Carina on 19.04.22, 21:33 All contents of "GamesInventoryList.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.misc

import de.carina.thehunter.TheHunter
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
        if (PlainTextComponentSerializer.plainText().serialize(event.view.title()) != PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize(TheHunter.prefix + "§6Games"))) {
            //TODO: Test hier
            println("text2:" + PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize(TheHunter.prefix + "§6Games")))
            println("text3:" + PlainTextComponentSerializer.plainText().serialize(event.view.title()))
            return
        }
        event.isCancelled = true
        if (event.currentItem == null)
            return
        if (event.currentItem!!.type != Material.ACACIA_SIGN)
            return
        val arenaName = PlainTextComponentSerializer.plainText().serialize(event.currentItem!!.itemMeta.displayName()!!)
        if (event.whoClicked !is Player)
            return
        (event.whoClicked as Player).performCommand("thehunter join " + arenaName)
    }
}
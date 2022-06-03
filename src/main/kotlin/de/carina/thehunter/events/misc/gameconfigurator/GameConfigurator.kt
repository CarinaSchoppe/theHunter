/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "GameConfigurator.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.misc.gameconfigurator

import de.carina.thehunter.util.builder.Inventories
import de.carina.thehunter.util.builder.Items
import de.carina.thehunter.util.misc.Permissions
import de.carina.thehunter.util.misc.Util
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class GameConfigurator : Listener {

    @EventHandler
    fun onGameConfigEdit(event: InventoryClickEvent) {
        if (!Util.currentGameSelected.containsKey(event.whoClicked as Player)) return
        if (PlainTextComponentSerializer.plainText().serialize(event.view.title()) != PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize("§d${Util.currentGameSelected[event.whoClicked as Player]}§6: Game Setup")))
            return
        if (!(event.whoClicked as Player).hasPermission(Permissions.SETUP_GUI))
            return
        event.isCancelled = true

        if (event.currentItem == null)
            return

        if (event.currentItem!!.itemMeta == null)
            return

        when (event.currentItem!!.itemMeta!!) {
            Items.addLobbyButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config lobbyspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            Items.addBackButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config backspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            Items.addEndButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config endspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            Items.finishButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config finish ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            Items.addArenaCenterButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config arenacenter ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            Items.addSpectatorButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config spectatorspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            Items.addSpawnButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config playerspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            Items.settingsHead.itemMeta -> {
                (event.whoClicked as Player).openInventory(Inventories.createSettingsInventory(Util.currentGameSelected[event.whoClicked as Player]!!))
            }
        }

    }
}
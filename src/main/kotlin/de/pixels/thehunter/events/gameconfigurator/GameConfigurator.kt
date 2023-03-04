/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 2:26 PM All contents of "GameConfigurator.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.events.gameconfigurator

import de.pixels.thehunter.util.builder.Inventories
import de.pixels.thehunter.util.builder.Items
import de.pixels.thehunter.util.misc.Permissions
import de.pixels.thehunter.util.misc.Util
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
        if (!(event.whoClicked as Player).hasPermission(Permissions.SETUP_GUI)) return
        if (PlainTextComponentSerializer.plainText()
                .serialize(event.view.title()) != PlainTextComponentSerializer.plainText().serialize(
                LegacyComponentSerializer.legacySection()
                    .deserialize("§d${Util.currentGameSelected[event.whoClicked as Player]!!.name}§6: Game Setup")
            )
        ) return
        event.isCancelled = true
        if (event.currentItem == null) return
        if (event.currentItem!!.itemMeta == null) return
        when (event.currentItem!!.itemMeta!!) {

            Items.addLobbyButton.itemMeta -> {
                if (event.isLeftClick) (event.whoClicked as Player).performCommand("thehunter setup config lobbyspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
                else (event.whoClicked as Player).performCommand("thehunter setup remove lobbyspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")

            }

            Items.addBackButton.itemMeta -> {
                if (event.isLeftClick) (event.whoClicked as Player).performCommand("thehunter setup config backspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
                else (event.whoClicked as Player).performCommand("thehunter setup remove backspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            }

            Items.addEndButton.itemMeta -> {
                if (event.isLeftClick) (event.whoClicked as Player).performCommand("thehunter setup config endspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
                else (event.whoClicked as Player).performCommand("thehunter setup remove endspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            }

            Items.finishButton.itemMeta -> {
                if (event.isLeftClick) (event.whoClicked as Player).performCommand("thehunter setup config finish ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
                else (event.whoClicked as Player).performCommand("thehunter delete ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            }

            Items.addArenaCenterButton.itemMeta -> {
                if (event.isLeftClick) (event.whoClicked as Player).performCommand("thehunter setup config arenacenter ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
                else (event.whoClicked as Player).performCommand("thehunter setup remove arenacenter ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            }

            Items.addSpectatorButton.itemMeta -> {
                if (event.isLeftClick) (event.whoClicked as Player).performCommand("thehunter setup config spectatorspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
                else (event.whoClicked as Player).performCommand("thehunter setup remove spectatorspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            }

            Items.addSpawnButton.itemMeta -> {
                if (event.isLeftClick) (event.whoClicked as Player).performCommand("thehunter setup config playerspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
                else (event.whoClicked as Player).performCommand("thehunter setup remove playerspawn ${Util.currentGameSelected[event.whoClicked as Player]!!.name}")
            }

            Items.settingsHead.itemMeta -> {
                (event.whoClicked as Player).openInventory(Inventories.createSettingsInventory(Util.currentGameSelected[event.whoClicked as Player]!!))
            }
        }
    }
}

/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "SettingsConfigurator.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.misc.gameconfigurator

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.Items
import de.carina.thehunter.util.misc.Util
import de.carina.thehunter.util.misc.WorldboarderController
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class SettingsConfigurator : Listener {

    companion object {
        private const val minPlayersHigh = 20
        private const val minPlayersLow = 1
    }

    //TODO: hier
    @EventHandler
    fun onInteractWithSettings(event: InventoryClickEvent) {
        if (!Util.currentGameSelected.containsKey(event.whoClicked as Player)) return

        if (!(event.whoClicked as Player).hasPermission("thehunter.settingsgui"))
            return
        if (PlainTextComponentSerializer.plainText().serialize(event.view.title()) != PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize("§d${Util.currentGameSelected[event.whoClicked as Player]!!.name}§6: Game Settings")))
            return

        event.isCancelled = true
        if (event.currentItem == null)
            return

        if (event.currentItem!!.itemMeta == null)
            return

        val item = getItemObject(event) ?: return


        val type = if (event.currentItem?.type == Material.RED_WOOL) false else if (event.currentItem?.type == Material.GREEN_WOOL) true else return
        when (item.itemMeta) {
            Items.borderSize.itemMeta -> borderSize(type, event.whoClicked as Player)
            Items.saveButton.itemMeta -> Util.currentGameSelected[event.whoClicked as Player]!!.finish()
        }

    }


    private fun minPlayers(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]!!.minPlayers + 1 > minPlayersHigh) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["min-players-to-high".replace("%players%", minPlayersHigh.toString()).replace("%game%", Util.currentGameSelected[player]!!.name)]!!)
                return
            }
            Util.currentGameSelected[player]!!.minPlayers++
            player.sendMessage(TheHunter.instance.messages.messagesMap["min-players-increased".replace("%players%", Util.currentGameSelected[player]!!.minPlayers.toString()).replace("%game%", Util.currentGameSelected[player]!!.name)]!!)

        } else {
            if (Util.currentGameSelected[player]!!.minPlayers - 1 < minPlayersLow) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["min-players-to-low".replace("%players%", minPlayersLow.toString()).replace("%game%", Util.currentGameSelected[player]!!.name)]!!)

                return
            }
            Util.currentGameSelected[player]!!.minPlayers--
            player.sendMessage(TheHunter.instance.messages.messagesMap["min-players-reduced".replace("%players%", Util.currentGameSelected[player]!!.minPlayers.toString()).replace("%game%", Util.currentGameSelected[player]!!.name)]!!)

        }
    }

    private fun borderSize(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize + 10 > WorldboarderController.toHigh) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["bordersize-to-high".replace("%size%", WorldboarderController.toHigh.toString()).replace("%game%", Util.currentGameSelected[player]!!.name)]!!)
                return
            }
            Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize += 10
            player.sendMessage(TheHunter.instance.messages.messagesMap["bordersize-plus".replace("%size%", Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize.toString()).replace("%game%", Util.currentGameSelected[player]!!.name)]!!)
        } else {
            if (Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize - 10 > WorldboarderController.toLow) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["bordersize-to-low".replace("%size%", WorldboarderController.toHigh.toString()).replace("%game%", Util.currentGameSelected[player]!!.name)]!!)
                return
            }
            Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize -= 10
            player.sendMessage(TheHunter.instance.messages.messagesMap["bordersize-minus".replace("%size%", Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize.toString()).replace("%game%", Util.currentGameSelected[player]!!.name)]!!)
        }
    }

    private fun getItemObject(event: InventoryClickEvent): ItemStack? {
        if (event.currentItem!!.type == Material.RED_WOOL) {
            return event.inventory.getItem(event.slot - 5)
        } else if (event.currentItem!!.type == Material.GREEN_WOOL) {
            return event.inventory.getItem(event.slot - 4)
        }
        return null
    }
}
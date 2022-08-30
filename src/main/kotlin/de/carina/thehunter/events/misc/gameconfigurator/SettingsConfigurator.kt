/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/7/22, 3:19 AM by Carina The Latest changes made by Carina on 6/7/22, 3:19 AM All contents of "SettingsConfigurator.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.misc.gameconfigurator

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.Inventories
import de.carina.thehunter.util.builder.Items
import de.carina.thehunter.util.misc.Permissions
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
        private const val maxPlayersHigh = 50
        private const val maxPlayersLow = 2
        private const val teamSizeLow = 2
        private const val teamSizeHigh = 4
    }


    @EventHandler
    fun onInteractWithSettings(event: InventoryClickEvent) {
        if (!Util.currentGameSelected.containsKey(event.whoClicked as Player)) return
        if (!(event.whoClicked as Player).hasPermission(Permissions.SETTINGS_GUI))
            return
        if (PlainTextComponentSerializer.plainText().serialize(event.view.title()) != PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize("§d${Util.currentGameSelected[event.whoClicked as Player]!!.name}§6: Game Settings")))
            return
        event.isCancelled = true
        if (event.currentItem == null)
            return
        if (event.currentItem!!.itemMeta == null)
            return
        val item = getItemObject(event) ?: return
        val type = event.currentItem?.type != Material.RED_WOOL

        when (item.itemMeta) {
            Items.borderSize.itemMeta -> borderSize(type, event.whoClicked as Player)
            Items.saveButton.itemMeta -> {
                (event.whoClicked as Player).closeInventory()
                (event.whoClicked as Player).openInventory(Inventories.setupGameInventory(Util.currentGameSelected[event.whoClicked as Player]!!))

            }

            Items.minPlayers.itemMeta -> minPlayers(type, event.whoClicked as Player)
            Items.maxPlayers.itemMeta -> maxPlayers(type, event.whoClicked as Player)
            Items.teamsSize.itemMeta -> teamSize(type, event.whoClicked as Player)
            Items.teamsAllowedHead.itemMeta -> {
                teamsAllowed(type, event.whoClicked as Player)
                Inventories.itemEnchantmentSwitcher(event)
            }

            Items.teamsDamage.itemMeta -> {
                teamDamage(type, event.whoClicked as Player)
                Inventories.itemEnchantmentSwitcher(event)
            }
        }

    }


    private fun teamsAllowed(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]!!.teamsAllowed)
                return
            player.sendMessage(TheHunter.instance.messages.messagesMap["teams-allowed-enabled"]!!.replace("%game%", Util.currentGameSelected[player]!!.name))
            Util.currentGameSelected[player]!!.teamsAllowed = true
        } else {
            if (!Util.currentGameSelected[player]!!.teamsAllowed)
                return
            Util.currentGameSelected[player]!!.teamsAllowed = false
            player.sendMessage(TheHunter.instance.messages.messagesMap["teams-allowed-disabled"]!!.replace("%game%", Util.currentGameSelected[player]!!.name))

        }
    }

    private fun teamDamage(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]!!.teamDamage)
                return
            Util.currentGameSelected[player]!!.teamDamage = true
            player.sendMessage(TheHunter.instance.messages.messagesMap["team-damage-enabled"]!!.replace("%game%", Util.currentGameSelected[player]!!.name))
        } else {
            if (!Util.currentGameSelected[player]!!.teamDamage)
                return
            Util.currentGameSelected[player]!!.teamDamage = false
            player.sendMessage(TheHunter.instance.messages.messagesMap["team-damage-disabled"]!!.replace("%game%", Util.currentGameSelected[player]!!.name))

        }
    }


    private fun teamSize(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]!!.teamMaxSize + 1 > teamSizeHigh) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["teams-size-to-high"]!!.replace("%size%", teamSizeHigh.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))
                return
            }
            Util.currentGameSelected[player]!!.teamMaxSize++
            player.sendMessage(TheHunter.instance.messages.messagesMap["teams-size-increased"]!!.replace("%size%", Util.currentGameSelected[player]!!.teamMaxSize.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))

        } else {
            if (Util.currentGameSelected[player]!!.teamMaxSize - 1 < teamSizeLow) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["teams-size-to-low"]!!.replace("%size%", teamSizeLow.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))
                return
            }
            Util.currentGameSelected[player]!!.teamMaxSize--
            player.sendMessage(TheHunter.instance.messages.messagesMap["teams-size-reduced"]!!.replace("%size%", Util.currentGameSelected[player]!!.teamMaxSize.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))

        }
    }

    private fun minPlayers(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]!!.minPlayers + 1 > minPlayersHigh) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["min-players-to-high"]!!.replace("%players%", minPlayersHigh.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))
                return
            }
            Util.currentGameSelected[player]!!.minPlayers++
            player.sendMessage(TheHunter.instance.messages.messagesMap["min-players-increased"]!!.replace("%players%", Util.currentGameSelected[player]!!.minPlayers.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))

        } else {
            if (Util.currentGameSelected[player]!!.minPlayers - 1 < minPlayersLow) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["min-players-to-low"]!!.replace("%players%", minPlayersLow.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))
                return
            }
            Util.currentGameSelected[player]!!.minPlayers--
            player.sendMessage(TheHunter.instance.messages.messagesMap["min-players-reduced"]!!.replace("%players%", Util.currentGameSelected[player]!!.minPlayers.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))

        }
    }

    private fun maxPlayers(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]!!.maxPlayers + 1 > maxPlayersHigh) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["max-players-to-high"]!!.replace("%players%", maxPlayersHigh.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))
                return
            }
            Util.currentGameSelected[player]!!.maxPlayers++
            player.sendMessage(TheHunter.instance.messages.messagesMap["max-players-increased"]!!.replace("%players%", Util.currentGameSelected[player]!!.maxPlayers.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))

        } else {
            if (Util.currentGameSelected[player]!!.maxPlayers - 1 < maxPlayersLow) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["max-players-to-low"]!!.replace("%players%", maxPlayersLow.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))
                return
            }
            Util.currentGameSelected[player]!!.maxPlayers--
            player.sendMessage(TheHunter.instance.messages.messagesMap["max-players-reduced"]!!.replace("%players%", Util.currentGameSelected[player]!!.maxPlayers.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))

        }
    }

    private fun borderSize(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]!!.worldBoarderController.worldBoarderSize + 10 > WorldboarderController.toHigh) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["border-size-to-high"]!!.replace("%size%", WorldboarderController.toHigh.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))
                return
            }
            Util.currentGameSelected[player]!!.worldBoarderController.worldBoarderSize += 10
            player.sendMessage(TheHunter.instance.messages.messagesMap["border-size-plus"]!!.replace("%size%", Util.currentGameSelected[player]!!.worldBoarderController.worldBoarderSize.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))
        } else {
            if (Util.currentGameSelected[player]!!.worldBoarderController.worldBoarderSize - 10 < WorldboarderController.toLow) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["border-size-to-low"]!!.replace("%size%", WorldboarderController.toLow.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))
                return
            }
            Util.currentGameSelected[player]!!.worldBoarderController.worldBoarderSize -= 10
            player.sendMessage(TheHunter.instance.messages.messagesMap["border-size-minus"]!!.replace("%size%", Util.currentGameSelected[player]!!.worldBoarderController.worldBoarderSize.toString()).replace("%game%", Util.currentGameSelected[player]!!.name))
        }
    }

    private fun getItemObject(event: InventoryClickEvent): ItemStack? {
        return when (event.currentItem!!.type) {
            Material.RED_WOOL -> {
                event.inventory.getItem(event.slot - 4)
            }

            Material.GREEN_WOOL -> {
                event.inventory.getItem(event.slot - 3)
            }

            Material.RED_BED -> event.currentItem!!
            else -> null
        }
    }
}
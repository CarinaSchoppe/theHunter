/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/2/22, 2:08 PM All contents of "SettingsConfigurator.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.events.gameconfigurator

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.builder.Inventories
import de.pixels.thehunter.util.builder.Items
import de.pixels.thehunter.util.game.ingame.WorldboarderController
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import de.pixels.thehunter.util.misc.Util
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class SettingsConfigurator : Listener {

    companion object {
        private const val MIN_PLAYER_HEIGHT = 20
        private const val MIN_PLAYERS_LOW = 1
        private const val MAX_PLAYERS_HEIGHT = 50
        private const val MAX_PLAYERS_LOW = 2
        private const val TEAM_SIZE_LOW = 2
        private const val TEAM_SIZE = 4
    }


    @EventHandler
    fun onInteractWithSettings(event: InventoryClickEvent) {
        if (!Util.currentGameSelected.containsKey(event.whoClicked as Player) || !(event.whoClicked as Player).hasPermission(Permissions.SETTINGS_GUI)) return

        if (PlainTextComponentSerializer.plainText()
                .serialize(event.view.title()) != PlainTextComponentSerializer.plainText().serialize(
                LegacyComponentSerializer.legacySection()
                    .deserialize("§d${Util.currentGameSelected[event.whoClicked as Player]?.name}§6: Game Settings")
            )
        )
            return
        event.isCancelled = true
        if (event.currentItem == null || event.currentItem?.itemMeta == null)
            return

        val item = getItemObject(event) ?: return
        val type = event.currentItem?.type != Material.RED_WOOL
        (event.whoClicked as Player).playSound(event.whoClicked, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)

        when (item.itemMeta) {
            Items.borderSize.itemMeta -> borderSize(type, event.whoClicked as Player)
            Items.saveButton.itemMeta -> {
                (event.whoClicked as Player).closeInventory()
                (event.whoClicked as Player).openInventory(Inventories.setupGameInventory(Util.currentGameSelected[event.whoClicked as Player] ?: return))

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
            if (Util.currentGameSelected[player]?.teamsAllowed == true)
                return
            TheHunter.instance.messages.messagesMap["teams-allowed-enabled"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        Util.currentGameSelected[player]?.name ?: ""
                    )
                )
            }
            Util.currentGameSelected[player]?.teamsAllowed = true
        } else {
            if (Util.currentGameSelected[player]?.teamsAllowed == false)
                return
            Util.currentGameSelected[player]?.teamsAllowed = false
            TheHunter.instance.messages.messagesMap["teams-allowed-disabled"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        Util.currentGameSelected[player]?.name ?: ""
                    )
                )
            }

        }
    }

    private fun teamDamage(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]?.teamDamage == true)
                return
            Util.currentGameSelected[player]?.teamDamage = true
            TheHunter.instance.messages.messagesMap["team-damage-enabled"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        Util.currentGameSelected[player]?.name ?: ""
                    )
                )
            }
        } else {
            if (Util.currentGameSelected[player]?.teamDamage == false)
                return
            Util.currentGameSelected[player]?.teamDamage = false
            TheHunter.instance.messages.messagesMap["team-damage-disabled"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        Util.currentGameSelected[player]?.name ?: ""
                    )
                )
            }

        }
    }


    private fun teamSize(type: Boolean, player: Player) {
        if (type) {
            if ((Util.currentGameSelected[player]?.teamMaxSize ?: return) + 1 > TEAM_SIZE) {
                TheHunter.instance.messages.messagesMap["teams-size-to-high"]?.replace(
                    ConstantStrings.SIZE_PERCENT,
                    TEAM_SIZE.toString()
                )?.let {
                    player.sendMessage(
                        it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                    )
                }
                return
            }
            Util.currentGameSelected[player]?.teamMaxSize = Util.currentGameSelected[player]?.teamMaxSize?.plus(1) ?: return
            TheHunter.instance.messages.messagesMap["teams-size-increased"]?.replace(
                ConstantStrings.SIZE_PERCENT,
                Util.currentGameSelected[player]?.teamMaxSize?.toString() ?: ""
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        } else {
            if ((Util.currentGameSelected[player]?.teamMaxSize ?: return) - 1 < TEAM_SIZE_LOW) {
                TheHunter.instance.messages.messagesMap["teams-size-to-low"]?.replace(
                    ConstantStrings.SIZE_PERCENT,
                    TEAM_SIZE_LOW.toString()
                )?.let {
                    player.sendMessage(
                        it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                    )
                }
                return
            }
            Util.currentGameSelected[player]?.teamMaxSize = Util.currentGameSelected[player]?.teamMaxSize?.minus(1) ?: return
            TheHunter.instance.messages.messagesMap["teams-size-reduced"]?.replace(
                ConstantStrings.SIZE_PERCENT,
                Util.currentGameSelected[player]?.teamMaxSize.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        }
    }

    private fun minPlayers(type: Boolean, player: Player) {
        if (type) {
            if (MIN_PLAYER_HEIGHT < (Util.currentGameSelected[player]?.minPlayers?.plus(1) ?: return)) {
                TheHunter.instance.messages.messagesMap["min-players-to-high"]?.replace(
                    ConstantStrings.PLAYERS_PERCENT,
                    MIN_PLAYER_HEIGHT.toString()
                )?.let {
                    player.sendMessage(
                        it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                    )
                }
                return
            }
            Util.currentGameSelected[player]?.minPlayers = Util.currentGameSelected[player]?.minPlayers?.plus(1) ?: return
            TheHunter.instance.messages.messagesMap["min-players-increased"]?.replace(
                ConstantStrings.PLAYERS_PERCENT,
                Util.currentGameSelected[player]?.minPlayers.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        } else {
            if (MIN_PLAYERS_LOW > (Util.currentGameSelected[player]?.minPlayers?.minus(1) ?: return)) {
                TheHunter.instance.messages.messagesMap["min-players-to-low"]?.replace(
                    ConstantStrings.PLAYERS_PERCENT,
                    MIN_PLAYERS_LOW.toString()
                )?.let {
                    player.sendMessage(
                        it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                    )
                }
                return
            }
            Util.currentGameSelected[player]?.minPlayers = Util.currentGameSelected[player]?.minPlayers?.minus(1) ?: return
            TheHunter.instance.messages.messagesMap["min-players-reduced"]?.replace(
                ConstantStrings.PLAYER_PERCENT,
                Util.currentGameSelected[player]?.minPlayers.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        }
    }

    private fun maxPlayers(type: Boolean, player: Player) {
        if (type) {
            if (MAX_PLAYERS_HEIGHT < (Util.currentGameSelected[player]?.maxPlayers?.plus(1) ?: return)) {
                TheHunter.instance.messages.messagesMap["max-players-to-high"]?.replace(
                    ConstantStrings.PLAYERS_PERCENT,
                    MAX_PLAYERS_HEIGHT.toString()
                )?.let {
                    player.sendMessage(
                        it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                    )
                }
                return
            }
            Util.currentGameSelected[player]?.maxPlayers = Util.currentGameSelected[player]?.maxPlayers?.plus(1) ?: return
            TheHunter.instance.messages.messagesMap["max-players-increased"]?.replace(
                ConstantStrings.PLAYERS_PERCENT,
                Util.currentGameSelected[player]?.maxPlayers.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        } else {
            if ((Util.currentGameSelected[player]?.maxPlayers ?: return) - 1 < MAX_PLAYERS_LOW) {
                TheHunter.instance.messages.messagesMap["max-players-to-low"]?.replace(
                    ConstantStrings.PLAYERS_PERCENT,
                    MAX_PLAYERS_LOW.toString()
                )?.let {
                    player.sendMessage(
                        it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                    )
                }
                return
            }
            Util.currentGameSelected[player]?.maxPlayers = Util.currentGameSelected[player]?.maxPlayers?.minus(1) ?: return
            TheHunter.instance.messages.messagesMap["max-players-reduced"]?.replace(
                ConstantStrings.PLAYERS_PERCENT,
                Util.currentGameSelected[player]?.maxPlayers.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        }
    }

    private fun borderSize(type: Boolean, player: Player) {
        if (type) {
            if ((Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize ?: return) + 10 > WorldboarderController.TO_HIGH) {
                TheHunter.instance.messages.messagesMap["border-size-to-high"]?.replace(
                    ConstantStrings.SIZE_PERCENT,
                    WorldboarderController.TO_HIGH.toString()
                )?.let {
                    player.sendMessage(
                        it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                    )
                }
                return
            }
            Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize = Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize?.plus(10) ?: return
            TheHunter.instance.messages.messagesMap["border-size-plus"]?.replace(
                ConstantStrings.SIZE_PERCENT,
                Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }
        } else {
            if (WorldboarderController.TO_LOW > (Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize?.minus(10) ?: return)) {
                TheHunter.instance.messages.messagesMap["border-size-to-low"]?.replace(
                    ConstantStrings.SIZE_PERCENT,
                    WorldboarderController.TO_LOW.toString()
                )?.let {
                    player.sendMessage(
                        it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                    )
                }
                return
            }
            Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize = Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize?.minus(10) ?: return
            TheHunter.instance.messages.messagesMap["border-size-minus"]?.replace(
                ConstantStrings.SIZE_PERCENT,
                Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }
        }
    }

    private fun getItemObject(event: InventoryClickEvent): ItemStack? {
        return when (event.currentItem?.type) {
            Material.RED_WOOL -> {
                event.inventory.getItem(event.slot - 4)
            }

            Material.GREEN_WOOL -> {
                event.inventory.getItem(event.slot - 3)
            }

            Material.RED_BED -> event.currentItem
            else -> null
        }
    }
}

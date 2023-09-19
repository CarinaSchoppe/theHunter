/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/2/22, 2:08 PM All contents of "SettingsConfigurator.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.events.gameconfigurator

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.builder.Inventories
import de.pixels.thehunter.util.builder.Items
import de.pixels.thehunter.util.misc.WorldboarderController
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
        /**
         * Minimum height of a player.
         *
         * This constant represents the minimum height that a player can have. The value is expressed in units which could be
         * inches, centimeters, or any other unit depending on how the height is being measured.
         */
        private const val MIN_PLAYER_HEIGHT = 20

        /**
         * Minimum number of players required for low priority.
         */
        private const val MIN_PLAYERS_LOW = 1

        /**
         * Maximum height allowed for players.
         */
        private const val MAX_PLAYERS_HEIGHT = 50

        /**
         * The maximum number of low-level players allowed.
         */
        private const val MAX_PLAYERS_LOW = 2

        /**
         * Represents the lower limit for team size.
         *
         * The value of this constant is 2, indicating that a team should have at least 2 members in order to meet the lower limit
         * for its size.
         */
        private const val TEAM_SIZE_LOW = 2

        /**
         * Represents the size of a team.
         *
         * The value of this constant is set to 4.
         */
        private const val TEAM_SIZE = 4
    }


    /**
     * Handles interactions with the settings GUI.
     *
     * @param event The inventory click event.
     */
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


    /**
     * Sets the teams allowed property for a specific player's current game.
     *
     * If [type] is true, enables teams allowed. If [type] is false, disables teams allowed.
     *
     * @param type A boolean value indicating whether to enable or disable teams allowed.
     * @param player The player object to set the teams allowed property for.
     */
    private fun teamsAllowed(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]?.teamsAllowed == true)
                return
            TheHunter.instance.messagesFile.messagesMap["teams-allowed-enabled"]?.let {
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
            TheHunter.instance.messagesFile.messagesMap["teams-allowed-disabled"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        Util.currentGameSelected[player]?.name ?: ""
                    )
                )
            }

        }
    }

    /**
     * Toggles the team damage setting for a player in the current game.
     *
     * @param type the type of team damage setting to apply:
     *   - `true` to enable team damage
     *   - `false` to disable team damage
     * @param player the player for whom to toggle the team damage setting
     */
    private fun teamDamage(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]?.teamDamage == true)
                return
            Util.currentGameSelected[player]?.teamDamage = true
            TheHunter.instance.messagesFile.messagesMap["team-damage-enabled"]?.let {
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
            TheHunter.instance.messagesFile.messagesMap["team-damage-disabled"]?.let {
                player.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        Util.currentGameSelected[player]?.name ?: ""
                    )
                )
            }

        }
    }


    /**
     * Updates the team size based on the given type for the specified player.
     *
     * @param type A Boolean representing the type of update. True indicates an increase in team size, while False indicates a decrease.
     * @param player The Player object for whom the team size needs to be updated.
     */
    private fun teamSize(type: Boolean, player: Player) {
        if (type) {
            if ((Util.currentGameSelected[player]?.teamMaxSize ?: return) + 1 > TEAM_SIZE) {
                TheHunter.instance.messagesFile.messagesMap["teams-size-to-high"]?.replace(
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
            TheHunter.instance.messagesFile.messagesMap["teams-size-increased"]?.replace(
                ConstantStrings.SIZE_PERCENT,
                Util.currentGameSelected[player]?.teamMaxSize?.toString() ?: ""
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        } else {
            if ((Util.currentGameSelected[player]?.teamMaxSize ?: return) - 1 < TEAM_SIZE_LOW) {
                TheHunter.instance.messagesFile.messagesMap["teams-size-to-low"]?.replace(
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
            TheHunter.instance.messagesFile.messagesMap["teams-size-reduced"]?.replace(
                ConstantStrings.SIZE_PERCENT,
                Util.currentGameSelected[player]?.teamMaxSize.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        }
    }

    /**
     * Sets the minimum number of players for a game.
     *
     * @param type - A Boolean value indicating whether to increase or decrease the minimum number of players.
     *              - `true` to increase the minimum number of players.
     *              - `false` to decrease the minimum number of players.
     * @param player - The player object representing the player changing the minimum number of players.
     */
    private fun minPlayers(type: Boolean, player: Player) {
        if (type) {
            if (MIN_PLAYER_HEIGHT < (Util.currentGameSelected[player]?.minPlayers?.plus(1) ?: return)) {
                TheHunter.instance.messagesFile.messagesMap["min-players-to-high"]?.replace(
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
            TheHunter.instance.messagesFile.messagesMap["min-players-increased"]?.replace(
                ConstantStrings.PLAYERS_PERCENT,
                Util.currentGameSelected[player]?.minPlayers.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        } else {
            if (MIN_PLAYERS_LOW > (Util.currentGameSelected[player]?.minPlayers?.minus(1) ?: return)) {
                TheHunter.instance.messagesFile.messagesMap["min-players-to-low"]?.replace(
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
            TheHunter.instance.messagesFile.messagesMap["min-players-reduced"]?.replace(
                ConstantStrings.PLAYER_PERCENT,
                Util.currentGameSelected[player]?.minPlayers.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        }
    }

    /**
     * Updates the maximum number of players for a game.
     *
     * @param type Boolean value indicating whether to increase or decrease the maximum number of players.
     * @param player The player for whom to update the maximum number of players.
     */
    private fun maxPlayers(type: Boolean, player: Player) {
        if (type) {
            if (MAX_PLAYERS_HEIGHT < (Util.currentGameSelected[player]?.maxPlayers?.plus(1) ?: return)) {
                TheHunter.instance.messagesFile.messagesMap["max-players-to-high"]?.replace(
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
            TheHunter.instance.messagesFile.messagesMap["max-players-increased"]?.replace(
                ConstantStrings.PLAYERS_PERCENT,
                Util.currentGameSelected[player]?.maxPlayers.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        } else {
            if ((Util.currentGameSelected[player]?.maxPlayers ?: return) - 1 < MAX_PLAYERS_LOW) {
                TheHunter.instance.messagesFile.messagesMap["max-players-to-low"]?.replace(
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
            TheHunter.instance.messagesFile.messagesMap["max-players-reduced"]?.replace(
                ConstantStrings.PLAYERS_PERCENT,
                Util.currentGameSelected[player]?.maxPlayers.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }

        }
    }

    /**
     * Updates the border size of the world boarder for the specified player in the current game.
     *
     * @param type Indicating whether to increase or decrease the border size. True for increase, false for decrease.
     * @param player The player whose world boarder will be updated.
     */
    private fun borderSize(type: Boolean, player: Player) {
        if (type) {
            if ((Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize ?: return) + 10 > WorldboarderController.TO_HIGH) {
                TheHunter.instance.messagesFile.messagesMap["border-size-to-high"]?.replace(
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
            TheHunter.instance.messagesFile.messagesMap["border-size-plus"]?.replace(
                ConstantStrings.SIZE_PERCENT,
                Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }
        } else {
            if (WorldboarderController.TO_LOW > (Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize?.minus(10) ?: return)) {
                TheHunter.instance.messagesFile.messagesMap["border-size-to-low"]?.replace(
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
            TheHunter.instance.messagesFile.messagesMap["border-size-minus"]?.replace(
                ConstantStrings.SIZE_PERCENT,
                Util.currentGameSelected[player]?.worldBoarderController?.worldBoarderSize.toString()
            )?.let {
                player.sendMessage(
                    it.replace(ConstantStrings.GAME_PERCENT, Util.currentGameSelected[player]?.name ?: "")
                )
            }
        }
    }

    /**
     * Retrieves the ItemStack object based on the provided InventoryClickEvent.
     *
     * @param event The InventoryClickEvent triggered by the player.
     * @return The ItemStack object associated with the event, or null if no matching object is found.
     */
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

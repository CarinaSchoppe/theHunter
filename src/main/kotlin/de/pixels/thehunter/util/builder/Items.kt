/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "Items.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.builder

import de.pixels.thehunter.TheHunter
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

object Items {
    /**
     * The gamblerPerkItem represents an item that grants the gambler perk to the game.
     *
     * It is a valuable item made of gold ingot with a special display name and lore.
     * When clicked, it adds the gambler perk to the game.
     */
    val gamblerPerkItem = ItemBuilder(Material.GOLD_INGOT).addDisplayName("§6Gambler")
        .addLore("§7Click to add the gambler perk to the game").build()

    /**
     * Represents the settings for the "Green Wool" option.
     *
     * @property settingsWoolGreen The configuration for the "Green Wool" option.
     */
    val settingsWoolGreen = ItemBuilder(Material.GREEN_WOOL).addLore("§7Click here to activate the setting")

    /**
     * Creates a [perkMenuItem] variable which represents a menu item for the "Perks" menu.
     * The [perkMenuItem] is a book item with the display name "§6Perks" and the lore "§7Click to open the perks menu".
     *
     * @property perkMenuItem The menu item representing the "Perks" menu.
     *
     * @see ItemBuilder
     * @see Material
     */
    val perkMenuItem = ItemBuilder(Material.BOOK).addDisplayName("§6Perks").addLore("§7Click to open the perks menu").build()
    /**
     * WoolRed Settings
     *
     * This variable represents the settings for the red wool.
     *
     * @property settingsWoolRed
     * @param Material.RED_WOOL The material of the red wool.
     * @param ItemBuilder(Material.RED_WOOL) The builder used to create an instance of the red wool item.
     * @param addLore("§7Click here to deactivate the setting") Adds a lore to the red wool item, indicating its deactivation status.
     * @return The red wool item with the specified settings.
     */
    val settingsWoolRed = ItemBuilder(Material.RED_WOOL).addLore("§7Click here to deactivate the setting")

    /**
     * Represents the settings head item.
     *
     * This variable is an instance of the ItemBuilder class, which is used to create Minecraft items.
     * It is initialized with the material JUNGLE_FENCE and modified to have a lore and display name.
     * The lore is set to "§7Click to open the settings menu" and the display name is set to "§6Settings Menu".
     */
    val settingsHead = ItemBuilder(Material.JUNGLE_FENCE).addLore("§7Click to open the settings menu")
        .addDisplayName("§6Settings Menu").build()

    /**
     * Represents the "Add Lobby" button item.
     *
     * The "Add Lobby" button is used to add the spawn location of the lobby to the game.
     * It is visually represented by a diamond material with a custom display name and lore.
     */
    val addLobbyButton = ItemBuilder(Material.DIAMOND).addDisplayName("§6Lobby Location")
        .addLore("§7Click to add the spawn of the lobby to the game").build()

    /**
     * Represents the kangaroo perk item.
     */
    val kangarooPerkItem = ItemBuilder(Material.RABBIT_FOOT).addDisplayName("§6Kangaroo")
        .addLore("§7Click to add the kangaroo perk to the game").build()

    /**
     * Represents a bat perk item.
     *
     * This item is used to add the bat perk to the game when clicked.
     * The item is a spawn egg with a display name of "§6Bat" and a lore of "§7Click to add the bat perk to the game".
     */
    val batPerkItem = ItemBuilder(Material.BAT_SPAWN_EGG).addDisplayName("§6Bat")
        .addLore("§7Click to add the bat perk to the game").build()

    /**
     * Represents a perk item for the bear player class.
     *
     * @property bearPerkItem The bear perk item.
     */
    val bearPerkItem = ItemBuilder(Material.GOAT_HORN).addDisplayName("§6Bear")
        .addLore("§7Click to add the bear perk to the game").build()

    /**
     * Represents a sonic perk item.
     *
     * This item can be added to the game to provide players with the sonic perk.
     * When used, it allows players to move at increased speed for a certain duration.
     */
    val sonicPerkItem = ItemBuilder(Material.SUGAR).addDisplayName("§6Sonic")
        .addLore("§7Click to add the sonic perk to the game").build()

    /**
     * Represents a backpacker perk item.
     *
     * This item is used to add the backpacker perk to the game.
     *
     * @property backpackerPerkItem The ItemStack representing the backpacker perk item.
     * @constructor Creates a backpacker perk item.
     * @param material The material of the item.
     */
    val backpackerPerkItem = ItemBuilder(Material.CHEST).addDisplayName("§6Backpacker")
        .addLore("§7Click to add the backpacker perk to the game").build()

    /**
     * A variable that represents the bloodhound perk item.
     *
     * This item is used to add the bloodhound perk to the game when clicked.
     *
     * Usage example:
     * ```
     * val bloodhoundPerkItem = ItemBuilder(Material.BONE).addDisplayName("§6Bloodhound")
     *     .addLore("§7Click to add the bloodhound perk to the game").build()
     * ```
     *
     * @see ItemBuilder
     * @see Material
     */
    val bloodhoundPerkItem = ItemBuilder(Material.BONE).addDisplayName("§6Bloodhound")
        .addLore("§7Click to add the bloodhound perk to the game").build()

    /**
     * Represents an item used for the Bomberman perk in the game.
     *
     * @property bombermanPerkItem The Bomberman perk item.
     *
     * @constructor Creates a BombermanPerkItem object.
     */
    val bombermanPerkItem = ItemBuilder(Material.TNT).addDisplayName("§6Bomberman")
        .addLore("§7Click to add the bomberman perk to the game").build()

    /**
     * Represents the ninja perk item.
     *
     * @property ninjaPerkItem The ninja perk item instance.
     */
    val ninjaPerkItem = ItemBuilder(Material.NETHERITE_SWORD).addDisplayName("§6Ninja")
        .addLore("§7Click to add the ninja perk to the game").build()

    /**
     * Represents a pig perk item.
     *
     * This class provides a builder pattern to create a pig perk item with specific properties.
     * The pig perk item is an item that can be used to add the "pig perk" to the game.
     *
     * Use the builder methods to customize the pig perk item.
     *
     * @param material The material type of the pig perk item.
     * @param displayName The display name of the pig perk item.
     * @param lore The lore description of the pig perk item.
     *
     * @constructor Creates a new instance of the pig perk item.
     */
    val pigPerkItem = ItemBuilder(Material.PORKCHOP).addDisplayName("§6Pig")
        .addLore("§7Click to add the pig perk to the game").build()

    /**
     * The piratePerkItem variable represents an item with the pirate perk.
     * It is built using the ItemBuilder class from the org.bukkit.inventory package.
     * The item is of type Material.PAPER and has a display name of "§6Pirate".
     * It also has a lore message of "§7Click to add the pirate perk to the game".
     *
     * @see ItemBuilder
     */
    val piratePerkItem = ItemBuilder(Material.PAPER).addDisplayName("§6Pirate")
        .addLore("§7Click to add the pirate perk to the game").build()

    /**
     * Represents the angel perk item.
     *
     * This item is used to add the angel perk to the game.
     * When clicked, it will activate the angel perk.
     */
    val angelPerkItem = ItemBuilder(Material.FEATHER).addDisplayName("§6Angel")
        .addLore("§7Click to add the angel perk to the game").build()


    /**
     * Represents the button used to add the spectator location to the game.
     */
    val addSpectatorButton =
        ItemBuilder(Material.ACACIA_BOAT).addLore("§7Click to add the spectator location to the game")
            .addDisplayName("§6Spectator Location").build()

    /**
     * Represents a back button item.
     *
     * This item is used to add the back location to the game when clicked.
     * It is created using the `ItemBuilder` class with the `BRICK` material.
     * The display name is set to "§6Back Location" and the lore is set to "§7Click to add the back location to the game".
     *
     * @property addBackButton The back button item.
     */
    val addBackButton = ItemBuilder(Material.BRICK).addLore("§7Click to add the back location to the game")
        .addDisplayName("§6Back Location").build()

    /**
     * Represents a finish button.
     *
     * @property finishButton The finish button item.
     *
     * @constructor Creates a finish button with the specified material, lore, and display name.
     * @param material The material of the finish button.
     * @param lore The lore of the finish button.
     * @param displayName The display name of the finish button.
     */
    val finishButton = ItemBuilder(Material.BARRIER).addLore("§7Click to add the finish location to the game")
        .addDisplayName("§6Finish Location").build()

    /**
     * Represents a spawn button item for adding spawn locations to the game.
     *
     * @property addSpawnButton The spawn button item.
     */
    val addSpawnButton = ItemBuilder(Material.DIAMOND_SWORD).addLore("§7Click to add the spawn location to the game")
        .addDisplayName("§6Spawn Location").build()

    /**
     * Represents an "Add End" button.
     *
     * This button is used to add the end location to the game.
     *
     * @property addEndButton The button object.
     *
     * @constructor Creates an "Add End" button with the specified properties.
     * @param material The material of the button.
     *
     * @throws IllegalArgumentException if material is null.
     *
     * @since 1.0.0
     */
    val addEndButton = ItemBuilder(Material.REDSTONE).addLore("§7Click to add the end location to the game")
        .addDisplayName("§6End Location").build()

    /**
     * Represents a button used to add the arena center to the game.
     *
     * @property addArenaCenterButton The button item with the specified display name and lore.
     */
    val addArenaCenterButton = ItemBuilder(Material.IRON_DOOR).addLore("§7Click to add the arena center to the game")
        .addDisplayName("§6Arena Center").build()

    /**
     * A variable representing the head item for teams allowed information.
     *
     * @property teamsAllowedHead The head item for teams allowed information.
     */
    val teamsAllowedHead =
        ItemBuilder(Material.PLAYER_HEAD).addDisplayName("§6Teams Allowed").addLore("§7Teams allowed or not").build()

    /**
     * Represents the team size settings item.
     *
     * This item allows for adjusting the team size by adding or reducing it.
     *
     * Usage:
     * - Left Click: Increase the team size
     * - Right Click: Decrease the team size
     */
    val teamsSize =
        ItemBuilder(Material.DIAMOND_SWORD).addDisplayName("§6Team Size").addLore("§7Teamsize add or reduce").build()

    /**
     * Represents a redstone material item used to toggle team damage.
     *
     * Example usage:
     * val teamsDamage = ItemBuilder(Material.REDSTONE)
     *      .addDisplayName("§6Team Damage")
     *      .addLore("§7TeamDamage allowed or not")
     *      .build()
     */
    val teamsDamage =
        ItemBuilder(Material.REDSTONE).addDisplayName("§6Team Damage").addLore("§7TeamDamage allowed or not").build()

    /**
     * Represents the minimum players variable.
     *
     * This variable contains an instance of an ItemBuilder object representing a diamond chestplate.
     * The chestplate has a display name of "§6Min Players" and a lore message of "§7Min Players amount".
     *
     * @see ItemBuilder
     *
     * @property minPlayers The minimum players variable.
     */
    val minPlayers =
        ItemBuilder(Material.DIAMOND_CHESTPLATE).addDisplayName("§6Min Players").addLore("§7Min Players amount").build()

    /**
     * Represents the maximum players item.
     * This item is used to display the maximum players amount in a graphical user interface.
     *
     * @property maxPlayers The maximum players item.
     *
     * @constructor Creates a new maximum players item.
     * @param maxPlayers The maximum players item instance.
     */
    val maxPlayers =
        ItemBuilder(Material.NETHERITE_INGOT).addDisplayName("§6Max Players").addLore("§7Max Players amount").build()

    /**
     * Represents the border size for an item.
     *
     * @property borderSize The item with the border size.
     */
    val borderSize = ItemBuilder(Material.BARRIER).addDisplayName("§6Border Size").addLore("§7Border Size").build()

    /**
     * Represents the leave item used in the game.
     *
     * @property leaveItem The built leave item.
     */
    val leaveItem = ItemBuilder(Material.IRON_DOOR).addDisplayName(TheHunter.prefix + "§6Leave")
        .addLore("§7Click to leave the game").addEnchantment(Enchantment.DURABILITY, 1).build()

    /**
     * Represents the save button for settings.
     *
     * @property saveButton The item representing the save button.
     */
    val saveButton = ItemBuilder(Material.RED_BED).addDisplayName(TheHunter.prefix + "§6Save Settings")
        .addLore("§7Click to save the settings").build()

    /**
     * Represents a name tag item used in the game.
     *
     * This item is used to enter the name of a game and click the corresponding item.
     * It has a display name and lore, along with an enchantment.
     *
     * @property nameTag The built name tag item.
     * @constructor Creates a new name tag item with the specified display name and lore.
     * @param displayName The display name of the name tag item.
     */
    val nameTag = ItemBuilder(Material.NAME_TAG).addDisplayName(TheHunter.prefix + "§6Game Name")
        .addLore("§7Enter the name of the game and click the corresponding item")
        .addEnchantment(Enchantment.DURABILITY, 1).build()

}

/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "Items.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.builder

import de.carina.thehunter.TheHunter
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

object Items {
    val settingsGreen = ItemBuilder(Material.GREEN_WOOL).addLore("§7Click here to activate the setting")
    val settingsRed = ItemBuilder(Material.RED_WOOL).addLore("§7Click here to activate thedeactivate the setting")
    val settingsHead = ItemBuilder(Material.JUNGLE_FENCE).addLore("§7Click to open the settings menu").addDisplayName("§Settings Menu").build()
    val addLobbyButton = ItemBuilder(Material.DIAMOND).addDisplayName("§6Lobby Location").addLore("§7Click to add the spawn of the lobby to the game").build()
    val addSpectatorButton = ItemBuilder(Material.ACACIA_BOAT).addLore("§7Click to add the spectator location to the game").addDisplayName("§6Spectator Location").build()
    val addBackButton = ItemBuilder(Material.BRICK).addLore("§7Click to add the back location to the game").addDisplayName("§6Back Location").build()
    val finishButton = ItemBuilder(Material.BARRIER).addLore("§7Click to add the finish location to the game").addDisplayName("§6Finish Location").build()
    val addSpawnButton = ItemBuilder(Material.DIAMOND_SWORD).addLore("§7Click to add the spawn location to the game").addDisplayName("§6Spawn Location").build()
    val addEndButton = ItemBuilder(Material.REDSTONE).addLore("§7Click to add the end location to the game").addDisplayName("§6End Location").build()
    val addArenaCenterButton = ItemBuilder(Material.IRON_DOOR).addLore("§7Click to add the arena center to the game").addDisplayName("§6Arena Center").build()
    val teamsAllowedHead = ItemBuilder(Material.PLAYER_HEAD).addDisplayName("§6Teams Allowed").addLore("§7Teams allowed or not").build()
    val teamsSize = ItemBuilder(Material.DIAMOND_SWORD).addDisplayName("§6Team Size").addLore("§7Teamsize add or reduce").build()
    val teamsDamage = ItemBuilder(Material.REDSTONE).addDisplayName("§6Team Damage").addLore("§7TeamDamage allowed or not").build()
    val minPlayers = ItemBuilder(Material.DIAMOND_CHESTPLATE).addDisplayName("§6Min Players").addLore("§7Min Players amount").build()
    val maxPlayers = ItemBuilder(Material.NETHERITE_INGOT).addDisplayName("§6Max Players").addLore("§7Max Players amount").build()
    val borderSize = ItemBuilder(Material.BARRIER).addDisplayName("§6Border Size").addLore("§7Border Size").build()
    val leaveItem = ItemBuilder(Material.IRON_DOOR).addDisplayName(TheHunter.prefix + "§6Leave").addLore("§7Click to leave the game").addEnchantment(Enchantment.DURABILITY, 1).build()
    val saveButton = ItemBuilder(Material.RED_BED).addDisplayName(TheHunter.prefix + "§6Save Settings").addLore("§7Click to save the settings").addEnchantment(Enchantment.DURABILITY, 1).build()

}
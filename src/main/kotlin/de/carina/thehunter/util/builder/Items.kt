package de.carina.thehunter.util.builder

import org.bukkit.Material

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
}
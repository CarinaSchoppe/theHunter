/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 11:07 PM All contents of "TheHunter.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter

import de.pixels.thehunter.commands.BaseCommand
import de.pixels.thehunter.commands.ingame.StartCommand
import de.pixels.thehunter.events.gameconfigurator.GameConfigurator
import de.pixels.thehunter.events.gameconfigurator.SettingsConfigurator
import de.pixels.thehunter.events.ingame.*
import de.pixels.thehunter.events.misc.PlayerDisconnectHandler
import de.pixels.thehunter.events.misc.ServerJoinHandler
import de.pixels.thehunter.events.player.*
import de.pixels.thehunter.guns.GunHandler
import de.pixels.thehunter.items.special.*
import de.pixels.thehunter.util.files.BaseFile
import de.pixels.thehunter.util.files.Messages
import de.pixels.thehunter.util.files.Settings
import de.pixels.thehunter.util.game.ingame.GameSigns
import de.pixels.thehunter.util.game.ingame.GamesInventoryList
import de.pixels.thehunter.util.game.ingame.PlayerTeamHead
import de.pixels.thehunter.util.game.ingame.achievements.AchievementManager
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.game.management.StatsSystem
import de.pixels.thehunter.util.misc.Autoupdater
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * This class represents the main plugin class for The Hunter game.
 *
 * @property settings The settings object used for configuration.
 * @property messages The messages object used for storing messages.
 * @property statsSystem The stats system object for managing player statistics.
 * @property version The version of the plugin.
 */
class TheHunter : JavaPlugin() {

    /*
    TODO: Villager Clickable somehow
    TODO: Perks
    TODO: Achievements
    TODO: Premium join
    TODO: grenade makes no damage
    TODO: Legacy code umändern 
    TODO: Testing!!!
    TODO: Empty gamestate array 
    TODO: Inventory Clicks.
    TOOD: LegacyBugs    
     */


    /**
     * The companion object for the [TheHunter] class.
     *
     * This object provides access to shared properties that are specific to the [TheHunter] class.
     */
    companion object {
        var prefix = "§8[§6TheHunter§8] §f"
        lateinit var instance: TheHunter
    }

    /**
     * Represents the settings for the application.
     *
     * This class provides access to various configuration settings that control the behavior of the application.
     * The settings can be customized by the user through the application's user interface or programmatically.
     * The values of the settings are initialized lazily using the "lateinit" modifier, meaning they will be
     * initialized when first accessed.
     *
     * @property settings An instance of the Settings class that contains the configuration settings for the application.
     */
    lateinit var settings: Settings

    /**
     * Contains a collection of messages.
     *
     * @property messages The collection of messages.
     */
    lateinit var messages: Messages

    /**
     * Represents a system for managing and tracking statistics.
     *
     * This class provides functionality for initializing and managing a statistics system
     * to track various metrics and data.
     *
     * @property statsSystem Represents the object of the StatsSystem class.
     */
    lateinit var statsSystem: StatsSystem

    /**
     * Represents the version of the software.
     *
     * @property version The version string in the format "x.x.x.x".
     */
    val version = "1.2.6.1"

    /**
     * Called when the plugin is enabled.
     */
    override fun onEnable() {
        instance = this
        settings = Settings("config.yml").addData()
        messages = Messages("messages.yml").addData()
        statsSystem = StatsSystem()
        StatsSystem.loadStatsPlayersFromFile()
        initialize(Bukkit.getPluginManager())
        prefix = settings.getColorCodedString("prefix")
        messages.sendMessageToConsole("start-up-message-successfully")
    }

    /**
     * Saves all player stats to files, resets all games,
     * and sends a shutdown success message to the console.
     */
    override fun onDisable() {
        StatsSystem.saveAllStatsPlayerToFiles()
        resetAllGames()
        messages.sendMessageToConsole("shutdown-message-successfully")
    }

    /**
     * Resets all the games managed by the GamesHandler.
     * This method iterates over all the games and calls the `resetMap()` method
     * of each game's `mapResetter` object to reset the game map.
     */
    private fun resetAllGames() {
        for (game in GamesHandler.games) {
            game.mapResetter.resetMap()
        }
    }

    /**
     * Initializes the plugin by registering commands, events, and loading games from folders.
     *
     * @param pluginManager The plugin manager used to register events.
     */
    private fun initialize(pluginManager: PluginManager) {

        //Commands:
        getCommand("theHunter")?.setExecutor(BaseCommand())
        getCommand("start")?.setExecutor(StartCommand())

        //Events:
        pluginManager.registerEvents(EggBomb(), this)
        pluginManager.registerEvents(FallingBlocks(), this)
        pluginManager.registerEvents(ServerJoinHandler(), this)
        pluginManager.registerEvents(Knife(), this)
        pluginManager.registerEvents(EyeSpy(), this)
        pluginManager.registerEvents(Healer(), this)
        pluginManager.registerEvents(Swapper(), this)
        pluginManager.registerEvents(Tracker(), this)
        pluginManager.registerEvents(Food(), this)
        pluginManager.registerEvents(EnergyDrink(), this)
        pluginManager.registerEvents(JumpStick(), this)
        pluginManager.registerEvents(LootChestHandler(), this)
        pluginManager.registerEvents(GunHandler(), this)
        pluginManager.registerEvents(TeamDamage(), this)
        pluginManager.registerEvents(MapModification(), this)
        pluginManager.registerEvents(WeaponInteraction(), this)
        pluginManager.registerEvents(PlayerChat(), this)
        pluginManager.registerEvents(GameSigns(), this)
        pluginManager.registerEvents(PlayerDeath(), this)
        pluginManager.registerEvents(PlayerDisconnectHandler(), this)
        pluginManager.registerEvents(GamesInventoryList(), this)
        pluginManager.registerEvents(DeathChestHandler(), this)
        pluginManager.registerEvents(LobbyInteraction(), this)
        pluginManager.registerEvents(PlayerTeamHead(), this)
        pluginManager.registerEvents(SettingsConfigurator(), this)
        pluginManager.registerEvents(GameConfigurator(), this)
        pluginManager.registerEvents(PlayerHotbarHover(), this)
        pluginManager.registerEvents(PlayerRegenerateIngame(), this)
        AchievementManager.registerAchievements(this)

        loadGamesFromFolders()

        Autoupdater().checkUpdate("41971")
    }


    /**
     * Loads games from the "arenas" folder.
     */
    private fun loadGamesFromFolders() {
        val folder = File(BaseFile.GAME_FOLDER + "/arenas/")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val files = folder.listFiles() ?: return

        for (file in files) {
            if (file.isDirectory) {
                Game.loadGameFromConfig(file.name)
            }
        }
    }

}

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
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.game.ingame.GameSigns
import de.pixels.thehunter.util.game.ingame.GamesInventoryList
import de.pixels.thehunter.util.game.ingame.PlayerTeamHead
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.game.management.StatsSystem
import de.pixels.thehunter.util.misc.Autoupdater
import de.pixels.thehunter.util.misc.*
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class TheHunter : JavaPlugin() {

    /*
    TODO: Premium join
    TODO: grenade makes no damage
    TODO: Code Cleanup
     */


    companion object {
        var prefix = "§8[§6TheHunter§8] §f"
        lateinit var instance: TheHunter
    }

    lateinit var settings: Settings
    lateinit var messages: Messages
    lateinit var statsSystem: StatsSystem
    val version = "1.0.0"

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

    override fun onDisable() {
        StatsSystem.saveAllStatsPlayerToFiles()
        resetAllGames()
        messages.sendMessageToConsole("shutdown-message-successfully")
    }

    private fun resetAllGames() {
        for (game in GamesHandler.games) {
            game.mapResetter.resetMap()
        }
    }

    private fun initialize(pluginManager: PluginManager) {

        //Commands:
        getCommand("theHunter")!!.setExecutor(BaseCommand())
        getCommand("start")!!.setExecutor(StartCommand())

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
        pluginManager.registerEvents(AmmoHandler(), this)
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

        loadGamesFromFolders()

        Autoupdater().checkUpdate("41971")
    }


    private fun loadGamesFromFolders() {
        val folder = File(BaseFile.gameFolder + "/arenas/")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        val files = folder.listFiles()

        for (file in files) {
            if (file.isDirectory) {
                Game.loadGameFromConfig(file.name)
            }
        }
    }

}

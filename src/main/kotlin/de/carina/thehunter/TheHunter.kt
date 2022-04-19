/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 19:15 by Carina The Latest changes made by Carina on 19.04.22, 19:15 All contents of "TheHunter.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter

import de.carina.thehunter.commands.BaseCommand
import de.carina.thehunter.events.game.*
import de.carina.thehunter.events.misc.BlocksFlyEvent
import de.carina.thehunter.events.misc.LobbyInteraction
import de.carina.thehunter.events.misc.MapModify
import de.carina.thehunter.events.misc.PlayerJoinsServer
import de.carina.thehunter.guns.GunHandler
import de.carina.thehunter.items.chest.special.*
import de.carina.thehunter.items.configurator.LeaveItem
import de.carina.thehunter.util.files.BaseFile
import de.carina.thehunter.util.files.ItemSettings
import de.carina.thehunter.util.files.Messages
import de.carina.thehunter.util.files.Settings
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.game.GameSigns
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.Ammo
import de.carina.thehunter.util.misc.GamesInventoryList
import de.carina.thehunter.util.misc.StatsSystem
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class TheHunter : JavaPlugin() {

    /*
    TODOS till done:
    TODO: InventoryCommander
    TODO: anvilGUI
    TODO: Premium join
    TODO: AutoUpdater
    TODO: PlayerHiding
    TODO: Commands
    TODO: TOTAL CHECKUP
    TODO: Price Online Shop
    TODO: Testing
    TODO: Worldboarder bug
     */

    companion object {
        var PREFIX = "§8[§6TheHunter§8] §f"
        lateinit var instance: TheHunter
    }

    lateinit var settings: Settings
    lateinit var messages: Messages
    lateinit var itemSettings: ItemSettings
    lateinit var statsSystem: StatsSystem

    override fun onEnable() {
        instance = this
        settings = Settings("config.yml").addData()
        itemSettings = ItemSettings().addData()
        messages = Messages("messages.yml").addData()
        statsSystem = StatsSystem()
        StatsSystem.loadStatsPlayersFromFile()
        initialize(Bukkit.getPluginManager())
        PREFIX = settings.getColorCodedString("prefix")
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

        //Events:
        pluginManager.registerEvents(EggBomb(), this)
        pluginManager.registerEvents(BlocksFlyEvent(), this)
        pluginManager.registerEvents(PlayerJoinsServer(), this)
        pluginManager.registerEvents(Knife(), this)
        pluginManager.registerEvents(EyeSpy(), this)
        pluginManager.registerEvents(Healer(), this)
        pluginManager.registerEvents(Swapper(), this)
        pluginManager.registerEvents(Tracker(), this)
        pluginManager.registerEvents(Food(), this)
        pluginManager.registerEvents(EnergyDrink(), this)
        pluginManager.registerEvents(JumpStick(), this)
        pluginManager.registerEvents(ChestHandler(), this)
        pluginManager.registerEvents(Ammo(), this)
        pluginManager.registerEvents(GunHandler(), this)
        pluginManager.registerEvents(TeamDamage(), this)
        pluginManager.registerEvents(MapModify(), this)
        pluginManager.registerEvents(DeathChest(), this)
        pluginManager.registerEvents(PlayerChat(), this)
        pluginManager.registerEvents(GameSigns(), this)
        pluginManager.registerEvents(PlayerKillsOtherOrDies(), this)
        pluginManager.registerEvents(PlayerDisconnects(), this)
        pluginManager.registerEvents(LeaveItem(), this)
        pluginManager.registerEvents(GamesInventoryList(), this)
        pluginManager.registerEvents(LobbyInteraction(), this)

        loadGamesFromFolders()
    }


    fun loadGamesFromFolders() {
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
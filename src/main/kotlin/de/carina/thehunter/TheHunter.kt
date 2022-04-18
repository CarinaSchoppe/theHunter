/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 16.04.22, 12:24 by Carina The Latest changes made by Carina on 16.04.22, 12:24 All contents of "TheHunter.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter

import de.carina.thehunter.commands.BaseCommand
import de.carina.thehunter.events.game.ChestHandler
import de.carina.thehunter.events.misc.BlocksFlyEvent
import de.carina.thehunter.events.misc.PlayerJoinsServer
import de.carina.thehunter.items.chest.special.*
import de.carina.thehunter.util.files.ItemSettings

import de.carina.thehunter.util.files.Messages
import de.carina.thehunter.util.files.Settings
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.StatsSystem
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.java.JavaPlugin

class TheHunter : JavaPlugin() {

    /*
    TODOS:
    TODO: MapLoader
    TODO: GameStates
    TODO: GameSigns
    TODO: player Join event
    TODO: player leave event
    TODO: player death event
    TODO: Map event
    TODO: Gun event
    TODO: Cluster event
    TODO: EntityDamage event
    TODO: StatsSystem
    TODO: MultiGameMode
    TODO: CommandManager
    TODO: InventoryBuilder
    TODO: ItemBuilder
    TODO: InventoryCommander
    TODO: anvilGUI
    TODO: Guns
    TODO: ItemChests
    TODO: DeathChests
    TODO: Countdowns
    TODO: Premium join
    TODO: API
    TODO: Scoreboard
    TODO: TeamMode
    TODO: AutoUpdater
    TODO: Map Reset
    TODO: CleanUp
    TODO: Utility classes
    TODO: ItemChest
    TODO: Items (Eyespy, Knife, tracker, swapper, medipack, grenade, food, energydrink, Ammo)
    TODO: PlayerDropping
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
    }


}
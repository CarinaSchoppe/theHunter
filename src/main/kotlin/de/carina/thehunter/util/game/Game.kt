/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 16.04.22, 12:17 by Carina The Latest changes made by Carina on 16.04.22, 12:17 All contents of "Game.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.game

import de.carina.thehunter.TheHunter
import de.carina.thehunter.countdowns.*
import de.carina.thehunter.gamestates.*
import de.carina.thehunter.items.chest.special.ItemChest
import de.carina.thehunter.util.files.BaseFile
import de.carina.thehunter.util.misc.MapResetter
import de.carina.thehunter.util.misc.WorldboarderController
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.io.File

class Game(var name: String) {


    var lobbyLocation: Location? = null
    var backLocation: Location? = null
    var endLocation: Location? = null
    var arenaCenter: Location? = null

    lateinit var currentGameState: GameState
    lateinit var currentCountdown: Countdown
    lateinit var worldBoarderController: WorldboarderController
    lateinit var mapResetter: MapResetter
    lateinit var scoreBoard: Scoreboard
    lateinit var gameItems: GameItems
    lateinit var gameChest: ItemChest


    val countdowns = mutableListOf<Countdown>()
    val players = mutableSetOf<Player>()
    val spectators = mutableSetOf<Player>()
    val playerSpawns = mutableListOf<Location>()
    val teams = mutableSetOf<Team>()
    val gameStates = mutableListOf<GameState>()
    val gameEntities = mutableSetOf<Entity>()


    var randomPlayerDrop = true
    var teamsAllowed = true
    var chestFall = true
    var chestAmount = 50
    var teamMaxSize = 4
    var maxPlayers: Int = 20
    var minPlayers: Int = 2
    var currentPlayers: Int = 0
    var immunity = 10
    var teamDamage = false

    fun isGameValidConfigured(): Boolean {
        if (lobbyLocation == null || backLocation == null || endLocation == null || arenaCenter == null) return false
        if (minPlayers > maxPlayers) return false
        if (maxPlayers == 0) return false
        if (worldBoarderController.worldBoarderSize < 50) return false
        if (worldBoarderController.worldBoarderSize < worldBoarderController.minBorderSize) return false
        if (playerSpawns.size - 1 < maxPlayers && !randomPlayerDrop) return false
        return true
    }

    fun start() {
        TODO("not implemented")
    }

    fun end() {
        TODO("not implemented")
    }

    fun nextGameState() {
        currentGameState.stop()
        if (currentGameState is EndState) {
            GamesHandler.games.remove(this)
            loadGameFromConfig(name)
        } else {
            currentGameState = gameStates[gameStates.indexOf(currentGameState) + 1]
            currentGameState.start()
        }
    }


    fun saveGameToConfig(): Boolean {
        if (name == null) return false
        if (arenaCenter == null) return false
        if (backLocation == null) return false
        if (lobbyLocation == null) return false
        if (endLocation == null) return false
        val fileSettings = File("${BaseFile.gameFolder}/arenas/$name/settings.yml")
        val ymlSettings = YamlConfiguration.loadConfiguration(fileSettings)

        ymlSettings.addDefault("game-name", name)
        ymlSettings.addDefault("chest-fall", chestFall)
        ymlSettings.addDefault("chest-amount", chestAmount)
        ymlSettings.addDefault("random-drop", randomPlayerDrop)
        ymlSettings.addDefault("immunity", immunity)
        ymlSettings.addDefault("max-players", maxPlayers)
        ymlSettings.addDefault("min-players", minPlayers)
        ymlSettings.addDefault("world-boarder-size", worldBoarderController.worldBoarderSize)
        ymlSettings.addDefault("teams-allowed", teamsAllowed)
        ymlSettings.addDefault("team-max-size", teamMaxSize)
        ymlSettings.addDefault("team-damage", teamDamage)
        ymlSettings.addDefault("worldboarder-shrinkspeed", worldBoarderController.shrinkSpeed)
        ymlSettings.addDefault("worldboarder-min-border-size", worldBoarderController.minBorderSize)
        ymlSettings.addDefault("worldboarder-shrinkboarder", worldBoarderController.shrinkBoarder)

        val fileLocations = File("${BaseFile.gameFolder}/arenas/$name/locations.yml")
        val ymlLocations = YamlConfiguration.loadConfiguration(fileLocations)

        if (playerSpawns.isNotEmpty()) ymlLocations.addDefault("spawn-locations", playerSpawns)
        ymlLocations.addDefault("arena-center", arenaCenter)
        ymlLocations.addDefault("lobby-location", lobbyLocation)
        ymlLocations.addDefault("back-location", backLocation)
        ymlLocations.addDefault("end-location", endLocation)

        ymlSettings.options().copyDefaults(true)
        ymlLocations.options().copyDefaults(true)
        ymlLocations.save(fileLocations)
        ymlSettings.save(fileSettings)

        return true
    }

    companion object {
        fun loadGameFromConfig(fileName: String) {
            val fileSettings = File("${BaseFile.gameFolder}/arenas/$fileName/settings.yml")
            val ymlSettings = YamlConfiguration.loadConfiguration(fileSettings)

            val game = Game(ymlSettings.getString("game-name")!!)

            game.chestFall = ymlSettings.getBoolean("chest-fall")
            game.chestAmount = ymlSettings.getInt("chest-amount")
            game.randomPlayerDrop = ymlSettings.getBoolean("random-drop")
            game.maxPlayers = ymlSettings.getInt("max-players")
            game.minPlayers = ymlSettings.getInt("min-players")
            game.teamsAllowed = ymlSettings.getBoolean("teams-allowed")
            game.teamMaxSize = ymlSettings.getInt("team-max-size")
            game.immunity = ymlSettings.getInt("immunity")
            game.teamDamage = ymlSettings.getBoolean("team-damage")
            game.worldBoarderController.worldBoarderSize = ymlSettings.getInt("world-boarder-size")
            game.worldBoarderController.shrinkSpeed = ymlSettings.getInt("worldboarder-shrinkspeed")
            game.worldBoarderController.minBorderSize = ymlSettings.getInt("worldboarder-min-border-size")
            game.worldBoarderController.shrinkBoarder = ymlSettings.getBoolean("worldboarder-shrinkboarder")

            val fileLocations = File("${BaseFile.gameFolder}/arenas/$fileName/locations.yml")
            val ymlLocations = YamlConfiguration.loadConfiguration(fileLocations)

            game.playerSpawns.addAll(ymlLocations.getList("spawn-locations") as MutableList<Location>)
            game.lobbyLocation = ymlLocations.getLocation("lobby-location")!!
            game.backLocation = ymlLocations.getLocation("back-location")!!
            game.endLocation = ymlLocations.getLocation("end-location")!!
            game.arenaCenter = ymlLocations.getLocation("arena-center")!!
            game.create()
            game.currentGameState.start()
        }


    }

    fun create() {
        countdowns.addAll(listOf(LobbyCountdown(this), IngameCountdown(this), EndCountdown(this)))
        gameStates.addAll(listOf(LobbyState(this), IngameState(this), EndState(this)))
        currentGameState = gameStates[GameStates.LOBBY_STATE.id]
        currentCountdown = countdowns[Countdowns.LOBBY_COUNTDOWN.id]
        worldBoarderController = WorldboarderController(this)
        scoreBoard = Scoreboard(this)
        mapResetter = MapResetter(this)
        gameItems = GameItems(this)
        gameChest = ItemChest(this)
        gameItems.saveAllItems()
        gameItems.loadAllItems()
        gameItems.loadAllGunSettings()
        if (isGameValidConfigured()) GamesHandler.games.add(this)
        else Bukkit.getConsoleSender().sendMessage(TheHunter.instance.messages.messagesMap["wrong-config"]!!.replace("%game%", name))
    }

}
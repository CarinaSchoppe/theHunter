/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "Game.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.game

import de.carina.thehunter.TheHunter
import de.carina.thehunter.countdowns.Countdown
import de.carina.thehunter.countdowns.Countdowns
import de.carina.thehunter.countdowns.EndCountdown
import de.carina.thehunter.countdowns.LobbyCountdown
import de.carina.thehunter.gamestates.*
import de.carina.thehunter.items.ItemChest
import de.carina.thehunter.util.files.BaseFile
import de.carina.thehunter.util.misc.DeathChests
import de.carina.thehunter.util.misc.MapResetter
import de.carina.thehunter.util.misc.Util
import de.carina.thehunter.util.misc.WorldboarderController
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Sign
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.io.File

class Game(var name: String) {

    var lobbyLocation: Location? = null
    var spectatorLocation: Location? = null
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
    lateinit var deathChests: DeathChests


    val countdowns = mutableListOf<Countdown>()
    val players = mutableSetOf<Player>()
    val spectators = mutableSetOf<Player>()
    val playerSpawns = mutableListOf<Location>()
    val teams = mutableSetOf<Team>()
    val gameStates = mutableListOf<GameState>()
    val gameEntities = mutableSetOf<Entity>()
    val signs = mutableSetOf<Sign>()


    var randomPlayerDrop = true
    var teamsAllowed = true
    var chestFall = true
    var chestAmount = 50
    var teamMaxSize = 4
    var maxPlayers: Int = 20
    var minPlayers: Int = 1
    var immunity = 10
    var teamDamage = false
    var mapModify = false


    fun isGameValidConfigured(): Boolean {
        if (lobbyLocation == null || backLocation == null || endLocation == null || arenaCenter == null || spectatorLocation == null) return false
        if (minPlayers > maxPlayers) return false
        if (maxPlayers == 0) return false
        if (worldBoarderController.worldBoarderSize < 50) return false
        if (worldBoarderController.worldBoarderSize < worldBoarderController.minBorderSize) return false
        if (playerSpawns.size - 1 < maxPlayers && !randomPlayerDrop) return false
        return true
    }


    fun nextGameState() {
        if (currentGameState is EndState) {
            currentGameState.stop()
            currentGameState = gameStates[GameStates.LOBBY_STATE.id]
            Util.updateGameSigns(this)
            currentGameState = gameStates[GameStates.END_STATE.id]
            clearAll()
            GamesHandler.games.remove(this)
            loadGameFromConfig(name)
        } else {
            currentGameState.stop()
            currentGameState = gameStates[gameStates.indexOf(currentGameState) + 1]
            Util.updateGameSigns(this)
            currentGameState.start()
        }
    }

    private fun clearAll() {
        players.clear()
        spectators.clear()
        playerSpawns.clear()
        teams.clear()
        gameStates.clear()
        gameEntities.clear()
        signs.clear()
        gameEntities.clear()
        countdowns.clear()
    }

    private fun saveGameToConfig(): Boolean {
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
        ymlSettings.addDefault("map-modify", mapModify)
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
        ymlLocations.addDefault("spectator-location", spectatorLocation)
        ymlLocations.addDefault("lobby-location", lobbyLocation)
        ymlLocations.addDefault("back-location", backLocation)
        ymlLocations.addDefault("end-location", endLocation)

        ymlSettings.options().copyDefaults(true)
        ymlLocations.options().copyDefaults(true)
        ymlLocations.save(fileLocations)
        ymlSettings.save(fileSettings)
        Bukkit.getConsoleSender().sendMessage(TheHunter.instance.messages.messagesMap["game-successfully-saved"]!!.replace("%game%", name))
        return true
    }

    companion object {
        fun loadGameFromConfig(fileName: String) {
            val fileSettings = File("${BaseFile.gameFolder}/arenas/$fileName/settings.yml")
            val ymlSettings = YamlConfiguration.loadConfiguration(fileSettings)
            val game = Game(ymlSettings.getString("game-name")!!)
            game.create()
            game.chestFall = ymlSettings.getBoolean("chest-fall")
            game.chestAmount = ymlSettings.getInt("chest-amount")
            game.randomPlayerDrop = ymlSettings.getBoolean("random-drop")
            game.maxPlayers = ymlSettings.getInt("max-players")
            game.minPlayers = ymlSettings.getInt("min-players")
            game.teamsAllowed = ymlSettings.getBoolean("teams-allowed")
            game.teamMaxSize = ymlSettings.getInt("team-max-size")
            game.immunity = ymlSettings.getInt("immunity")
            game.mapModify = ymlSettings.getBoolean("map-modify")
            game.teamDamage = ymlSettings.getBoolean("team-damage")
            game.worldBoarderController.worldBoarderSize = ymlSettings.getInt("world-boarder-size")
            game.worldBoarderController.shrinkSpeed = ymlSettings.getInt("worldboarder-shrinkspeed")
            game.worldBoarderController.minBorderSize = ymlSettings.getInt("worldboarder-min-border-size")
            game.worldBoarderController.shrinkBoarder = ymlSettings.getBoolean("worldboarder-shrinkboarder")

            val fileLocations = File("${BaseFile.gameFolder}/arenas/$fileName/locations.yml")
            val ymlLocations = YamlConfiguration.loadConfiguration(fileLocations)

            if (ymlLocations.getList("spawn-locations") != null)
                game.playerSpawns.addAll(ymlLocations.getList("spawn-locations") as MutableList<Location>)
            game.lobbyLocation = ymlLocations.getLocation("lobby-location")!!
            game.backLocation = ymlLocations.getLocation("back-location")!!
            game.endLocation = ymlLocations.getLocation("end-location")!!
            game.arenaCenter = ymlLocations.getLocation("arena-center")!!
            game.spectatorLocation = ymlLocations.getLocation("spectator-location")!!
            game.finish()
            game.currentGameState = game.gameStates[GameStates.LOBBY_STATE.id]
            game.currentGameState.start()
            Bukkit.getConsoleSender().sendMessage(TheHunter.instance.messages.messagesMap["loaded-game-successfully"]!!.replace("%game%", game.name))
            game.worldBoarderController.resetWorldBoarder()
        }


    }


    fun checkWinning(): Boolean {
        when (players.size) {
            /*    1 -> {
                    val message = TheHunter.instance.messages.messagesMap["player-won"]!!.replace("%player%", players.first().name)
                    for (spectator in spectators)
                        spectator.sendMessage(message)
                    players.forEach { it.sendMessage(message) }
                    TheHunter.instance.statsSystem.playerWon(players.first())
                    return true
                }*/
            0 -> {
                val message = TheHunter.instance.messages.messagesMap["game-over"]!!
                for (spectator in spectators)
                    spectator.sendMessage(message)
                return true
            }

            else -> {
                val team: Team = teams.find {
                    it.teamMembers.contains(players.first())
                } ?: return false
                if (!team.teamMembers.containsAll(players))
                    return false
                val message = TheHunter.instance.messages.messagesMap["team-won"]!!.replace("%leader%", team.teamLeader.name)
                for (spectator in spectators)
                    spectator.sendMessage(message)
                players.forEach {
                    it.sendMessage(message)
                    TheHunter.instance.statsSystem.playerWon(it)
                }
                return true
            }
        }
    }

    fun create() {
        countdowns.addAll(listOf(LobbyCountdown(this), EndCountdown(this)))
        gameStates.addAll(listOf(LobbyState(this), IngameState(this), EndState(this)))

        currentGameState = gameStates[GameStates.LOBBY_STATE.id]
        currentCountdown = countdowns[Countdowns.LOBBY_COUNTDOWN.id]
        worldBoarderController = WorldboarderController(this)
        scoreBoard = Scoreboard(this)
        mapResetter = MapResetter(this)
        gameItems = GameItems(this)
        gameChest = ItemChest(this)
        deathChests = DeathChests(this)
        GamesHandler.setupGames.add(this)
    }

    fun finish() {
        if (!isGameValidConfigured()) {
            Bukkit.getConsoleSender().sendMessage(TheHunter.instance.messages.messagesMap["wrong-config"]!!.replace("%game%", name))
            return
        }
        currentGameState.stop()
        currentGameState = gameStates[GameStates.END_STATE.id]
        currentGameState.stop()
        GamesHandler.setupGames.remove(this)
        GamesHandler.games.remove(this)
        currentGameState = gameStates[GameStates.LOBBY_STATE.id]
        currentGameState.start()
        saveGameToConfig()
        gameItems.saveAllItems()
        gameItems.loadAllItems()
        gameItems.loadAllGunSettings()
        GamesHandler.games.add(this)
        Util.updateGameSigns(this)
        Bukkit.getConsoleSender().sendMessage(TheHunter.instance.messages.messagesMap["loaded-game-successfully"]!!.replace("%game%", name))

    }

}
/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:58 PM All contents of "Game.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.game.management

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.countdowns.Countdown
import de.pixels.thehunter.countdowns.Countdowns
import de.pixels.thehunter.countdowns.EndCountdown
import de.pixels.thehunter.countdowns.LobbyCountdown
import de.pixels.thehunter.gamestates.*
import de.pixels.thehunter.items.util.ItemChest
import de.pixels.thehunter.util.files.BaseFile
import de.pixels.thehunter.util.game.ingame.*
import de.pixels.thehunter.util.misc.ConstantStrings
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
    lateinit var itemSettings: ItemSettings
    lateinit var gameChest: ItemChest
    lateinit var deathChest: DeathChest


    val countdowns = mutableListOf<Countdown>()
    val currentGameKills = mutableMapOf<Player, Int>()
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
    var regenerate = false


    fun isGameInvalidConfigured(): Boolean {
        if (lobbyLocation == null || backLocation == null || endLocation == null || arenaCenter == null || spectatorLocation == null || minPlayers > maxPlayers || maxPlayers <= 0 || minPlayers < 0 || worldBoarderController.worldBoarderSize < 50 || worldBoarderController.worldBoarderSize < worldBoarderController.minBorderSize) return true

        return playerSpawns.size - 1 < maxPlayers && !randomPlayerDrop
    }


    fun nextGameState() {
        if (currentGameState is EndState) {
            currentGameState.stop()
            currentGameState = gameStates[GameStates.LOBBY_STATE.id]
            GameSigns.updateGameSigns(this)
            currentGameState = gameStates[GameStates.END_STATE.id]
            clearAll()
            GamesHandler.games.remove(this)

            loadGameFromConfig(name)
        } else {
            currentGameState.stop()
            currentGameState = gameStates[gameStates.indexOf(currentGameState) + 1]
            GameSigns.updateGameSigns(this)
            currentGameState.start()
        }
    }

    private fun clearAll() {
        playerSpawns.clear()
        teams.clear()
        signs.clear()
        players.clear()
        spectators.clear()
        gameEntities.clear()
        countdowns.clear()
        gameStates.clear()
        itemSettings.settingsMap.clear()
        gameChest.chests.clear()
        deathChest.deathChests.clear()
    }

    private fun saveGameToConfig(): Boolean {
        if (arenaCenter == null || backLocation == null || lobbyLocation == null || endLocation == null) return false
        val fileSettings = File("${BaseFile.GAME_FOLDER}/arenas/$name/settings.yml")
        val ymlSettings = YamlConfiguration.loadConfiguration(fileSettings)

        ymlSettings.addDefault("game-name", name)
        ymlSettings.addDefault("chest-fall", chestFall)
        ymlSettings.addDefault("chest-amount", chestAmount)
        ymlSettings.addDefault("random-drop", randomPlayerDrop)
        ymlSettings.addDefault("immunity", immunity)
        ymlSettings.addDefault("map-modify", mapModify)
        ymlSettings.addDefault("player-regenerate", regenerate)
        ymlSettings.addDefault("max-players", maxPlayers)
        ymlSettings.addDefault("min-players", minPlayers)
        ymlSettings.addDefault("world-boarder-size", worldBoarderController.worldBoarderSize)
        ymlSettings.addDefault("teams-allowed", teamsAllowed)
        ymlSettings.addDefault("team-max-size", teamMaxSize)
        ymlSettings.addDefault("team-damage", teamDamage)
        ymlSettings.addDefault("worldboarder-shrinkspeed", worldBoarderController.shrinkSpeed)
        ymlSettings.addDefault("worldboarder-min-border-size", worldBoarderController.minBorderSize)
        ymlSettings.addDefault("worldboarder-shrinkboarder", worldBoarderController.shrinkBoarder)

        val fileLocations = File("${BaseFile.GAME_FOLDER}/arenas/$name/locations.yml")
        val ymlLocations = YamlConfiguration.loadConfiguration(fileLocations)

        if (playerSpawns.isNotEmpty()) ymlLocations.addDefault(ConstantStrings.SPAWN_LOCATIONS, playerSpawns)
        ymlLocations.addDefault("arena-center", arenaCenter)
        ymlLocations.addDefault("spectator-location", spectatorLocation)
        ymlLocations.addDefault("lobby-location", lobbyLocation)
        ymlLocations.addDefault("back-location", backLocation)
        ymlLocations.addDefault("end-location", endLocation)

        ymlSettings.options().copyDefaults(true)
        ymlLocations.options().copyDefaults(true)
        ymlLocations.save(fileLocations)
        ymlSettings.save(fileSettings)
        TheHunter.instance.messages.messagesMap["game-successfully-saved"]?.let {
            Bukkit.getConsoleSender().sendMessage(
                it.replace(
                    ConstantStrings.GAME_PERCENT,
                    name
                )
            )
        }
        return true
    }

    companion object {
        fun loadGameFromConfig(fileName: String) {
            val fileSettings = File("${BaseFile.GAME_FOLDER}/arenas/$fileName/settings.yml")
            val ymlSettings = YamlConfiguration.loadConfiguration(fileSettings)
            val game = ymlSettings.getString("game-name")?.let { Game(it) }
            game?.create()
            game?.chestFall = ymlSettings.getBoolean("chest-fall")
            game?.chestAmount = ymlSettings.getInt("chest-amount")
            game?.randomPlayerDrop = ymlSettings.getBoolean("random-drop")
            game?.maxPlayers = ymlSettings.getInt("max-players")
            game?.minPlayers = ymlSettings.getInt("min-players")
            game?.teamsAllowed = ymlSettings.getBoolean("teams-allowed")
            game?.teamMaxSize = ymlSettings.getInt("team-max-size")
            game?.immunity = ymlSettings.getInt("immunity")
            game?.mapModify = ymlSettings.getBoolean("map-modify")
            game?.teamDamage = ymlSettings.getBoolean("team-damage")
            game?.regenerate = ymlSettings.getBoolean("player-regenerate")
            game?.worldBoarderController?.worldBoarderSize = ymlSettings.getInt("world-boarder-size")
            game?.worldBoarderController?.shrinkSpeed = ymlSettings.getInt("worldboarder-shrinkspeed")
            game?.worldBoarderController?.minBorderSize = ymlSettings.getInt("worldboarder-min-border-size")
            game?.worldBoarderController?.shrinkBoarder = ymlSettings.getBoolean("worldboarder-shrinkboarder")

            val fileLocations = File("${BaseFile.GAME_FOLDER}/arenas/$fileName/locations.yml")
            val ymlLocations = YamlConfiguration.loadConfiguration(fileLocations)

            if (ymlLocations.getList(ConstantStrings.SPAWN_LOCATIONS) != null && ymlLocations.getList(ConstantStrings.SPAWN_LOCATIONS) is MutableList<*>)
                game?.playerSpawns?.addAll(ymlLocations.getList(ConstantStrings.SPAWN_LOCATIONS) as MutableList<Location>)
            game?.lobbyLocation = ymlLocations.getLocation("lobby-location")
            game?.backLocation = ymlLocations.getLocation("back-location")
            game?.endLocation = ymlLocations.getLocation("end-location")
            game?.arenaCenter = ymlLocations.getLocation("arena-center")
            game?.spectatorLocation = ymlLocations.getLocation("spectator-location")
            game?.finish()
            game?.currentGameState = game?.gameStates?.get(GameStates.LOBBY_STATE.id) ?: return
            game.currentGameState.start()
            game.worldBoarderController.resetWorldBoarder()
        }


    }


    fun checkWinning(): Boolean {
        return when (players.size) {

            0 -> {
                val message = TheHunter.instance.messages.messagesMap["game-over"]
                for (spectator in spectators)
                    message?.let { spectator.sendMessage(it) }
                true
            }

            else -> {
                val team = teams.find {
                    it.teamMembers.containsAll(players)
                }
                if (team == null && players.size == 1) {
                    val message = TheHunter.instance.messages.messagesMap["player-won"]?.replace(
                        "%player%",
                        players.first().name
                    )
                    for (spectator in spectators) message?.let { spectator.sendMessage(it) }
                    players.forEach { message?.let { message -> it.sendMessage(message) } }
                    TheHunter.instance.statsSystem.playerWon(players.first())
                    return true
                } else if (team == null) return false
                val message =
                    TheHunter.instance.messages.messagesMap["team-won"]?.replace("%leader%", team.teamLeader.name)
                for (spectator in spectators) message?.let { spectator.sendMessage(it) }
                players.forEach {
                    message?.let { message -> it.sendMessage(message) }
                    TheHunter.instance.statsSystem.playerWon(it)
                }
                true
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
        itemSettings = ItemSettings(this)
        gameChest = ItemChest(this)
        deathChest = DeathChest(this)
        GamesHandler.setupGames.add(this)
    }

    fun finish() {
        if (isGameInvalidConfigured()) {
            TheHunter.instance.messages.messagesMap["wrong-config"]?.let {
                Bukkit.getConsoleSender()
                    .sendMessage(it.replace("%game%", name))
            }
            return
        }

        GamesHandler.setupGames.remove(this)
        GamesHandler.games.remove(this)
        currentGameState = gameStates[GameStates.LOBBY_STATE.id]
        currentGameState.start()
        saveGameToConfig()
        gameItems.saveAllItems()
        gameItems.loadAllItems()
        itemSettings.addData()
        gameItems.loadAllGunSettings()
        GamesHandler.games.add(this)
        GameSigns.updateGameSigns(this)
        TheHunter.instance.messages.messagesMap["loaded-game-successfully"]?.let {
            Bukkit.getConsoleSender()
                .sendMessage(it.replace("%game%", name))
        }

    }

}

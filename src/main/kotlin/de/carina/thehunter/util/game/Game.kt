/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 07.04.22, 17:07 by Carina The Latest changes made by Carina on 07.04.22, 17:07 All contents of "Game.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.game

import de.carina.thehunter.countdowns.Countdown
import de.carina.thehunter.countdowns.EndCountdown
import de.carina.thehunter.countdowns.IngameCountdown
import de.carina.thehunter.countdowns.LobbyCountdown
import de.carina.thehunter.gamestates.GameState
import de.carina.thehunter.gamestates.GameStates
import de.carina.thehunter.util.files.BaseFile
import de.carina.thehunter.util.misc.WorldboarderController
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

class Game(private val gameName: String) {


    lateinit var gameState: GameState
    lateinit var countdown: Countdown
    val countdowns = listOf(LobbyCountdown(), IngameCountdown(), EndCountdown())
    var MAX_PLAYERS: Int = 0
    var MIN_PLAYERS: Int = 0
    var currentPlayers: Int = 0
    var gameStarted: Boolean = false
    val players = mutableSetOf<Player>()
    val spectators = mutableSetOf<Player>()
    val playerLocations = mutableMapOf<Player, Location>()
    val randomDrop = true
    lateinit var lobbyLocation: Location
    lateinit var backLocation: Location
    lateinit var endLocation: Location
    var worldBoarderSize = 50000
    val worldBoarderController = WorldboarderController()

    fun start() {
        TODO("not implemented")
    }

    fun end() {
        TODO("not implemented")
    }

    fun nextGameState() {
        gameState = GameStates.gameStates[GameStates.gameStates.indexOf(gameState) + 1]
    }


    fun saveGameToConfig() {
        val fileSettings = File("${BaseFile.gameFolder}/arenas/$gameName/settings.yml")
        val ymlSettings = YamlConfiguration.loadConfiguration(fileSettings)
        ymlSettings.set("game-name", gameName)
        ymlSettings.set("random-drop", randomDrop)
        ymlSettings.set("max-players", MAX_PLAYERS)
        ymlSettings.set("min-players", MIN_PLAYERS)
        ymlSettings.set("world-boarder-size", worldBoarderSize)


        val fileLocations = File("${BaseFile.gameFolder}/arenas/$gameName/locations.yml")
        val ymlLocations = YamlConfiguration.loadConfiguration(fileLocations)
        ymlLocations.set("spawn-locations", playerLocations)
        ymlLocations.set("lobby-location", lobbyLocation)
        ymlLocations.set("back-location", backLocation)
        ymlLocations.set("end-location", endLocation)

    }

    fun loadGameFromConfig(fileName: String) {
        TODO("not implemented")
    }
}
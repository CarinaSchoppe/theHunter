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

    /**
     * Represents the location of the lobby.
     *
     * A lobby location is used to store the coordinates (latitude and longitude) of the lobby in a specific location.
     * It can be used to track and determine the position of the lobby within a larger context.
     *
     * @property lobbyLocation The location of the lobby. It contains the latitude and longitude values.
     *                          If the value is null, it means that the lobby location is not set.
     */
    var lobbyLocation: Location? = null

    /**
     * The location of the spectator.
     *
     * This variable is used to store the location of the spectator within the application.
     * It represents a nullable Location object which can be null if the location is not available.
     *
     * @property spectatorLocation The nullable location of the spectator. It can be null.
     */
    var spectatorLocation: Location? = null

    /**
     * Represents the previous location.
     *
     * This variable stores the previous location when navigating between screens or states.
     *
     * @property backLocation The previous location, or null if there is no previous location.
     */
    var backLocation: Location? = null

    /**
     * Represents the end location of a certain operation or movement.
     *
     * This variable may be null if the end location is not yet known or applicable.
     *
     * @property endLocation The end location.
     */
    var endLocation: Location? = null

    /**
     * The center location of the arena.
     *
     * @property arenaCenter The center location of the arena. Can be null if not specified.
     */
    var arenaCenter: Location? = null

    /**
     * Represents the current state of the game.
     *
     * This variable is used to store the current state of the game.
     * It is declared using the lateinit keyword, which means that it will be
     * initialized at a later point in the code.
     *
     * The type of the variable is GameState, which is likely a custom class
     * representing the game state. The exact details, properties, and methods
     * of the GameState class are not provided in this documentation.
     *
     * It is important to initialize this variable before accessing its properties
     * or calling its methods, otherwise a NullPointerException will be thrown.
     * The developer should ensure that the currentGameState variable is initialized
     * before using it.
     *
     * Usage:
     * ```
     * lateinit var currentGameState: GameState
     * //...
     * currentGameState = GameState() // Initialize the variable with an instance of GameState
     * // Use the currentGameState object to get or set properties, or to call methods
     * ```
     */
    lateinit var currentGameState: GameState

    /**
     * Represents a countdown timer.
     *
     * @property currentCountdown The current countdown timer.
     */
    lateinit var currentCountdown: Countdown

    /**
     * World Boarder Controller class manages the boundaries of the game world.
     * The controller is responsible for handling operations related to the world's boundaries,
     * such as updating the boarder position, checking if a position is within the boarder limits,
     * and controlling the behavior when a position reaches the boarder.
     *
     * @property worldBoarderController The instance of the WorldboarderController class.
     */
    lateinit var worldBoarderController: WorldboarderController

    /**
     * A class used to reset a map to its initial state.
     *
     * @property mapResetter  The map resetter instance.
     */
    lateinit var mapResetter: MapResetter

    /**
     * The Scoreboard class represents a scoreboard for keeping track of scores.
     *
     * @property scoreBoard The scoreboard instance.
     */
    lateinit var scoreBoard: Scoreboard

    /**
     * Represents the game items used in a game.
     */
    lateinit var gameItems: GameItems

    /**
     * Represents the settings for an item.
     *
     * @property itemSettings The settings for the item. It is uninitialized and should be set before usage.
     */
    lateinit var itemSettings: ItemSettings

    /**
     * Represents a game chest containing items.
     *
     * The gameChest variable is intended to hold an instance of the ItemChest class.
     * It is declared with the "lateinit" modifier, indicating that it will be initialized
     * at a later point in the code.
     */
    lateinit var gameChest: ItemChest

    /**
     * Represents a death chest in the game.
     *
     * The `deathChest` variable represents a chest that is created when a player dies in the game. It is used to store the
     * items that the player had in their inventory at the time of death, so that they can retrieve them later.
     *
     * The `deathChest` variable is declared using the `lateinit` keyword, which means that it is not immediately assigned a
     * value. Instead, it must be assigned a value before it is used, otherwise it will throw an `UninitializedPropertyAccessException`.
     *
     * Example usage:
     *
     * ```
     * lateinit var deathChest: DeathChest
     *
     * // Create a new death chest and assign it to the `deathChest` variable
     * deathChest = DeathChest()
     * ```
     */
    lateinit var deathChest: DeathChest


    /**
     * Represents a list of countdowns.
     *
     * A [Countdown] represents a time-limited event that counts down from a specified duration.
     * Multiple countdowns can be stored in this [Countdowns] collection.
     *
     * @property countdowns The list of countdown objects.
     */
    val countdowns = mutableListOf<Countdown>()

    /**
     * Keeps track of the number of kills for each player in the current game.
     */
    val currentGameKills = mutableMapOf<Player, Int>()

    /**
     * Represents a set of players.
     *
     * This variable is a mutable set that contains instances of the Player class.
     *
     * @property players The mutable set of Player instances.
     */
    val players = mutableSetOf<Player>()

    /**
     * Variable to store a mutable set of players representing the spectators.
     *
     * The set can be modified to add or remove players as spectators during the execution of the program.
     */
    val spectators = mutableSetOf<Player>()

    /**
     * Represents the list of player spawn locations.
     *
     * This variable holds a mutable list of [Location] objects that represent the spawn locations for the players in a game.
     * The spawn locations can be added, removed, or modified dynamically as needed.
     *
     * @property playerSpawns The mutable list of player spawn locations.
     */
    val playerSpawns = mutableListOf<Location>()

    /**
     * Represents a collection of teams.
     *
     * The `teams` variable is a mutable set that stores objects of type `Team`.
     * It can be used to manage and manipulate a collection of teams.
     *
     * @property teams The mutable set that stores instances of `Team`.
     *
     * @see Team
     */
    val teams = mutableSetOf<Team>()

    /**
     * Represents a list of game states.
     *
     * @property gameStates The list of game states.
     */
    val gameStates = mutableListOf<GameState>()

    /**
     * Represents a collection of game entities.
     *
     * This variable stores a mutable set of [Entity] objects that represent the game entities in a game.
     * Entities can be added or removed from the collection dynamically.
     *
     * @property gameEntities a mutable set of [Entity] objects representing the game entities
     *
     * @see Entity
     */
    val gameEntities = mutableSetOf<Entity>()

    /**
     * Represents a mutable set of signs.
     *
     * @property signs The mutable set of signs.
     */
    val signs = mutableSetOf<Sign>()


    /**
     * Represents a flag indicating whether a random player drop is enabled.
     *
     * @type {boolean}
     */
    var randomPlayerDrop = true

    /**
     * Holds the information about whether teams are allowed or not.
     *
     * @since 1.0
     */
    var teamsAllowed = true

    /**
     * Represents the status of a chest falling.
     *
     * The `chestFall` variable indicates whether a chest has fallen or not. It is a boolean value,
     * where `true` implies that the chest has fallen, and `false` implies that the chest has not fallen.
     *
     * @since 1.0.0
     */
    var chestFall = true

    /**
     * Represents the amount of chests.
     *
     * @property chestAmount The current amount of chests.
     */
    var chestAmount = 50

    /**
     * Represents the maximum size allowed for a team.
     *
     * The value of this variable is 4, indicating that a team can have at most 4 members.
     *
     * @since 1.0
     */
    var teamMaxSize = 4

    /**
     * Represents the maximum number of players allowed in a game.
     *
     * @property maxPlayers The maximum number of players allowed in a game.
     */
    var maxPlayers: Int = 20

    /**
     * The minimum number of players required to start the game.
     *
     * @property minPlayers The value is an integer.
     */
    var minPlayers: Int = 1

    /**
     * Represents the immunity level of a character or entity.
     *
     * @property immunity The value representing the immunity level. The higher the value, the more immune the character is.
     */
    var immunity = 10

    /**
     * A boolean variable indicating whether there has been team damage.
     */
    var teamDamage = false

    /**
     * Determines whether the map is modifiable or not.
     *
     * @param mapModify - A variable that represents the modifiability of the map.
     * @return - Returns true if the map is modifiable, false otherwise.
     */
    var mapModify = false

    /**
     * Indicates whether the data needs to be regenerated.
     *
     * If set to true, it means that the data needs to be regenerated. If set to false, it means that the
     * data does not need to be regenerated.
     *
     * @type {boolean}
     */
    var regenerate = false


    /**
     * Checks if the game configuration is invalid.
     * A game configuration is invalid if:
     * - lobbyLocation is null
     * - backLocation is null
     * - endLocation is null
     * - arenaCenter is null
     * - spectatorLocation is null
     * - minPlayers is greater than maxPlayers
     * - maxPlayers is less than or equal to 0
     * - minPlayers is less than 0
     * - worldBoarderSize is less than 50
     * - worldBoarderSize is less than minBorderSize
     * - playerSpawns size minus 1 is less than maxPlayers and randomPlayerDrop is false
     *
     * @return true if the game configuration is invalid, false otherwise
     */
    fun isGameInvalidConfigured(): Boolean {
        if (lobbyLocation == null || backLocation == null || endLocation == null || arenaCenter == null || spectatorLocation == null || minPlayers > maxPlayers || maxPlayers <= 0 || minPlayers < 0 || worldBoarderController.worldBoarderSize < 50 || worldBoarderController.worldBoarderSize < worldBoarderController.minBorderSize) return true

        return playerSpawns.size - 1 < maxPlayers && !randomPlayerDrop
    }


    /**
     * Transition to the next game state.
     *
     * If the current game state is an EndState, the method performs the following steps:
     * 1. Stops the current game state.
     * 2. Sets the current game state to the LOBBY_STATE.
     * 3. Updates the game signs using the GameSigns.updateGameSigns() method.
     * 4. Sets the current game state to the END_STATE.
     * 5. Clears all data related to the current game.
     * 6. Removes the current game from the GamesHandler.games list.
     * 7. Loads the game configuration from the specified name using the loadGameFromConfig() method.
     *
     * If the current game state is not an EndState, the method performs the following steps:
     * 1. Stops the current game state.
     * 2. Sets the current game state to the next game state based on the index of the current game state in the gameStates list.
     * 3. Updates the game signs using the GameSigns.updateGameSigns() method.
     * 4. Starts the new current game state.
     */
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

    /**
     * Clears all game data and settings.
     * This method removes all player spawns, teams, signs, players, spectators, game entities, countdowns, game states,
     * item settings, game chests, and death chests from the game.
     */
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

    /**
     * Saves the current game configuration to a file.
     *
     * @return True if the game configuration was successfully saved, false otherwise.
     */
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
        TheHunter.instance.messagesFile.messagesMap["game-successfully-saved"]?.let {
            Bukkit.getConsoleSender().sendMessage(
                it.replace(
                    ConstantStrings.GAME_PERCENT,
                    name
                )
            )
        }
        return true
    }

    /**
     * The `Companion` class provides a companion object that contains a single function `loadGameFromConfig`. This function is responsible for reading game configuration from files and initializing the game object with the corresponding settings and locations.
     */
    companion object {
        /**
         * Loads a game configuration from a file.
         *
         * @param fileName The name of the configuration file.
         */
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


    /**
     * Checks if a winning condition has been met.
     *
     * @return true if a winning condition has been met, false otherwise
     */
    fun checkWinning(): Boolean {
        return when (players.size) {
            0 -> {
                val message = TheHunter.instance.messagesFile.messagesMap["game-over"]
                for (spectator in spectators)
                    message?.let { spectator.sendMessage(it) }
                true
            }

            else -> {
                val team = teams.find {
                    it.teamMembers.containsAll(players)
                }
                if (team == null && players.size == 1) {
                    val message = TheHunter.instance.messagesFile.messagesMap["player-won"]?.replace(
                        "%player%",
                        players.first().name
                    )
                    for (spectator in spectators) message?.let { spectator.sendMessage(it) }
                    players.forEach { message?.let { message -> it.sendMessage(message) } }
                    TheHunter.instance.statsSystem.playerWon(players.first())
                    return true
                } else if (team == null) return false
                val message =
                    TheHunter.instance.messagesFile.messagesMap["team-won"]?.replace("%leader%", team.teamLeader.name)
                for (spectator in spectators) message?.let { spectator.sendMessage(it) }
                players.forEach {
                    message?.let { message -> it.sendMessage(message) }
                    TheHunter.instance.statsSystem.playerWon(it)
                }
                true
            }
        }
    }

    /**
     * Creates a new instance of the game.
     *
     * This method initializes all the necessary components and sets the initial game state.
     * It adds countdowns and game states to their respective lists, and sets the current game state
     * and countdown based on the game's default states.
     * It also sets up various handlers, such as the WorldboarderController, Scoreboard, MapResetter,
     * GameItems, ItemSettings, ItemChest, and DeathChest.
     * Lastly, it adds the game to the setupGames list in the GamesHandler.
     */
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

    /**
     * This method is used to finish the game. It performs the following tasks:
     * 1. Checks if the game is invalidly configured. If so, it sends an error message to the console and returns.
     * 2. Removes the game from the setup list and games list.
     * 3. Sets the current game state to the lobby state and starts it.
     * 4. Saves the game configuration to the config file.
     * 5. Saves all game items.
     * 6. Loads all game items.
     * 7. Adds data to the item settings.
     * 8. Loads all gun settings.
     * 9. Adds the game back to the games list.
     * 10. Updates the game signs.
     * 11. Sends a success message to the console.
     */
    fun finish() {
        if (isGameInvalidConfigured()) {
            TheHunter.instance.messagesFile.messagesMap["wrong-config"]?.let {
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
        TheHunter.instance.messagesFile.messagesMap["loaded-game-successfully"]?.let {
            Bukkit.getConsoleSender()
                .sendMessage(it.replace("%game%", name))
        }

    }

}

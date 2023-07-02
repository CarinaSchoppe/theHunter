/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 11:06 PM All contents of "ConstantStrings.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.misc

object ConstantStrings {

    /**
     * Represents the constant value for dot points.
     *
     * The `DOT_POINTS` variable is used to store the string ".Points".
     * It can be used in various contexts, such as for displaying bullet points in a text or
     * as a separator in file names.
     *
     * This constant value is immutable and should not be modified.
     * It is assigned the string value ".Points" using the `const` keyword.
     *
     * Example Usage:
     * ```
     * val bulletPoints = "• First point" + DOT_POINTS + "• Second point" + DOT_POINTS + "• Third point"
     * println(bulletPoints)
     * ```
     *
     * Note:
     * This constant value does not include any leading or trailing white spaces.
     * If any additional characters or white spaces are required, they must be explicitly added.
     */
    const val DOT_POINTS = ".Points"

    /**
     * Constant representing the key for accessing the number of deaths in a dot notation format.
     *
     * The value of this constant is ".Deaths".
     */
    const val DOT_DEATHS = ".Deaths"

    /**
     * Represents the constant value for sniper ammo.
     *
     * @since 1.0
     */
    const val SNIPER_AMMO: String = "sniper-ammo"

    /**
     * Represents the constant variable for pistol ammo.
     *
     * The variable name is `PISTOL_AMMO` and its value is a string representing "pistol-ammo".
     */
    const val PISTOL_AMMO: String = "pistol-ammo"

    /**
     * Constant variable representing the name of rifle ammo.
     * This variable is of type String.
     *
     * @see <a href="https://example.com/rifle-ammo">Rifle Ammo Documentation</a>
     */
    const val RIFLE_AMMO: String = "rifle-ammo"

    /**
     * Represents the constant variable MINIGUN_AMMO.
     *
     * This variable is a string that holds the value "minigun-ammo".
     *
     * Usage:
     * ```
     * val ammoType: String = MINIGUN_AMMO
     * ```
     */
    const val MINIGUN_AMMO: String = "minigun-ammo"

    /**
     * Constant representing the key for spawn locations in a data structure.
     *
     * @since 1.0
     */
    const val SPAWN_LOCATIONS = "spawn-locations"

    /**
     * Represents the variable used to substitute a command name.
     *
     * The value of this variable is "%command%".
     * This variable is typically used in the context of string manipulation or command interpolation.
     *
     * @see String
     * @see Command
     */
    const val COMMAND_PERCENT = "%command%"

    /**
     * Constant variable representing the GUI command.
     *
     * The GUI command is used to indicate that a graphical user interface (GUI) interaction is performed or requested.
     *
     * Usage:
     * ```
     * if (command == GUI_COMMAND) {
     *     // Perform GUI-related actions
     * }
     * ```
     */
    const val GUI_COMMAND = "gui"

    /**
     * Represents the start command.
     *
     * The start command is used to initiate a particular action or process.
     * It is typically used as the input in a command-line interface or messaging system.
     *
     * @see Command
     */
    const val START_COMMAND = "start"

    /**
     * The STATS_COMMAND constant represents the command keyword used to request statistical information.
     */
    const val STATS_COMMAND = "stats"

    /**
     * The command string to identify the team.
     */
    const val TEAM_COMMAND = "team"

    /**
     * The constant value representing the key for SQLite database path.
     * Use this constant to specify the key when accessing or storing the SQLite database path.
     */
    const val SQLITE_PATH = "sqlite-path"

    /**
     * Constant variable representing the delete command.
     */
    const val DELETE_COMMAND = "delete"

    /**
     * The constant variable for the join command.
     *
     * This constant holds the value "join", which is used as a command to join or connect to a specific entity or group.
     *
     * Example usage:
     *     command == JOIN_COMMAND -> join(entityId)
     *
     * @see [Entity.join]
     * @see [Group.join]
     */
    const val JOIN_COMMAND = "join"

    /**
     * Represents the setup command.
     *
     * This variable is used to define the command string for performing setup operations.
     * It is typically used in command-line interfaces or as part of a configuration file.
     *
     * Usage:
     * ```
     * val command = SETUP_COMMAND
     * ```
     */
    const val SETUP_COMMAND = "setup"

    /**
     * Constant variable representing the leave command.
     *
     * This constant is used to specify the command to leave a certain action or process.
     * It is typically used in interactive systems or command-line interfaces.
     *
     * Usage:
     *     When the user wants to exit a specific action or process, they can enter the leave command.
     *     This command is case-sensitive.
     *
     * Example:
     *     If the user enters "leave" as a command, it will trigger the appropriate behavior associated with the leave command.
     *     The exact behavior will depend on how the leave command is implemented in the system.
     *
     * Notes:
     *     - The leave command is usually used as an alternative to other commands, such as "cancel" or "exit".
     *     - It is important to handle the leave command correctly and gracefully to avoid unexpected behavior or data loss.
     *     - Consider providing feedback or confirmation messages when the leave command is executed successfully.
     *     - The constant value of LEAVE_COMMAND should not be modified after initialization.
     *
     * @see [Other Related Commands]
     */
    const val LEAVE_COMMAND = "leave"

    /**
     * Represents the command string for accessing the inventory.
     *
     * This constant variable holds the value "inventory", which is used to represent
     * the command for accessing the inventory in the software system.
     *
     * Example usage:
     * ```
     * if (userCommand == INVENTORY_COMMAND) {
     *     // Access the inventory
     * }
     * ```
     */
    const val INVENTORY_COMMAND = "inventory"

    /**
     * A constant representing the key for indicating that a player is not in a team.
     *
     * This constant is used as the value for the key when accessing or storing player team information in a data structure,
     * such as a Map or Bundle.
     *
     * Usage example:
     * ```
     * // Initialize a Map to store player-team information
     * val playerTeamMap = mutableMapOf<String, String>()
     *
     * // Add a player to the team with the key-value pair
     * playerTeamMap["JohnDoe"] = PLAYER_NOT_IN_TEAM
     *
     * // Retrieve the team information for a player
     * val playerTeam = playerTeamMap["JohnDoe"]
     * if (playerTeam == PLAYER_NOT_IN_TEAM) {
     *     // Player does not belong to any team
     *     // Perform appropriate actions
     * }
     * ```
     */
    const val PLAYER_NOT_IN_TEAM = "player-not-in-team"

    /**
     * Represents the command string for accessing the settings in a software application.
     *
     * This constant variable stores the command string used to access and modify the settings
     * of a software application. The settings command is typically used in command-line interfaces
     * or user interfaces to provide a way for users to configure various application settings.
     *
     * Example usage:
     * ```
     * val command = SETTINGS_COMMAND // "settings"
     * ```
     */
    const val SETTINGS_COMMAND = "settings"

    /**
     * Constant variable representing the map reset command.
     * This command is used to reset the map to its default state.
     */
    const val MAP_RESET_COMMAND = "mapreset"

    /**
     * Represents the error message for not enough arguments provided.
     */
    const val NOT_ENOUGH_ARGUMENTS = "not-enough-arguments"

    /**
     * Represents a string placeholder for arguments in a template or a message.
     *
     * The value of this constant, ARGUMENTS_PERCENT, is "%arguments%".
     *
     * Example usage:
     *
     * ```
     * val message = "Hello, %arguments%! How are you?"
     * val args = "John Doe"
     * val formattedMessage = message.replace(ARGUMENTS_PERCENT, args)
     * println(formattedMessage) // Output: Hello, John Doe! How are you?
     * ```
     */
    const val ARGUMENTS_PERCENT = "%arguments%"

    /**
     * Represents a constant value indicating that a given player does not exist.
     *
     * @since Unknown
     */
    const val NOT_A_PLAYER = "not-a-player"

    /**
     * Represents the create command string.
     *
     * This variable is used to indicate the create command in a command-line interface or any other context where a command
     * needs to be represented by a string.
     *
     * The command "create" is typically used to create something, like a new file, a new object, or any other entity depending
     * on the specific context where the command is used.
     *
     * Example usage:
     *
     * ```
     * val command = CREATE_COMMAND
     * // Use the command string as needed
     * ```
     */
    const val CREATE_COMMAND = "create"

    /**
     * Represents the constant value for the case where a game does not exist.
     */
    const val GAME_NOT_EXISTS = "game-not-exists"

    /**
     * Represents the SELECT command used in SQL queries.
     *
     * The `SELECT` command is used to retrieve data from a database table.
     * It is typically followed by a list of columns to select and optionally,
     * a filter condition (WHERE clause) to narrow down the result set.
     *
     * This constant variable holds the string representation of the SELECT command,
     * which can be used in SQL query strings.
     */
    const val SELECT_COMMAND = "select"

    /**
     * Constant variable representing the remove command.
     *
     * The REMOVE_COMMAND constant is used to identify the remove command in the code. It is a string value that contains
     * the exact keyword for the remove command.
     *
     * Usage:
     *    - The REMOVE_COMMAND can be used to check if a given command is a remove command.
     *
     * Example:
     *    val command = "remove"
     *    if (command == REMOVE_COMMAND) {
     *        // Process the remove command
     *    }
     */
    const val REMOVE_COMMAND = "remove"

    /**
     * Represents the command string for configuring a certain aspect of the software.
     *
     * The value of `CONFIG_COMMAND` is set to "config". This command is typically used
     * to customize various settings or configurations within the software.
     *
     * Example usage:
     * ```
     * val command = CONFIG_COMMAND // "config"
     * ```
     */
    const val CONFIG_COMMAND = "config"

    /**
     * Represents the placeholder for the game name or percentage.
     *
     * This constant value is used as a placeholder in strings to denote the position where the game name or percentage
     * value should be inserted.
     *
     * Usage:
     * ```
     * val message = "The completion rate is $GAME_PERCENT."
     * println(message.replace(GAME_PERCENT, "75%"))
     * // Output: The completion rate is 75%.
     * ```
     */
    const val GAME_PERCENT = "%game%"

    /**
     * A constant representing the value for a command that is not found.
     *
     * This constant is used to indicate that the requested command could not be found. It is typically used
     * in command-line interfaces or other scenarios where commands are executed and handled.
     *
     * The value of this constant is "command-not-found".
     */
    const val COMMAND_NOT_FOUND = "command-not-found"

    /**
     * Represents a constant variable indicating the absence of permission.
     *
     * This constant is used to represent a situation where no permission is granted or available.
     *
     * The value of this constant is "no-permission".
     * It is intended to be used as a standard and universally understood value to indicate that
     * no permission is granted or available in the current context.
     */
    const val NO_PERMISSION = "no-permission"

    /**
     * Represents the value for the lobby spawn.
     *
     * This variable is used to store the key for retrieving the lobby spawn location.
     * The lobby spawn location is typically used as the starting point for players when joining a lobby.
     */
    const val LOBBY_SPAWN = "lobbyspawn"

    /**
     * Constant representing the keyword for spectator spawn.
     *
     * The value of this constant is "spectatorspawn".
     */
    const val SPECTATOR_SPAWN = "spectatorspawn"

    /**
     * A constant variable representing the healer amount.
     *
     * The `HEALER_AMOUNT` variable is used to indicate the amount of healers available.
     * It is typically used as a key when storing or retrieving the healer amount value.
     *
     * @see [Healer](link_to_healer_class)
     */
    const val HEALER_AMOUNT = "healer-amount"

    /**
     * Represents the constant value for the back spawn location.
     *
     * The `BACK_SPAWN` variable is used to indicate the name or identifier of the back spawn location.
     * It is a constant value that should not be modified during the execution of the program.
     *
     * Usage:
     * ```
     * if (location == BACK_SPAWN) {
     *     // Place the player at the back spawn location
     * }
     * ```
     */
    const val BACK_SPAWN = "backspawn"

    /**
     * Represents the placeholder for the power percentage value in a string.
     *
     * This constant variable is used to replace the "%power%" placeholder in a string with the actual power percentage value.
     * The value of this constant should be a string representation of the placeholder.
     *
     * Example:
     * "The battery is at %power% charge level."
     *
     * @see [String.replace]
     */
    const val POWER_PERCENT = "%power%"

    /**
     * Constant representing the key for food recharge.
     */
    const val FOOD_RECHARGE = "food-recharge"

    /**
     * Represents the placeholder for a player's name in a string.
     *
     * This variable is used to substitute the "%player%" placeholder with the actual name of a player in a string.
     * It is typically used in scenarios where a dynamic substitution of the player's name is required.
     *
     * Example usage:
     * ```
     * val message = "Welcome, $PLAYER_PERCENT! Enjoy your game!"
     * val playerName = "JohnDoe"
     * val formattedMessage = message.replace(PLAYER_PERCENT, playerName)
     * println(formattedMessage)
     * // Output: Welcome, JohnDoe! Enjoy your game!
     * ```
     */
    const val PLAYER_PERCENT = "%player%"

    /**
     * Constant representing the end spawn value.
     *
     * This variable holds the string value "endspawn".
     * It is used to indicate the end of a spawn process.
     */
    const val END_SPAWN = "endspawn"

    /**
     * Represents the key for the value indicating the time remaining until the game starts.
     *
     * This variable stores the value indicating the time remaining until the game starts.
     * It is typically used in scenarios where a countdown is displayed to the user before the
     * game commences.
     *
     * @since 1.0.0
     */
    const val GAME_STARTING_IN = "game-starting-in"

    /**
     * The constant variable representing the key name for the duration speedup.
     *
     * This constant is used to define the key name for the duration speedup in various contexts.
     * The duration speedup represents the factor by which a duration or time interval is sped up.
     * It is typically used in scenarios where the duration of an operation needs to be adjusted or scaled.
     *
     * Usage:
     * ```
     * val durationSpeedup = DURATION_SPEEDUP
     * ```
     */
    const val DURATION_SPEEDUP = "duration-speedup"

    /**
     * Constant representing the arena center.
     *
     * The ARENA_CENTER variable is a constant string that represents the center point of the arena.
     * It is typically used to indicate the reference point for various calculations or positioning
     * within the arena.
     */
    const val ARENA_CENTER = "arenacenter"

    /**
     * Constant variable that represents the message displayed when the countdown timer reaches its end.
     */
    const val ENDCOUNTDOWN_MESSAGE = "endcountdown-message"

    /**
     * Represents the block percentage value used in the system.
     *
     * This variable is a constant value representing a percentage block
     * placeholder. The value is "%block%".
     *
     * @since 1.0.0
     */
    const val BLOCK_PERCENT = "%block%"

    /**
     * This constant represents the duration of the end countdown.
     */
    const val ENDCOUNTDOWN_DURATION = "duration-endcountdown"

    /**
     * This constant variable represents the event name for the game starting.
     *
     * The value of this variable is "game-starting".
     *
     * This event is typically used to indicate that the game is about to start.
     * It can be used to trigger any necessary preparations or initialization
     * before the game begins.
     *
     * @see [Game]
     */
    const val GAME_STARTING = "game-starting"

    /**
     * Represents a string constant for representing time percentage.
     *
     * The variable `TIME_PERCENT` holds a string value "%time%".
     * It is typically used in a string template to be substituted with actual time percentage.
     *
     * @since 1.0
     *
     * @see String
     */
    const val TIME_PERCENT = "%time%"

    /**
     * Represents the placeholder for the size percentage.
     *
     * The `SIZE_PERCENT` constant is used as a placeholder string that can be replaced with the actual
     * size percentage value.
     *
     * Usage:
     * ```kotlin
     * val size: Int = 50
     * val message = "The progress is $SIZE_PERCENT complete." // "The progress is %size% complete."
     * val formattedMessage = message.replace(SIZE_PERCENT, size.toString()) // "The progress is 50% complete."
     * ```
     */
    const val SIZE_PERCENT = "%size%"

    /**
     * The constant value representing the player spawn location.
     */
    const val PLAYER_SPAWN = "playerspawn"

    /**
     * Represents the placeholder for the 'Killer' percentage.
     *
     * This variable is used as a placeholder to substitute the 'Killer' percentage value
     * in a formatted string or message. It should be replaced with the actual percentage.
     *
     * The value of this variable is '%killer%'.
     */
    const val KILLER_PERCENT = "%killer%"

    /**
     * Represents the placeholder for the percentage of players in a text or string.
     *
     * The constant value `PLAYERS_PERCENT` is used as a placeholder to indicate where the percentage
     * of players should be dynamically inserted into a text or string. This allows for flexible and
     * dynamic content generation where the percentage of players can be displayed within a larger context.
     *
     * For example:
     * - "We have increased our user base by $PLAYERS_PERCENT in the last quarter."
     * - "A total of $PLAYERS_PERCENT of the players completed the game successfully."
     *
     * Please note that this constant does not contain any example code or specific implementation details.
     * It is solely meant to serve as a placeholder value for substitution when generating dynamic content.
     */
    const val PLAYERS_PERCENT = "%players%"

    /**
     * Represents the constant value for the game speedup.
     *
     * The `GAME_SPEEDUP` constant is used to define the key for accessing the game speedup value.
     * The value of this constant should be a string that uniquely identifies the game speedup configuration.
     * It is typically used for retrieving or setting the game speedup in a game engine or application.
     */
    const val GAME_SPEEDUP = "game-speedup"

    /**
     * Represents the command string for inviting someone.
     *
     * This variable is used to identify and handle the "invite" command.
     */
    const val INVITE_COMMAND = "invite"

    /**
     * The constant value for the finish command.
     *
     * This constant represents the command used to indicate the completion or termination of a process or operation.
     * The "finish" command is typically used to signal the end of a task, allowing the program to proceed to the next step or exit.
     *
     * Usage:
     * ```
     * if (input == FINISH_COMMAND) {
     *     // Perform finishing actions
     *     ...
     * } else {
     *     // Continue with other tasks
     *     ...
     * }
     * ```
     */
    const val FINISH_COMMAND = "finish"

    /**
     * Represents a constant variable indicating that a player is not in the game.
     *
     * This constant is used to identify the state of a player who is currently not participating in the game.
     * It can be used as a value to compare against player states to determine if a player is in the game or not.
     *
     * Example usage:
     *
     * ```
     * if (playerState == PLAYER_NOT_IN_GAME) {
     *     println("Player is not currently in the game")
     * }
     * ```
     */
    const val PLAYER_NOT_IN_GAME = "player-not-in-game"

    /**
     * Holds the constant value for the accept command.
     *
     * The `ACCEPT_COMMAND` constant represents the keyword that is used to indicate acceptance of a command
     * or a request. It can be used as an input or a comparison value to trigger specific actions in the program.
     */
    const val ACCEPT_COMMAND = "accept"

    /**
     * The constant variable representing the promote command.
     *
     * This constant is used to identify the promote command in an application or script.
     * The value of this constant is "promote".
     *
     * Usage:
     * The PROMOTE_COMMAND constant can be used to perform actions specific to the promote command.
     * It can be used in conditional statements, comparisons, or as a parameter for functions or methods.
     *
     * Example:
     * ```
     * val inputCommand: String = readUserInput() // Assume user entered "promote"
     * if (inputCommand == PROMOTE_COMMAND) {
     *     // Perform specific actions for the promote command
     *     // ...
     * }
     * ```
     */
    const val PROMOTE_COMMAND = "promote"

}

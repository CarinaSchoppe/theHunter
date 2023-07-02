/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "Permissions.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.misc

object Permissions {

    /**
     * Represents the command used to reset the map.
     *
     * This command is used to reset the map to its default state. It can be used to clear any modifications or changes made to
     * the map and restore it to its original state.
     *
     * Usage: `mapreset`
     *
     * Please note that running this command may result in irreversible changes to the map, so use it with caution.
     *
     * @see <a href="https://example.com/mapreset">Further information about map resetting</a>
     */
    const val MAPRESET_COMMAND = "mapreset"

    /**
     * Constant variable representing the join command.
     * The join command is used to join a specific group or channel.
     */
    const val JOIN_COMMAND = "join"

    /**
     * Constant variable representing the 'settingsgui' configuration settings for GUI.
     */
    const val SETTINGS_GUI = "settingsgui"

    /**
     * Represents the constant value for the "EGG_BOMB".
     *
     * This constant represents the string "eggbomb". It is used in the code
     * to identify a specific value or behavior related to an "egg bomb".
     *
     * Usage:
     *     val eggBombType = EGG_BOMB
     *
     * Note: This constant is read-only (immutable).
     */
    const val EGG_BOMB = "eggbomb"

    /**
     * Represents the setup command used in the application.
     *
     * This command is used to initialize and configure the application or perform any necessary setup steps.
     * It is typically used at the beginning of the application or whenever setup is required.
     *
     * Usage example:
     * ```
     * val command = SETUP_COMMAND
     * // Execute the setup command
     * executeCommand(command)
     * ```
     *
     * @since 1.0.0
     */
    const val SETUP_COMMAND = "setup"

    /**
     * Represents the command to manage teams.
     *
     * The TEAM_COMMAND constant is used to identify and execute operations related to teams.
     * It is typically passed as a command parameter to trigger the team management functionality.
     *
     * Example usages:
     * - CommandManager.execute(TEAM_COMMAND, "create", "TeamA") // Creates a new team with name "TeamA"
     * - CommandManager.execute(TEAM_COMMAND, "delete", "TeamB") // Deletes the team with name "TeamB"
     *
     * @see CommandManager
     */
    const val TEAM_COMMAND = "team"

    /**
     * Constant variable that represents the setup GUI configuration.
     *
     * The value of this constant is "setupgui".
     *
     * This variable is used to specify the configuration for the setup graphical user interface (GUI).
     * It is typically used as a parameter or property key to identify and differentiate setup GUI-related operations
     * or configurations in the code.
     *
     * Example usages:
     * ```
     * // Set the setup GUI configuration
     * myConfig.setProperty(SETUP_GUI, "true");
     *
     * // Check if the setup GUI is enabled
     * val isSetupGUIEnabled = myConfig.getProperty(SETUP_GUI) == "true";
     * ```
     */
    const val SETUP_GUI = "setupgui"

    /**
     * Represents the command for removing a game.
     */
    const val REMOVE_GAME_COMMAND = "remove-game"

    /**
     * Constant variable representing the name of the GUI library "AnvilGUI".
     * This library provides a graphical user interface for creating interactive GUIs in Java applications.
     *
     * @see [AnvilGUI GitHub Repository](https://github.com/WesJD/AnvilGUI)
     */
    const val ANVIL_GUI = "anvilgui"

    /**
     * This constant represents the start command.
     *
     * The `START_COMMAND` variable holds the value "start", which is used as the command to initiate a particular action or process.
     * It is a constant variable, which means it cannot be modified once it has been assigned a value.
     *
     * This command can be used in various scenarios within a software application, such as starting a program, initiating a process,
     * or triggering a specific action.
     *
     * Example usage:
     * ```
     * if (command == START_COMMAND) {
     *     // Start the desired action or process
     * }
     * ```
     *
     * @see [Command pattern](https://en.wikipedia.org/wiki/Command_pattern)
     */
    const val START_COMMAND = "start"

    /**
     * Constant representing the "stats" command.
     *
     * This constant holds the value "stats", which is used as a command to request statistical information.
     * This command can be used in a software system to retrieve various statistics or metrics.
     *
     * Example usage:
     * mySystem.executeCommand(STATS_COMMAND);  // Request statistical information
     *
     * @see CommandExecutor
     */
    const val STATS_COMMAND = "stats"

    /**
     * Represents the leave command.
     *
     * This constant variable is used to identify the leave command in a program.
     * It is typically used as a string value to check if a user input matches the leave command.
     *
     * Usage:
     * if (userInput == LEAVE_COMMAND) {
     *    // Execute leave command
     * }
     *
     * @since 1.0.0
     */
    const val LEAVE_COMMAND = "leave"

    /**
     * Represents the constant value for the games inventory.
     *
     * The games inventory is a string constant used to identify the inventory
     * of games in the application.
     *
     * @since 1.0.0
     */
    const val GAMES_INVENTORY = "inventory"

    /**
     * Prefix used for permissions related to the "theHunter" feature.
     */
    const val PERMISSION_PREFIX = "theHunter"

    /**
     * Represents the constant value for the player inviter.
     *
     * This constant value, "playerinviter", is used to identify the player inviter in the code.
     * It is commonly used as a key or identifier in various contexts related to player invitation functionality.
     *
     * Note: This constant is declared as a top-level constant using the "const" keyword, indicating that its value is
     * determined at compile time and cannot be modified during runtime.
     *
     * Example usage:
     * ```
     * val inviterName = PLAYER_INVITER
     * ```
     */
    const val PLAYER_INVITER = "playerinviter"

    /**
     * Constant variable representing the sign join.
     *
     * This constant specifies the value "signjoin". It is used for joining or concatenating signs in a string.
     */
    const val SIGN_JOIN = "signjoin"

}

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "GameStates.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.gamestates

enum class GameStates(val id: Int) {

    /**
     * Represents the lobby state of a game.
     *
     * @param state The state of the lobby.
     */
    LOBBY_STATE(0),

    /**
     * Represents the in-game state of the application.
     *
     * This class provides methods to interact with the current state of the game being played.
     *
     * @param level The level of the game being played.
     */
    INGAME_STATE(1),

    /**
     * The END_STATE class represents the end state of a process.
     *
     * This class has a single field to store the state value. The state value represents the reason for the process to end.
     */
    END_STATE(2),

}

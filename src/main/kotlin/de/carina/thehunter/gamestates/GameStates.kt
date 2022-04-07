/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 07.04.22, 15:06 by Carina The Latest changes made by Carina on 07.04.22, 14:25 All contents of "GameStates.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.gamestates

object GameStates {
    const val LOBBY_STATE = 0
    const val INGAME_STATE = 1
    const val END_STATE = 2

    val gameStates = mutableListOf(LobbyState, IngameState, EndState)
}
/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 07.04.22, 20:18 by Carina The Latest changes made by Carina on 07.04.22, 20:18 All contents of "IngameState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.gamestates

import de.carina.thehunter.util.game.Game

class IngameState(game: Game) : GameState(game) {
    override fun start() {
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override val gameStateID: Int = GameStates.INGAME_STATE
}
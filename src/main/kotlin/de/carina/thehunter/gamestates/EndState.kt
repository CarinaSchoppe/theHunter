/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 16.04.22, 12:01 by Carina The Latest changes made by Carina on 16.04.22, 11:33 All contents of "EndState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.gamestates

import de.carina.thehunter.util.game.Game

class EndState(game: Game) : GameState(game) {
    override fun start() {
        TODO("Not yet implemented")
    }

    override fun stop() {


        game.mapResetter!!.resetMap()
    }

    override val gameStateID: Int = GameStates.END_STATE.id
}
/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 11:06 PM All contents of "GameState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.gamestates

import de.carina.thehunter.util.game.Game

abstract class GameState(val game: Game) {

    abstract fun start()
    abstract fun stop()
    abstract val gameStateID: Int

    override fun toString(): String {
        return this.javaClass.name
    }
}

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 11:06 PM All contents of "GameState.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.gamestates

import de.pixels.thehunter.util.game.management.Game

abstract class GameState(val game: Game) {

    /**
     * Starts the process or execution of a task.
     *
     * This method is abstract and should be implemented by subclasses to define the specific behavior of starting a task.
     *
     * @see [Task]
     */
    abstract fun start()

    /**
     * Stops the execution of the method.
     */
    abstract fun stop()

    /**
     * Represents the unique identifier of a game state.
     */
    abstract val gameStateID: Int

    /**
     * Returns a string representation of the object.
     *
     * @return the simple name of the Java class as a string representation of the object.
     */
    override fun toString(): String {
        return this.javaClass.simpleName
    }
}

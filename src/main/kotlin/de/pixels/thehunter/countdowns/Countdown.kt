/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "Countdown.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.countdowns

import de.pixels.thehunter.util.game.management.Game

abstract class Countdown(val game: Game) {


    /**
     * Represents the duration of a certain event or an interval of time.
     *
     * This variable stores the duration as an integer value, which represents the length of the event or interval
     * in some consistent unit of time measurement. The unit of time can be determined based on the context in which
     * this variable is used.
     *
     * The value of this variable should always be non-negative, as negative durations are considered invalid.
     *
     * This variable is abstract, meaning that it is intended to be implemented or extended by concrete subclasses
     * or instances depending on the specific needs of the program or system utilizing it.
     *
     * @property duration The length of the event or interval in some consistent unit of time measurement.
     */
    abstract var duration: Int

    /**
     * This method represents the idle state of the system.
     *
     * It is an abstract method that needs to be implemented by the subclasses that inherit from this class.
     * The method is responsible for defining the behavior of the system when it is in an idle state.
     *
     * Implementations of this method should include any actions or behaviors that need to be executed
     * when the system is not actively engaged in any specific task or operation.
     *
     * Note that this method does not return any value.
     *
     * @see [Subclass documentation] - To understand how this method is implemented in the subclasses.
     */
    abstract fun idle()

    /**
     * Start the process.
     *
     * This method should be implemented by subclasses to handle the starting of the process.
     */
    abstract fun start()

    /**
     * Stops the operation or current process.
     */
    abstract fun stop()

    /**
     * Represents the state indicating if an object is idle.
     *
     * @property isIdle true if the object is idle, false otherwise.
     */
    abstract var isIdle: Boolean

    /**
     * Abstract variable representing the ID.
     *
     * This variable is used to store an integer value representing an ID.
     * It is declared as an abstract value and must be implemented by subclasses.
     *
     * @since 1.0
     */
    abstract val id: Int
}

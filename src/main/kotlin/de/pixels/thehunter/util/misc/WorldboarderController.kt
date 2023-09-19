/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 8:53 PM All contents of "WorldboarderController.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.misc

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.game.management.Game
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

class WorldboarderController(private val game: Game) {

    /**
     * The speed at which an object shrinks.
     *
     * @type {number}
     * @name shrinkSpeed
     * @default 10
     */
    var shrinkSpeed = 10

    /**
     * Holds the minimum border size value.
     */
    var minBorderSize = 100

    /**
     * The size of the world border.
     *
     * @property worldBoarderSize The size of the world border as an integer.
     */
    var worldBoarderSize = 500

    /**
     * Determines whether the boarder should be shrinked or not.
     *
     * @property shrinkBoarder Specifies if the boarder should be shrinked or not.
     */
    var shrinkBoarder = true

    /**
     * Represents a Bukkit task that has been scheduled but has not yet been run.
     *
     * This variable is declared as `private` and `lateinit`, indicating that it
     * is a private member that will be assigned a value at a later time.
     *
     * @see BukkitTask
     */
    private lateinit var task: BukkitTask

    /**
     * Shrinks the world border if the shrinkBoarder flag is set.
     *
     * @see [resetWorldBoarder]
     * @see [Bukkit.getScheduler]
     * @see [Bukkit.getScheduler.runTaskTimer]
     */
    fun shrinkWorld() {
        if (!shrinkBoarder)
            return

        resetWorldBoarder()
        task = Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Runnable {
            if ((game.arenaCenter?.world?.worldBorder?.size?.minus(1) ?: return@Runnable) >= minBorderSize) {
                game.arenaCenter?.world?.worldBorder?.size?.minus(1).also {
                    if (it != null) {
                        game.arenaCenter?.world?.worldBorder?.size = it
                    }
                }
            } else {
                task.cancel()
            }
        }, (shrinkSpeed * 20).toLong(), (shrinkSpeed * 20).toLong())
    }

    /**
     * Resets the world border for the game arena.
     */
    fun resetWorldBoarder() {
        game.arenaCenter?.world?.worldBorder?.center = game.arenaCenter ?: return
        game.arenaCenter?.world?.worldBorder?.damageAmount = 100.0
        game.arenaCenter?.world?.worldBorder?.size = worldBoarderSize.toDouble()
    }

    companion object {
        /**
         * Represents the upper limit value.
         *
         * The `TO_HIGH` variable is a constant that holds the value 1000. It is used as an upper limit in various operations
         * where a maximum value needs to be defined.
         *
         * @since 1.0.0
         */
        const val TO_HIGH: Int = 1000

        /**
         * Represents the lower limit value.
         *
         * The TO_LOW constant is an integer value that defines the lower limit threshold.
         *
         * @since 1.0
         */
        const val TO_LOW: Int = 25
    }


}

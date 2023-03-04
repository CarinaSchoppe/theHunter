/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 8:53 PM All contents of "WorldboarderController.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.misc

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.game.Game
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

class WorldboarderController(private val game: Game) {

    var shrinkSpeed = 10
    var minBorderSize = 100
    var worldBoarderSize = 500
    var shrinkBoarder = true
    private lateinit var task: BukkitTask
    fun shrinkWorld() {
        if (!shrinkBoarder)
            return

        resetWorldBoarder()
        task = Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Runnable {
            if (game.arenaCenter!!.world.worldBorder.size - 1 >= minBorderSize) {
                game.arenaCenter!!.world.worldBorder.size -= 1
            } else {
                task.cancel()
            }
        }, (shrinkSpeed * 20).toLong(), (shrinkSpeed * 20).toLong())
    }

    fun resetWorldBoarder() {
        game.arenaCenter!!.world.worldBorder.center = game.arenaCenter!!
        game.arenaCenter!!.world.worldBorder.damageAmount = 100.0
        game.arenaCenter!!.world.worldBorder.size = worldBoarderSize.toDouble()
    }

    companion object {
        const val toHigh: Int = 1000
        const val toLow: Int = 25
    }


}

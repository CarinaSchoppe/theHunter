/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 16.04.22, 12:01 by Carina The Latest changes made by Carina on 16.04.22, 11:33 All contents of "WorldboarderController.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.misc

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.Game
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

class WorldboarderController(private val game: Game) {

    var shrinkSpeed = 10
    private val worldBoarder = game.arenaCenter!!.world.worldBorder
    var minBorderSize = 100
    var worldBoarderSize = 50000
    var shrinkBoarder = true
    lateinit var task: BukkitTask
    fun shrinkWorld() {
        if (!shrinkBoarder)
            return

        Bukkit.getScheduler().runTaskTimer(TheHunter.instance, Runnable {
            if (worldBoarder.size - shrinkSpeed.toDouble() >= minBorderSize) {
                worldBoarder.size -= shrinkSpeed.toDouble()
            } else {
                task.cancel()
            }
        }, (shrinkSpeed * 20).toLong(), (shrinkSpeed * 20).toLong())
    }

    fun resetWorldBoarder() {
        worldBoarder.center = game.arenaCenter!!
        worldBoarder.size = game.arenaRadius.toDouble()
    }


}
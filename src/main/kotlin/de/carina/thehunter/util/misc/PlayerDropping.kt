/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.04.22, 14:55 by Carina The Latest changes made by Carina on 21.04.22, 14:55 All contents of "PlayerDropping.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.misc

import de.carina.thehunter.util.game.Game
import org.bukkit.Location
import java.util.*

object PlayerDropping {

    fun dropPlayers(game: Game) {
        val locationCenter: Location = game.arenaCenter!!.subtract(game.worldBoarderController.worldBoarderSize / 2 - 1.0, 0.0, game.worldBoarderController.worldBoarderSize / 2 - 1.0)
        for (player in game.players) {
            val x = Random().nextInt(game.worldBoarderController.worldBoarderSize) + 0.0
            val z = Random().nextInt(game.worldBoarderController.worldBoarderSize) + 0.0
            player.teleport(locationCenter.add(x, 200.0, z))
            println("teleported to:" + x + " " + z)
        }
    }
}
/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "PlayerDropping.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.misc

import de.carina.thehunter.util.game.Game

object PlayerDropping {

    fun dropPlayers(game: Game) {
        if (!game.randomPlayerDrop) return
        for (player in game.players) {
            val x = Util.getRandomXYValueFromWorldBoarder(game)
            val y = Util.getRandomXYValueFromWorldBoarder(game)
            val location = game.arenaCenter!!.clone().add(x as Double, y as Double, 255.0)
            player.teleport(location)
        }
    }
}
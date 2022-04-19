/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 19:46 by Carina The Latest changes made by Carina on 19.04.22, 19:46 All contents of "PlayerDropping.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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
            val z = Util.getRandomXYValueFromWorldBoarder(game)
            val location = game.arenaCenter!!.clone().add(x + 0.0, 255.0, z + 0.0)
            //TODO: Fix  player.teleport(location)
        }
    }
}
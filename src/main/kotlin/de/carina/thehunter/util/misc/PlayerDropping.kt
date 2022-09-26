/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "PlayerDropping.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
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
        val locationCenter: Location = game.arenaCenter!!.clone().subtract((game.worldBoarderController.worldBoarderSize / 2).toDouble(), 0.0, (game.worldBoarderController.worldBoarderSize / 2).toDouble())
        for (player in game.players) {
            val x = Random().nextInt(game.worldBoarderController.worldBoarderSize).toDouble()
            val z = Random().nextInt(game.worldBoarderController.worldBoarderSize).toDouble()
            player.teleport(locationCenter.clone().add(x, 200.0, z))
        }
    }
}

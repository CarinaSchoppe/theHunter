/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "GamesHandler.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.game

import org.bukkit.entity.Entity
import org.bukkit.entity.Player

object GamesHandler {

    val games = mutableSetOf<Game>()
    val playerInGames = mutableMapOf<Player, Game>()
    val spectatorInGames = mutableMapOf<Player, Game>()
    val entitiesInGames = mutableMapOf<Entity, Game>()
    val setupGames = mutableSetOf<Game>()


}

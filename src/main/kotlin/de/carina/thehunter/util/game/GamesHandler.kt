/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 4/19/22, 12:56 PM All contents of "GamesHandler.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.game

import org.bukkit.entity.Entity
import org.bukkit.entity.Player

object GamesHandler {

    val games = mutableSetOf<Game>()
    val playerInGames = mutableMapOf<Player, Game>()
    val spectatorInGames = mutableMapOf<Player, Game>()
    val entitiesInGames = mutableMapOf<Entity, Game>()
    val setupGames = mutableSetOf<Game>()


}
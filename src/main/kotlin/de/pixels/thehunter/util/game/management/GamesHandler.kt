/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "GamesHandler.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.game.management

import org.bukkit.entity.Entity
import org.bukkit.entity.Player

object GamesHandler {

    /**
     * Represents a collection of games.
     *
     * The "games" variable is a mutable set that stores instances of the [Game] class. It allows adding,
     * removing, and querying different games in the collection.
     *
     * @property games The mutable set that holds instances of the [Game] class.
     */
    val games = mutableSetOf<Game>()

    /**
     * Represents a mapping of players to their associated games.
     *
     * The `playerInGames` variable is a mutable map that associates every `Player` object with the `Game` they are currently
     * participating in.
     *
     * Usage:
     * ```
     * val playerInGames = mutableMapOf<Player, Game>()
     * playerInGames[playerA] = gameX
     * playerInGames[playerB] = gameY
     * ```
     *
     * @property playerInGames The mutable map that associates `Player` objects with the corresponding `Game` objects.
     */
    val playerInGames = mutableMapOf<Player, Game>()

    /**
     * Tracks the mapping of players to games where they are spectating.
     */
    val spectatorInGames = mutableMapOf<Player, Game>()

    /**
     * Represents the mapping between entities and games.
     *
     * This mutable map stores the association between an `Entity` and the `Game`
     * it belongs to. It allows for efficient lookup and modification of the
     * mapping.
     *
     * The `Entity` class represents an object or character within a game, while
     * the `Game` class represents the game that the entities are part of.
     * The `entitiesInGames` map can be used to quickly find the game that an entity
     * belongs to or to determine the entities associated with a particular game.
     *
     * @property entitiesInGames The mutable map that maps `Entity` objects
     *                 to their associated `Game` objects.
     *
     * @see Entity
     * @see Game
     */
    val entitiesInGames = mutableMapOf<Entity, Game>()

    /**
     * Variable for setting up games.
     *
     * This variable is used to store a mutable set of Game objects.
     * It provides a convenient way to manage and manipulate a collection
     * of Game instances.
     *
     * @property setupGames The mutable set of Game instances.
     */
    val setupGames = mutableSetOf<Game>()


}

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:03 PM All contents of "Util.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.misc

import de.pixels.thehunter.util.game.management.Game
import org.bukkit.entity.Player

object Util {

    /**
     * Represents the currently selected game for each player.
     *
     * The `currentGameSelected` variable is a mutable map that associates each player with their selected game. It allows
     * for dynamically updating the game selection for each player.
     *
     * The keys of the map represent the players, and the values represent the games selected by each player.
     *
     * Example usage:
     *
     * ```
     * val player1 = Player("Alice")
     * val player2 = Player("Bob")
     *
     * val currentGameSelected = mutableMapOf<Player, Game>()
     *
     * currentGameSelected[player1] = Game("Chess")
     * currentGameSelected[player2] = Game("Monopoly")
     *
     * println(currentGameSelected[player1]) // Output: Game(name="Chess")
     * println(currentGameSelected[player2]) // Output: Game(name="Monopoly")
     * ```
     */
    var currentGameSelected = mutableMapOf<Player, Game>()

}

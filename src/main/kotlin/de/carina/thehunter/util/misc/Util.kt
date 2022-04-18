/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 00:42 by Carina The Latest changes made by Carina on 19.04.22, 00:42 All contents of "Util.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.misc

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.EndState
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.gamestates.LobbyState
import de.carina.thehunter.util.game.Game
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import java.util.*

object Util {
    fun getRandomXYValueFromWorldBoarder(game: Game): Int {
        return Random().nextInt(game.worldBoarderController.worldBoarderSize * 2) - game.worldBoarderController.worldBoarderSize
    }

    fun updateGameSigns(game: Game) {
        for (sign in game.signs) {
            sign.line(0, LegacyComponentSerializer.legacySection().deserialize(TheHunter.PREFIX))
            sign.line(1, LegacyComponentSerializer.legacySection().deserialize(game.name))
            if (game.currentGameState is LobbyState) {
                sign.line(3, LegacyComponentSerializer.legacySection().deserialize("§aLobby"))
                if (game.players.size < game.maxPlayers) sign.line(2, LegacyComponentSerializer.legacySection().deserialize("§7[§6" + game.players.size + " §7|§6" + game.maxPlayers + "§7]")) else sign.line(2, LegacyComponentSerializer.legacySection().deserialize("§7[§c" + game.players.size + " §7|§c" + game.maxPlayers + "§7]"))
            } else if (game.currentGameState is IngameState) sign.line(2, LegacyComponentSerializer.legacySection().deserialize("§6RUNNING")) else if (game.currentGameState is EndState) {
                sign.line(2, LegacyComponentSerializer.legacySection().deserialize("§cENDING"))
                sign.line(3, LegacyComponentSerializer.legacySection().deserialize("§aReady Restart"))
            }
            sign.update(true)
        }
    }
}
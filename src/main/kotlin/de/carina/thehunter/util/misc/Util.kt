package de.carina.thehunter.util.misc

import de.carina.thehunter.util.game.Game
import java.util.*

object Util {
    fun getRandomXYValueFromWorldBoarder(game: Game): Int {
        return Random().nextInt(game.worldBoarderController.worldBoarderSize * 2) - game.worldBoarderController.worldBoarderSize
    }
}
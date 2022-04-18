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
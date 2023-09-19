package de.pixels.thehunter.events.ingame.perks

import de.pixels.thehunter.util.game.management.Game
import org.bukkit.entity.Player

class PigPerk(game: Game, cost: Int, active: Boolean, name: String, description: String) : BasePerk(game, cost, active, name, description) {
    override fun execute() {
        TODO("Not yet implemented")
    }

    override fun givePerk(player: Player) {
        TODO("Not yet implemented")
    }

    override fun removePerk(player: Player) {
        TODO("Not yet implemented")
    }
}

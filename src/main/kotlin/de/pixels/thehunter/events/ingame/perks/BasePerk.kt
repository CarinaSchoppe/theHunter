package de.pixels.thehunter.events.ingame.perks

import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.misc.PerkAction
import org.bukkit.entity.Player

abstract class BasePerk(val game: Game, val cost: Int, val active: Boolean, val name: String, val description: String, val players: MutableSet<Player> = mutableSetOf()) : PerkAction {
    /**
     * Executes the specified functionality.
     */
    abstract override fun execute()

    /**
     * Gives a perk to the specified player.
     *
     * @param player The player to give the perk to.
     */
    abstract fun givePerk(player: Player)

    /**
     * Removes a perk from the given player.
     *
     * @param player The player from which the perk should be removed.
     */
    abstract fun removePerk(player: Player)
}

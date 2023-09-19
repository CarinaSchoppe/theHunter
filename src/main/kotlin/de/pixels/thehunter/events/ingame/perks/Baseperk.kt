package de.pixels.thehunter.events.ingame.perks

import de.pixels.thehunter.util.game.ingame.perksystem.PerkAction
import de.pixels.thehunter.util.game.management.Game

abstract class Baseperk(val game: Game, val cost: Int, val active: Boolean, val name: String, val description: String) : PerkAction {
}

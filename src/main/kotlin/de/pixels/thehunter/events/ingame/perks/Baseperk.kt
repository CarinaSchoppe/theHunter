package de.pixels.thehunter.events.ingame.perks

import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.misc.PerkAction

abstract class Baseperk(val game: Game, val cost: Int, val active: Boolean, val name: String, val description: String) : PerkAction

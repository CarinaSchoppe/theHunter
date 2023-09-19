package de.pixels.thehunter.util.game.ingame.perksystem

class Perkmanager {

    val perks = ArrayList<Perk>()

    companion object {
        lateinit var instance: Perkmanager

        fun loadPerksFromFile() {

        }
    }


    private constructor() {
        instance = this
    }

    fun getInstance(): Perkmanager {
        return instance
    }


}

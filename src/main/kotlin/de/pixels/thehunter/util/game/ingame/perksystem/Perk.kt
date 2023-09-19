package de.pixels.thehunter.util.game.ingame.perksystem

enum class Perk {

    KANGAROO("kangoorooCost"),
    BAT("batCost"),
    BEAR("bearCost"),
    SONIC("sonicCost"),
    BACKPACKER("backpackerCost"),
    BLOODHOUND("bloodhoundCost"),
    BOMBERMAN("bombermanCost"),
    NINJA("ninjaCost"),
    PIG("pigCost"),
    PIRATE("pirateCost"),
    GAMBLER("gamblerCost");

    var cost: Int = 10000

    constructor(cost: String) {
        this.cost = cost.toInt()
    }

    constructor(cost: Int) {
        this.cost = cost
    }


}

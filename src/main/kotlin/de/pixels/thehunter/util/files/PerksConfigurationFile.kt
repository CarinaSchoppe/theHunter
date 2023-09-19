package de.pixels.thehunter.util.files

import de.pixels.thehunter.util.game.ingame.perksystem.Perk

class PerksConfigurationFile(filePath: String) : BaseFile(filePath) {


    override fun addData(): PerksConfigurationFile {
        yml.addDefault(Perk.BACKPACKER.path.plus(".name"), "Backpacker")
        yml.addDefault(Perk.BACKPACKER.path.plus(".description"), "You can carry more items in your inventory.")
        yml.addDefault(Perk.BACKPACKER.path.plus(".cost"), 1000)
        yml.addDefault(Perk.BACKPACKER.path.plus(".active"), true)

        yml.addDefault(Perk.BAT.path.plus(".name"), "Bat")
        yml.addDefault(Perk.BAT.path.plus(".description"), "You can fly for a short time.")
        yml.addDefault(Perk.BAT.path.plus(".cost"), 1000)
        yml.addDefault(Perk.BAT.path.plus(".active"), true)

        yml.addDefault(Perk.BEAR.path.plus(".name"), "Bear")
        yml.addDefault(Perk.BEAR.path.plus(".description"), "You can take less damage from other players.")
        yml.addDefault(Perk.BEAR.path.plus(".cost"), 1000)
        yml.addDefault(Perk.BEAR.path.plus(".active"), true)

        yml.addDefault(Perk.BLOODHOUND.path.plus(".name"), "Bloodhound")
        yml.addDefault(Perk.BLOODHOUND.path.plus(".description"), "You can see the footsteps of other players.")
        yml.addDefault(Perk.BLOODHOUND.path.plus(".cost"), 1000)
        yml.addDefault(Perk.BLOODHOUND.path.plus(".active"), true)

        yml.addDefault(Perk.BOMBERMAN.path.plus(".name"), "Bomberman")
        yml.addDefault(Perk.BOMBERMAN.path.plus(".description"), "You can throw grenades.")
        yml.addDefault(Perk.BOMBERMAN.path.plus(".cost"), 1000)
        yml.addDefault(Perk.BOMBERMAN.path.plus(".active"), true)

        yml.addDefault(Perk.GAMBLER.path.plus(".name"), "Gambler")
        yml.addDefault(Perk.GAMBLER.path.plus(".description"), "You can gamble for items.")
        yml.addDefault(Perk.GAMBLER.path.plus(".cost"), 1000)
        yml.addDefault(Perk.GAMBLER.path.plus(".active"), true)

        yml.addDefault(Perk.KANGAROO.path.plus(".name"), "Kangaroo")
        yml.addDefault(Perk.KANGAROO.path.plus(".description"), "You can jump higher.")
        yml.addDefault(Perk.KANGAROO.path.plus(".cost"), 1000)
        yml.addDefault(Perk.KANGAROO.path.plus(".active"), true)

        yml.addDefault(Perk.NINJA.path.plus(".name"), "Ninja")
        yml.addDefault(Perk.NINJA.path.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(Perk.NINJA.path.plus(".cost"), 1000)
        yml.addDefault(Perk.NINJA.path.plus(".active"), true)

        yml.addDefault(Perk.PIG.path.plus(".name"), "Pig")
        yml.addDefault(Perk.PIG.path.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(Perk.PIG.path.plus(".cost"), 1000)
        yml.addDefault(Perk.PIG.path.plus(".active"), true)

        yml.addDefault(Perk.PIRATE.path.plus(".name"), "Pirate")
        yml.addDefault(Perk.PIRATE.path.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(Perk.PIRATE.path.plus(".cost"), 1000)
        yml.addDefault(Perk.PIRATE.path.plus(".active"), true)

        yml.addDefault(Perk.SONIC.path.plus(".name"), "Sonic")
        yml.addDefault(Perk.SONIC.path.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(Perk.SONIC.path.plus(".cost"), 1000)
        yml.addDefault(Perk.SONIC.path.plus(".active"), true)

        yml.addDefault(Perk.FEATHER.path.plus(".name"), "Feather")
        yml.addDefault(Perk.FEATHER.path.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(Perk.FEATHER.path.plus(".cost"), 1000)
        yml.addDefault(Perk.FEATHER.path.plus(".active"), true)

        super.addData()
        return this
    }
}

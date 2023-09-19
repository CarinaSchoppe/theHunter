package de.pixels.thehunter.util.game.management

import de.pixels.thehunter.events.ingame.perks.*
import de.pixels.thehunter.util.files.BaseFile
import de.pixels.thehunter.util.misc.AvailablePerks

class PerkSettings(val game: Game) : BaseFile("/arenas/${game.name}/perk-settings.yml") {
    /**
     * Variable to store the settings map.
     *
     * This variable is a mutable map that stores key-value pairs, where the key is of type String and the value is of type Baseperk.
     * It is used to store various settings or configurations in the form of key-value pairs.
     */
    val settingsMap = mutableMapOf<String, BasePerk>()


    val availablePerks = mutableMapOf<AvailablePerks, BasePerk>()

    /**
     * Fills the settings map with available perks.
     */
    private fun fillSettingsMap() {
        for (key in yml.getKeys(false)) {
            settingsMap[key] =
                when (key) {
                    AvailablePerks.KANGAROO.pathName -> {
                        val perk = KangarooPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.KANGAROO] = perk
                        perk
                    }

                    AvailablePerks.BAT.pathName -> {
                        val perk = BatPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.BAT] = perk
                        perk

                    }

                    AvailablePerks.ANGEL.pathName -> {
                        val perk = AngelPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.ANGEL] = perk
                        perk
                    }

                    AvailablePerks.BACKPACKER.pathName -> {
                        val perk = BackpackerPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.BACKPACKER] = perk
                        perk
                    }

                    AvailablePerks.PIG.pathName -> {
                        val perk = PigPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.PIG] = perk
                        perk
                    }

                    AvailablePerks.BEAR.pathName -> {
                        val perk = BearPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.BEAR] = perk
                        perk
                    }

                    AvailablePerks.BLOODHOUND.pathName -> {
                        val perk = BloodhoundPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.BLOODHOUND] = perk
                        perk
                    }

                    AvailablePerks.BOMBERMAN.pathName -> {
                        val perk = BombermanPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.BOMBERMAN] = perk
                        perk
                    }

                    AvailablePerks.GAMBLER.pathName -> {
                        val perk = GamblerPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.GAMBLER] = perk
                        perk
                    }

                    AvailablePerks.NINJA.pathName -> {
                        val perk = NinjaPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.NINJA] = perk
                        perk
                    }

                    AvailablePerks.PIRATE.pathName -> {
                        val perk = PiratePerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.PIRATE] = perk
                        perk
                    }

                    AvailablePerks.SONIC.pathName -> {
                        val perk = SonicPerk(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        availablePerks[AvailablePerks.SONIC] = perk
                        perk 
                    }

                    else -> throw IllegalArgumentException("Unknown perk: $key")
                } 


        }
    }


    /**
     * Adds default data for PerkSettings.
     *
     * @return PerkSettings - The updated PerkSettings object.
     */
    override fun addData(): PerkSettings {
        yml.addDefault(AvailablePerks.BACKPACKER.pathName.plus(".name"), AvailablePerks.BACKPACKER.pathName)
        yml.addDefault(AvailablePerks.BACKPACKER.pathName.plus(".description"), "You can carry more items in your inventory.")
        yml.addDefault(AvailablePerks.BACKPACKER.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.BACKPACKER.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.BAT.pathName.plus(".name"), AvailablePerks.BAT.pathName)
        yml.addDefault(AvailablePerks.BAT.pathName.plus(".description"), "You can fly for a short time.")
        yml.addDefault(AvailablePerks.BAT.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.BAT.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.BEAR.pathName.plus(".name"), AvailablePerks.BEAR.pathName)
        yml.addDefault(AvailablePerks.BEAR.pathName.plus(".description"), "You can take less damage from other players.")
        yml.addDefault(AvailablePerks.BEAR.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.BEAR.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.BLOODHOUND.pathName.plus(".name"), AvailablePerks.BLOODHOUND.pathName)
        yml.addDefault(AvailablePerks.BLOODHOUND.pathName.plus(".description"), "You can see the footsteps of other players.")
        yml.addDefault(AvailablePerks.BLOODHOUND.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.BLOODHOUND.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.BOMBERMAN.pathName.plus(".name"), AvailablePerks.BOMBERMAN.pathName)
        yml.addDefault(AvailablePerks.BOMBERMAN.pathName.plus(".description"), "You can throw grenades.")
        yml.addDefault(AvailablePerks.BOMBERMAN.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.BOMBERMAN.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.GAMBLER.pathName.plus(".name"), AvailablePerks.GAMBLER.pathName)
        yml.addDefault(AvailablePerks.GAMBLER.pathName.plus(".description"), "You can gamble for items.")
        yml.addDefault(AvailablePerks.GAMBLER.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.GAMBLER.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.KANGAROO.pathName.plus(".name"), AvailablePerks.KANGAROO.pathName)
        yml.addDefault(AvailablePerks.KANGAROO.pathName.plus(".description"), "You can jump higher.")
        yml.addDefault(AvailablePerks.KANGAROO.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.KANGAROO.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.NINJA.pathName.plus(".name"), AvailablePerks.NINJA.pathName)
        yml.addDefault(AvailablePerks.NINJA.pathName.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(AvailablePerks.NINJA.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.NINJA.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.PIG.pathName.plus(".name"), AvailablePerks.PIG.pathName)
        yml.addDefault(AvailablePerks.PIG.pathName.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(AvailablePerks.PIG.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.PIG.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.PIRATE.pathName.plus(".name"), AvailablePerks.PIRATE.pathName)
        yml.addDefault(AvailablePerks.PIRATE.pathName.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(AvailablePerks.PIRATE.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.PIRATE.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.SONIC.pathName.plus(".name"), AvailablePerks.SONIC.pathName)
        yml.addDefault(AvailablePerks.SONIC.pathName.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(AvailablePerks.SONIC.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.SONIC.pathName.plus(".active"), true)

        yml.addDefault(AvailablePerks.ANGEL.pathName.plus(".name"), AvailablePerks.ANGEL.pathName)
        yml.addDefault(AvailablePerks.ANGEL.pathName.plus(".description"), "You will not get any fall-damage.")
        yml.addDefault(AvailablePerks.ANGEL.pathName.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.ANGEL.pathName.plus(".active"), true)
        super.addData()
        fillSettingsMap()
        return this
    }
}

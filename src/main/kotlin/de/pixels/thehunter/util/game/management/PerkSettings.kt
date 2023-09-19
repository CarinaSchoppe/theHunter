package de.pixels.thehunter.util.game.management

import de.pixels.thehunter.events.ingame.perks.*
import de.pixels.thehunter.util.files.BaseFile
import de.pixels.thehunter.util.game.ingame.perksystem.AvailablePerks

class PerkSettings(val game: Game) : BaseFile("/arenas/${game.name}/perk-settings.yml") {
    /**
     * Variable to store the settings map.
     *
     * This variable is a mutable map that stores key-value pairs, where the key is of type String and the value is of type Baseperk.
     * It is used to store various settings or configurations in the form of key-value pairs.
     */
    val settingsMap = mutableMapOf<String, Baseperk>()

    /**
     * Fills the settings map with available perks.
     */
    private fun fillSettingsMap() {
        for (key in yml.getKeys(false)) {
            settingsMap[key] =
                when (key) {
                    AvailablePerks.KANGAROO.pathName -> Kangaroo(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.BAT.pathName -> Bat(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.ANGEL.pathName -> Angel(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.BACKPACKER.pathName -> Backpacker(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.PIG.pathName -> Pig(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.BEAR.pathName -> Bear(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.BLOODHOUND.pathName -> Bloodhound(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.BOMBERMAN.pathName -> Bomberman(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.GAMBLER.pathName -> Gambler(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.NINJA.pathName -> Ninja(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.PIRATE.pathName -> Pirate(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.SONIC.pathName -> Sonic(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    else -> throw IllegalArgumentException("Perk not found")
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

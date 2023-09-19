package de.pixels.thehunter.util.game.management

import de.pixels.thehunter.events.ingame.perks.*
import de.pixels.thehunter.util.files.BaseFile
import de.pixels.thehunter.util.game.ingame.perksystem.AvailablePerks

class PerkSettings(val game: Game) : BaseFile("/arenas/${game.name}/perk-settings.yml") {
    val settingsMap = mutableMapOf<String, Baseperk>()
    private fun fillSettingsMap() {
        for (key in yml.getKeys(false)) {
            settingsMap[key] = 
                when(key){
                  
                   AvailablePerks.KANGAROO.name ->                        Kangaroo(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.BAT.name -> Bat(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.ANGEL.name -> Angel(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.BACKPACKER.name -> Backpacker(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.PIG.name -> Pig(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.BEAR.name -> Bear(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                  AvailablePerks.BLOODHOUND.name -> Bloodhound(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    AvailablePerks.BOMBERMAN.name -> Bomberman(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                     AvailablePerks.GAMBLER.name -> Gambler(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        AvailablePerks.NINJA.name -> Ninja(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        AvailablePerks.PIRATE.name -> Pirate(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                        AvailablePerks.SONIC.name -> Sonic(game, yml.getInt(key.plus(".cost")), yml.getBoolean(key.plus(".active")), yml.getString(key.plus(".name"))!!, yml.getString(key.plus(".description"))!!)
                    
                  
                  
                    else -> throw IllegalArgumentException("Perk not found")
            }
        }
    }
    
    
    override fun addData(): PerkSettings {
        yml.addDefault(AvailablePerks.BACKPACKER.path.plus(".name"), AvailablePerks.BACKPACKER.name)
        yml.addDefault(AvailablePerks.BACKPACKER.path.plus(".description"), "You can carry more items in your inventory.")
        yml.addDefault(AvailablePerks.BACKPACKER.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.BACKPACKER.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.BAT.path.plus(".name"), AvailablePerks.BAT.name)
        yml.addDefault(AvailablePerks.BAT.path.plus(".description"), "You can fly for a short time.")
        yml.addDefault(AvailablePerks.BAT.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.BAT.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.BEAR.path.plus(".name"), AvailablePerks.BEAR.name)
        yml.addDefault(AvailablePerks.BEAR.path.plus(".description"), "You can take less damage from other players.")
        yml.addDefault(AvailablePerks.BEAR.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.BEAR.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.BLOODHOUND.path.plus(".name"), AvailablePerks.BLOODHOUND.name)
        yml.addDefault(AvailablePerks.BLOODHOUND.path.plus(".description"), "You can see the footsteps of other players.")
        yml.addDefault(AvailablePerks.BLOODHOUND.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.BLOODHOUND.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.BOMBERMAN.path.plus(".name"), AvailablePerks.BOMBERMAN.name)
        yml.addDefault(AvailablePerks.BOMBERMAN.path.plus(".description"), "You can throw grenades.")
        yml.addDefault(AvailablePerks.BOMBERMAN.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.BOMBERMAN.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.GAMBLER.path.plus(".name"), AvailablePerks.GAMBLER.name)
        yml.addDefault(AvailablePerks.GAMBLER.path.plus(".description"), "You can gamble for items.")
        yml.addDefault(AvailablePerks.GAMBLER.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.GAMBLER.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.KANGAROO.path.plus(".name"), AvailablePerks.KANGAROO.name)
        yml.addDefault(AvailablePerks.KANGAROO.path.plus(".description"), "You can jump higher.")
        yml.addDefault(AvailablePerks.KANGAROO.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.KANGAROO.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.NINJA.path.plus(".name"), AvailablePerks.NINJA.name)
        yml.addDefault(AvailablePerks.NINJA.path.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(AvailablePerks.NINJA.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.NINJA.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.PIG.path.plus(".name"), AvailablePerks.PIG.name)
        yml.addDefault(AvailablePerks.PIG.path.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(AvailablePerks.PIG.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.PIG.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.PIRATE.path.plus(".name"), AvailablePerks.PIRATE.name)
        yml.addDefault(AvailablePerks.PIRATE.path.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(AvailablePerks.PIRATE.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.PIRATE.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.SONIC.path.plus(".name"), AvailablePerks.SONIC.name)
        yml.addDefault(AvailablePerks.SONIC.path.plus(".description"), "You can become invisible for a short time.")
        yml.addDefault(AvailablePerks.SONIC.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.SONIC.path.plus(".active"), true)

        yml.addDefault(AvailablePerks.ANGEL.path.plus(".name"), AvailablePerks.ANGEL.name)
        yml.addDefault(AvailablePerks.ANGEL.path.plus(".description"), "You will not get any fall-damage.")
        yml.addDefault(AvailablePerks.ANGEL.path.plus(".cost"), 1000)
        yml.addDefault(AvailablePerks.ANGEL.path.plus(".active"), true)

        super.addData()
        return this
    }
}

package de.pixels.thehunter.util.game.ingame.perksystem

class Perkmanager {

    val perks = ArrayList<Perk>()

    companion object {
        lateinit var instance: Perkmanager

        fun loadPerksFromFile() {
            val file = File("plugins/TheHunter/perks.yml")
            if (!file.exists()) {
                file.createNewFile()
            }
            val config = YamlConfiguration.loadConfiguration(file)
            for (perk in Perk.values()) {
                if (!config.contains(perk.name)) {
                    config.set(perk.name, perk.cost)
                }
            }
            config.save(file)
        }
    }


    private constructor() {
        instance = this
    }

    fun getInstance(): Perkmanager {
        return instance
    }


}

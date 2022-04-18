package de.carina.thehunter.util.files

class ItemSettings : BaseFile("item-settings.yml") {


    val settingsMap = mutableMapOf<String, Any>()

    private fun fillSettingsMap() {
        for (key in yml.getKeys(false)) {
            settingsMap[key] = yml.get(key) as Any
        }
    }

    override fun addData(): ItemSettings {

        yml.addDefault("egg-bomb-amount", 4)
        yml.addDefault("egg-bomb-radius", 5)
        yml.addDefault("egg-bomb-delay", 1)
        yml.addDefault("knife-damage", 2.0)
        yml.addDefault("healer-amount", 5)
        yml.addDefault("eye-spy-duration", 5)
        yml.options().copyDefaults(true)
        yml.save(file)
        fillSettingsMap()
        return this
    }
}
/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 9:23 PM All contents of "ItemSettings.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.game.management

import de.pixels.thehunter.util.files.BaseFile

class ItemSettings(val game: Game) : BaseFile("/arenas/${game.name}/item-settings.yml") {

    /**
     * This variable, 'settingsMap', is a mutable map containing key-value pairs.
     * The keys are of type 'String', representing the name of the setting, and the values can be of any type.
     *
     * This map can be used to store and retrieve various settings and their corresponding values dynamically at runtime.
     * The values can be accessed, modified, and removed using standard map operations.
     *
     * Example usage:
     * val settingsMap = mutableMapOf<String, Any>()
     *
     * // Adding a setting to the map
     * settingsMap["settingName"] = "settingValue"
     *
     * // Retrieving a setting from the map
     * val value = settingsMap["settingName"]
     *
     * // Modifying a setting in the map
     * settingsMap["settingName"] = newValue
     *
     * // Removing a setting from the map
     * settingsMap.remove("settingName")
     */
    val settingsMap = mutableMapOf<String, Any>()

    /**
     * Populates the settings map with key-value pairs from the YAML configuration.
     */
    private fun fillSettingsMap() {
        for (key in yml.getKeys(false)) {
            settingsMap[key] = yml[key] as Any
        }
    }


    /**
     * Adds default data for ItemSettings.
     *
     * @return The updated ItemSettings with default data added.
     */
    override fun addData(): ItemSettings {
        yml.addDefault("egg-bomb-amount", 4)
        yml.addDefault("egg-bomb-radius", 5)
        yml.addDefault("egg-bomb-delay", 1)
        yml.addDefault("knife-damage", 2.0)
        yml.addDefault("healer-amount", 5)
        yml.addDefault("eye-spy-duration", 5)
        super.addData()
        fillSettingsMap()
        return this
    }
}

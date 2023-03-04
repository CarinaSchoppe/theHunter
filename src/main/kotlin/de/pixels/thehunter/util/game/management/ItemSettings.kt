/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 9:23 PM All contents of "ItemSettings.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.game.management

import de.pixels.thehunter.util.files.BaseFile

class ItemSettings(val game: Game) : BaseFile("/arenas/${game.name}/item-settings.yml") {

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
        super.addData()
        fillSettingsMap()
        return this
    }
}

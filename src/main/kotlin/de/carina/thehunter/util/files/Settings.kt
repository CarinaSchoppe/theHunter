/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 13.04.22, 22:09 by Carina The Latest changes made by Carina on 13.04.22, 22:09 All contents of "Settings.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.files

class Settings(filePath: String) : BaseFile(filePath) {


    val settingsMap = mutableMapOf<String, Object>()

    private fun fillSettingsMap() {
        for (key in yml.getKeys(false)) {
            settingsMap[key] = yml.get(key) as Object
        }
    }

    override fun addData() {
        yml.addDefault("prefix", "&7[&bTheHunter&7] &f")
        yml.addDefault("debug", false)
        yml.addDefault("duration-lobby", 60)
        yml.addDefault("duration-speedup", 5)
        yml.addDefault("duration-idle", 10)
        super.addData()
        fillSettingsMap()
    }
}
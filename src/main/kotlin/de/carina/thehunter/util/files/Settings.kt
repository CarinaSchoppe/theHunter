/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 21.04.22, 15:42 by Carina The Latest changes made by Carina on 21.04.22, 15:42 All contents of "Settings.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.files

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.ChatColor

class Settings(filePath: String) : BaseFile(filePath) {


    val settingsMap = mutableMapOf<String, Any>()

    private fun fillSettingsMap() {
        for (key in yml.getKeys(false)) {
            settingsMap[key] = yml.get(key) as Any
        }
        Bukkit.getConsoleSender().sendMessage(LegacyComponentSerializer.legacySection().deserialize(ChatColor.translateAlternateColorCodes('&', settingsMap["prefix"] as String) + "§aSettings loaded"))
    }

    override fun addData(): Settings {
        yml.addDefault("prefix", "&7[&bTheHunter&7] &f")
        yml.addDefault("debug", false)
        yml.addDefault("duration-lobby", 60)
        yml.addDefault("duration-endcountdown", 10)
        yml.addDefault("duration-speedup", 5)
        yml.addDefault("duration-idle", 10)
        yml.addDefault("server-name", "TheHunter")
        yml.addDefault("ts-ip", "localhost")
        yml.addDefault("autoupdater", true)
        super.addData()
        fillSettingsMap()
        return this
    }
}
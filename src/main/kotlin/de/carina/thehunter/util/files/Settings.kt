/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "Settings.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.files

import de.carina.thehunter.util.misc.ConstantStrings
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
        yml.addDefault("scoreboard-enable", true)
        yml.addDefault("mysql", true)
        yml.addDefault("sqlite-enable", true)
        yml.addDefault(ConstantStrings.SQLITE_PATH, "plugins/TheHunter/sqlite.db")
        yml.addDefault("mysql-host", "localhost")
        yml.addDefault("mysql-port", 3306)
        yml.addDefault("mysql-database", "thehunter")
        yml.addDefault("mysql-user", "root")
        yml.addDefault("mysql-password", "")
        yml.addDefault("server-name", "TheHunter")
        yml.addDefault("ts-ip", "localhost")
        yml.addDefault("updater", true)
        super.addData()
        fillSettingsMap()
        return this
    }
}

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 11:00 PM All contents of "Settings.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.files

import de.pixels.thehunter.util.misc.ConstantStrings
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.ChatColor

class SettingsFile(filePath: String) : BaseFile(filePath) {


    /**
     * Represents a settings map containing key-value pairs.
     *
     * This mutable map is used to store various settings and their corresponding values. The keys are
     * of type [String], and the values can be of any type.
     *
     * Use the [settingsMap] variable to access and modify the settings.
     *
     * @since 1.0.0
     */
    val settingsMap = mutableMapOf<String, Any>()

    /**
     * Fill the settings map with the values from the yml file.
     *
     * The settings map is filled by iterating through the keys in the yml file and adding them as key-value pairs
     * in the settings map. The key is the key from the yml file, and the value is the corresponding value stored in
     * the yml file.
     *
     * After filling the settings map, a message is sent to the console indicating that the settings have been loaded.
     *
     * Note: The yml file must be loaded before calling this method.
     */
    private fun fillSettingsMap() {
        for (key in yml.getKeys(false)) {
            settingsMap[key] = yml[key] as Any
        }
        Bukkit.getConsoleSender().sendMessage(
            LegacyComponentSerializer.legacySection().deserialize(
                ChatColor.translateAlternateColorCodes(
                    '&',
                    settingsMap["prefix"] as String
                ) + "§aSettings loaded"
            )
        )
    }

    /**
     * Adds default data to the settings YAML file.
     *
     * @return The updated Settings object.
     */
    override fun addData(): SettingsFile {
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

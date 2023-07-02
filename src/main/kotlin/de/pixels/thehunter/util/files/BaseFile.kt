/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/4/22, 10:36 PM All contents of "BaseFile.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.files

import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

open class BaseFile(filePath: String) {

    /**
     * Represents a YAML configuration file.
     *
     * @property yml The YAML configuration object.
     */
    val yml: YamlConfiguration

    /**
     * Represents a file on the system.
     *
     * @property file The underlying File object.
     * @constructor Creates a new instance of the File class with the specified File object.
     */
    val file: File

    /**
     * The Companion class contains a single companion object that holds constants related to the game configuration.
     *
     * @property GAME_FOLDER The folder path where the game is installed. This is a constant value that should not be modified.
     */
    companion object {
        const val GAME_FOLDER = "plugins/theHunterRemastered"
    }

    init {
        file = File("$GAME_FOLDER/$filePath")
        yml = YamlConfiguration.loadConfiguration(file)
    }

    /**
     * Saves the YAML configuration file.
     * If the file does not exist, it will be created with default options.
     * If the file already exists, it will be overwritten with the current options.
     *
     * @throws Exception if an error occurs while saving the file.
     */
    private fun saveFile() {
        try {
            yml.options().copyDefaults(true)
            yml.save(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Adds data to the BaseFile object.
     */
    open fun addData(): BaseFile {
        saveFile()
        return this
    }

    /**
     * Returns the color-coded string corresponding to the given path.
     *
     * @param path the path of the string in the YAML configuration
     * @return the color-coded string, or an empty string if not found
     */
    fun getColorCodedString(path: String): String {
        return ChatColor.translateAlternateColorCodes('&', yml.getString(path) ?: "")
    }


}

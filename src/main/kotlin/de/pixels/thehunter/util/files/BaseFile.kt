/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/4/22, 10:36 PM All contents of "BaseFile.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.files

import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

open class BaseFile(filePath: String) {

    val yml: YamlConfiguration
    val file: File

    companion object {
        const val GAME_FOLDER = "plugins/theHunterRemastered"
    }

    init {
        file = File("$GAME_FOLDER/$filePath")
        yml = YamlConfiguration.loadConfiguration(file)
    }

    private fun saveFile() {
        try {
            yml.options().copyDefaults(true)
            yml.save(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun addData(): BaseFile {
        saveFile()
        return this
    }

    fun getColorCodedString(path: String): String {
        return ChatColor.translateAlternateColorCodes('&', yml.getString(path) ?: "")
    }


}

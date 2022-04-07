/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 07.04.22, 15:06 by Carina The Latest changes made by Carina on 07.04.22, 15:06 All contents of "BaseFile.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.files

import de.carina.thehunter.TheHunter
import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

open class BaseFile(val filePath: String) {

    val yml: YamlConfiguration
    private val file: File
    private val gameFolder = "theHunterRemastered"

    init {
        file = File("plugins/$gameFolder/$filePath")
        yml = YamlConfiguration.loadConfiguration(file)
    }

    fun saveFile() {
        try {
            yml.options().copyDefaults(true)
            yml.save(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    open fun addData() {
        saveFile()
    }

    fun getColorCodedString(path: String): String {
        return ChatColor.translateAlternateColorCodes('&', yml.getString(path) ?: "")
    }

    fun getMessageWithPrefix(path: String): String {
        return TheHunter.PREFIX + getColorCodedString(path)

    }


}
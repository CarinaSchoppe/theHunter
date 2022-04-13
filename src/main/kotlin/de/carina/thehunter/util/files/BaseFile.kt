/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 14.04.22, 00:26 by Carina The Latest changes made by Carina on 14.04.22, 00:26 All contents of "BaseFile.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.files

import org.bukkit.ChatColor
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

open class BaseFile(filePath: String) {

    protected val yml: YamlConfiguration
    private val file: File

    companion object {
        const val gameFolder = "/plugins/theHunterRemastered"
    }

    init {
        file = File("$gameFolder/$filePath")
        yml = YamlConfiguration.loadConfiguration(file)
    }

    protected fun saveFile() {
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
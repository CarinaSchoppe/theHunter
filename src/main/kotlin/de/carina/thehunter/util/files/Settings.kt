/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 07.04.22, 23:06 by Carina The Latest changes made by Carina on 07.04.22, 23:06 All contents of "Settings.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.files

class Settings(filePath: String) : BaseFile(filePath) {

    override fun addData() {

        yml.addDefault("prefix", "&7[&bTheHunter&7] &f")
        yml.addDefault("debug", false)
        yml.addDefault("duration-lobby", 60)
        yml.addDefault("duration-speedup", 5)
        yml.addDefault("duration-idle", 10)
        super.addData()
    }
}
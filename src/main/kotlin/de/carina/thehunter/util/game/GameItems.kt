/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "GameItems.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.game

import de.carina.thehunter.util.files.BaseFile
import de.carina.thehunter.util.misc.ConstantStrings
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class GameItems(game: Game) {

    private val fileItems = File("${BaseFile.gameFolder}/arenas/${game.name}/items.yml")
    private val ymlItems = YamlConfiguration.loadConfiguration(fileItems)
    private val fileGuns = File("${BaseFile.gameFolder}/arenas/${game.name}/guns.yml")
    private val ymlGuns = YamlConfiguration.loadConfiguration(fileGuns)
    val items = mutableMapOf<String, Any>()
    val guns = mutableMapOf<String, Int>()

    fun loadAllItems() {
        for (item in ymlItems.getKeys(false)) {
            items[item] = ymlItems.get(item) as Any
        }
    }

    fun loadAllGunSettings() {
        for (gun in ymlGuns.getKeys(false)) {
            guns[gun] = ymlGuns.getInt(gun)
        }
    }

    fun saveAllItems() {
        ymlItems.addDefault("item-amounts", 10)
        ymlItems.addDefault("EyeSpy", true)
        ymlItems.addDefault("eyespy-amount", 2)
        ymlItems.addDefault("Healer", true)
        ymlItems.addDefault("healer-amount", 3)
        ymlItems.addDefault("Knife", true)
        ymlItems.addDefault("knife-amount", 1)
        ymlItems.addDefault("EggBomb", true)
        ymlItems.addDefault("eggbomb-amount", 2)
        ymlItems.addDefault("JumpStick", true)
        ymlItems.addDefault("jump-stick-amount", 2)
        ymlItems.addDefault("MinigunAmmo", true)
        ymlItems.addDefault("minigunammo-amount", 10)
        ymlItems.addDefault("AkAmmo", true)
        ymlItems.addDefault("akammo-amount", 6)
        ymlItems.addDefault("SniperAmmo", true)
        ymlItems.addDefault("sniperammo-amount", 2)
        ymlItems.addDefault("PistolAmmo", true)
        ymlItems.addDefault("pistolammo-amount", 3)
        ymlItems.addDefault("Tracker", true)
        ymlItems.addDefault("tracker-amount", 1)
        ymlItems.addDefault("Swapper", true)
        ymlItems.addDefault("swapper-amount", 2)
        ymlItems.addDefault("EnergyDrink", true)
        ymlItems.addDefault("energydrink-amount", 2)
        ymlItems.addDefault("Food", true)
        ymlItems.addDefault("food-amount", 3)
        ymlItems.addDefault("jump-stick-uses", 3)
        ymlItems.addDefault("jump-stick-power", 5)
        ymlItems.addDefault("food-recharge", 4)

        //Gun settings
        ymlGuns.addDefault("minigun-damage", 1)
        ymlGuns.addDefault("minigun-speed", 1)
        ymlGuns.addDefault("minigun-reload", 10)
        ymlGuns.addDefault(ConstantStrings.MINIGUN_AMMO, 50)
        ymlGuns.addDefault("minigun-power", 7)
        ymlGuns.addDefault("ak-damage", 3)
        ymlGuns.addDefault("ak-speed", 2)
        ymlGuns.addDefault("ak-reload", 7)
        ymlGuns.addDefault("ak-ammo", 30)
        ymlGuns.addDefault("ak-power", 9)
        ymlGuns.addDefault("sniper-damage", 6)
        ymlGuns.addDefault("sniper-speed", 5)
        ymlGuns.addDefault("sniper-reload", 15)
        ymlGuns.addDefault(ConstantStrings.SNIPER_AMMO, 7)
        ymlGuns.addDefault("sniper-power", 12)
        ymlGuns.addDefault("pistol-damage", 2)
        ymlGuns.addDefault("pistol-speed", 3)
        ymlGuns.addDefault("pistol-reload", 5)
        ymlGuns.addDefault(ConstantStrings.PISTOL_AMMO, 12)
        ymlGuns.addDefault("pistol-power", 5)



        ymlItems.options().copyDefaults(true)
        ymlItems.save(fileItems)
        ymlGuns.options().copyDefaults(true)
        ymlGuns.save(fileGuns)
    }
}

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 11:02 PM All contents of "GameItems.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.game.management

import de.pixels.thehunter.util.files.BaseFile
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class GameItems(game: Game) {

    /**

     * This variable represents a file object that points to the "items.yml" file
     * specific to a game stored in the "arenas" folder under the game's name.
     *
     * The file path is constructed using the BaseFile.GAME_FOLDER constant to identify
     * the base game folder, the `game.name` to identify the specific game name, and the
     * "/arenas" sub-folder to identify the "arenas" folder.
     *
     * Example usage:
     * val itemsFile = FileItems   // Access the file object representing items.yml for the game
     *
     * Please note that the example code is not provided in this documentation.
     */
    private val fileItems = File("${BaseFile.GAME_FOLDER}/arenas/${game.name}/items.yml")

    /**
     * Represents the YAML configuration file for items.
     *
     * This class provides methods to load, save, and manipulate the YAML configuration.
     * The configuration file is loaded from the specified file.
     *
     * @property ymlItems The instance of `YamlConfiguration` representing the loaded configuration.
     * @property fileItems The `File` object representing the YAML configuration file.
     *
     * @constructor Creates a new instance of YamlItems by loading the configuration file.
     * @param fileItems The `File` object representing the YAML configuration file.
     */
    private val ymlItems = YamlConfiguration.loadConfiguration(fileItems)

    /**
     * Holds the file path for the guns YAML file.
     *
     * The guns YAML file is used to store the configurations for the guns in the game.
     * The file path is calculated based on the game's name and the game folder.
     * It is a private variable and should not be accessed directly, but through its corresponding getter method.
     */
    private val fileGuns = File("${BaseFile.GAME_FOLDER}/arenas/${game.name}/guns.yml")

    /**
     * Represents a YamlConfiguration object for storing gun data.
     *
     * This private variable is used to load the gun data from a Yaml file.
     * It is recommended to use [YamlConfiguration.save] method after making modifications to this YamlConfiguration object.
     *
     * The gun data is loaded from the file specified by the `fileGuns` variable.
     *
     * @property ymlGuns The YamlConfiguration object that stores the gun data.
     * @see YamlConfiguration
     */
    private val ymlGuns = YamlConfiguration.loadConfiguration(fileGuns)

    /**

     * Mutable map that stores items.
     * The keys are of type [String] and values can be of any type [Any].
     *
     * Note: This variable is mutable and can be modified.
     */
    val items = mutableMapOf<String, Any>()

    /**
     * Mutable map representing the collection of guns.
     *
     * Key value pairs in the map represent the name of the gun and the quantity available respectively.
     *
     * Usage:
     *  - Add a gun:
     *      guns["gunName"] = quantity
     *
     *  - Remove a gun:
     *      guns.remove("gunName")
     *
     *  - Check the quantity of a gun:
     *      guns["gunName"]
     *
     *  - Update the quantity of a gun:
     *      guns["gunName"] = updatedQuantity
     *
     * Note: The name of the gun should be unique.
     *       The quantity should be a non-negative integer value.
     */
    val guns = mutableMapOf<String, Int>()

    /**
     * Loads all items from the YAML file into the 'items' map.
     */
    fun loadAllItems() {
        for (item in ymlItems.getKeys(false)) {
            items[item] = ymlItems[item] as Any
        }
    }

    /**
     * Load all gun settings from the YAML configuration file.
     *
     * This method iterates over the keys of the 'ymlGuns' object
     * and stores each gun setting with its corresponding value
     * in the 'guns' map.
     */
    fun loadAllGunSettings() {
        for (gun in ymlGuns.getKeys(false)) {
            guns[gun] = ymlGuns.getInt(gun)
        }
    }

    /**
     * Saves all items and gun settings to the YAML configuration files.
     */
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
        ymlItems.addDefault("RifleAmmo", true)
        ymlItems.addDefault("Rifleammo-amount", 6)
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
        ymlGuns.addDefault("Rifle-damage", 3)
        ymlGuns.addDefault("Rifle-speed", 2)
        ymlGuns.addDefault("Rifle-reload", 7)
        ymlGuns.addDefault("Rifle-ammo", 30)
        ymlGuns.addDefault("Rifle-power", 9)
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

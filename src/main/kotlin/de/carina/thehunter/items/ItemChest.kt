/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 8/4/22, 10:26 AM by Carina The Latest changes made by Carina on 7/12/22, 8:13 PM All contents of "ItemChest.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.items

import de.carina.thehunter.TheHunter
import de.carina.thehunter.items.special.*
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.game.GamesHandler
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import java.util.*

class ItemChest(private val game: Game) {

    val chests = mutableMapOf<Location, Inventory>()
    fun createItemInventory(): Inventory {
        val inventory = Bukkit.createInventory(null, 54, LegacyComponentSerializer.legacySection().deserialize(TheHunter.prefix + "§7Chest"))
        var items = mutableListOf<ItemStack>()
        var ammo = mutableListOf<ItemStack>()

        items = addItems(items)
        ammo = ammoAdding(ammo)

        repeater(inventory, items)
        repeater(inventory, ammo)
        return inventory
    }


    private fun repeater(inventory: Inventory, map: MutableList<ItemStack>) {
        repeat(Random().nextInt(game.gameItems.items["item-amounts"] as Int) + 1) {
            val item = map[Random().nextInt(map.size)]
            while (true) {
                val place = Random().nextInt(54)
                if (inventory.getItem(place) == null) {
                    inventory.setItem(place, item)
                    break
                }
            }
        }
    }


    private fun addItems(items: MutableList<ItemStack>): MutableList<ItemStack> {
        if (game.gameItems.items["EggBomb"] == true) {
            val item = EggBomb.eggBomb(game)
            item.amount = game.gameItems.items["eggbomb-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["EnergyDrink"] == true) {
            val item = EnergyDrink.energyDrink
            item.amount = game.gameItems.items["energydrink-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["EyeSpy"] == true) {
            val item = EyeSpy.eyeSpy
            item.amount = game.gameItems.items["eyespy-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["Food"] == true) {
            val item = Food.food
            item.amount = game.gameItems.items["food-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["Healer"] == true) {
            val item = Healer.healer
            item.amount = game.gameItems.items["healer-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["JumpStick"] == true) {
            val item = JumpStick.jumpStick
            item.amount = game.gameItems.items["jump-stick-amount"] as Int
            items.add(item)
        }

        if (game.gameItems.items["Swapper"] == true) {
            val item = Swapper.swapper
            item.amount = game.gameItems.items["swapper-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["Tracker"] == true) {
            val item = Tracker.tracker
            item.amount = game.gameItems.items["tracker-amount"] as Int
            items.add(item)
        }

        return items
    }

    private fun ammoAdding(ammo: MutableList<ItemStack>): MutableList<ItemStack> {
        if (game.gameItems.items["PistolAmmo"] == true) {
            val item = AmmoItems.pistolAmmo
            item.amount = game.gameItems.items["pistolammo-amount"] as Int
            ammo.add(item)
        }
        if (game.gameItems.items["SniperAmmo"] == true) {
            val item = AmmoItems.sniperAmmo
            item.amount = game.gameItems.items["sniperammo-amount"] as Int
            ammo.add(item)
        }
        if (game.gameItems.items["AkAmmo"] == true) {
            val item = AmmoItems.akAmmo
            item.amount = game.gameItems.items["akammo-amount"] as Int
            ammo.add(item)
        }
        if (game.gameItems.items["MiniGunAmmo"] == true) {
            val item = AmmoItems.minigunAmmo
            item.amount = game.gameItems.items["minigunammo-amount"] as Int
            ammo.add(item)
        }
        return ammo
    }

    fun makeChestsFall() {
        if (!game.chestFall)
            return
        val locationCenter: Location = game.arenaCenter!!.clone().subtract((game.worldBoarderController.worldBoarderSize / 2).toDouble(), 0.0, (game.worldBoarderController.worldBoarderSize / 2).toDouble())
        repeat(game.chestAmount) {
            println("Chest $it")
            //Location of the Worldboarder Edge
            println("minimum corner: " + locationCenter.x.toInt() + " " + locationCenter.z.toInt())
            val x = Random().nextInt(game.worldBoarderController.worldBoarderSize)
            val z = Random().nextInt(game.worldBoarderController.worldBoarderSize)
            var newLocation = locationCenter.clone().add(x.toDouble(), 170.0, z.toDouble())
            println("X: $x Z: $z center: " + game.arenaCenter!!.x.toInt() + " " + game.arenaCenter!!.z.toInt() + " new location: " + newLocation.x.toInt() + " z " + newLocation.z.toInt())
            val chest = locationCenter.world.spawnFallingBlock(newLocation, Material.BEACON.createBlockData())
            chest.velocity = Vector(0.0, -1.5, 0.0)
            chest.dropItem = false
            chest.setHurtEntities(false)
            game.gameEntities.add(chest)
            GamesHandler.entitiesInGames[chest] = game

        }
    }
}

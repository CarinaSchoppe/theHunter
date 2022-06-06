/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "DeathChests.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.util.misc

import de.carina.thehunter.util.game.Game
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class DeathChests(private val game: Game) {


    val chests = mutableMapOf<Location, Inventory>()
    fun createDeathChest(player: Player) {
        if (!game.players.contains(player))
            return
        val inventory = Bukkit.createInventory(null, 36)

        for (item in 0..35) {
            inventory.setItem(item, player.inventory.getItem(item))
        }

        player.location.block.type = org.bukkit.Material.CHEST
        val blockString = Material.AIR.toString() + ":" + player.location.world.name + ":" + player.location.x + ":" + player.location.y + ":" + player.location.z
        game.mapResetter.blocks.add(blockString)
        chests[player.location] = inventory
        player.inventory.clear()
        player.updateInventory()
        player.location.world.spawnEntity(player.location, EntityType.LIGHTNING)
    }

}

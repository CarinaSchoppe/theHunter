/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:22 PM All contents of "DeathChest.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.game.ingame.general

import de.pixels.thehunter.util.game.management.Game
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class DeathChest(private val game: Game) {


    /**
     * Represents a mutable map of death chests in the game.
     *
     * @property deathChests A mutable map where the keys are locations and the values are inventories.
     */
    val deathChests = mutableMapOf<Location, Inventory>()

    /**
     * Creates a death chest for the given player.
     *
     * @param player the player for whom the death chest is being created
     */
    fun createDeathChest(player: Player) {
        if (!game.players.contains(player))
            return

        val inventory = Bukkit.createInventory(null, 54, Component.text("${player.name}s deathchest"))
        player.location.block.type = Material.REDSTONE_LAMP
        game.mapResetter.addBlockToList(player.location.block)

        inventory.contents = player.inventory.contents.clone()
        deathChests[player.location] = inventory
        player.location.world.spawnEntity(player.location, EntityType.LIGHTNING)
    }

}

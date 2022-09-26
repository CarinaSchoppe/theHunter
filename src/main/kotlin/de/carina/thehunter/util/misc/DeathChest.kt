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
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

class DeathChest(private val game: Game) {


    fun createDeathChest(player: Player) {
        if (!game.players.contains(player))
            return


        player.location.block.type = Material.CHEST
        var chest: Chest = player.location.block as Chest
        chest.inventory.contents = player.inventory.contents
        game.mapResetter.blocks.add(player.location.block)
        player.location.world.spawnEntity(player.location, EntityType.LIGHTNING)
    }

}

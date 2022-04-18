/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 16.04.22, 12:02 by Carina The Latest changes made by Carina on 16.04.22, 12:02 All contents of "BlocksFlyEvent.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.events.misc

import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.items.chest.special.EggBomb
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.MapResetter
import org.bukkit.Material
import org.bukkit.entity.Egg
import org.bukkit.entity.FallingBlock
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.util.Vector

class BlocksFlyEvent : Listener {


    @EventHandler
    fun onExplosion(event: EntityExplodeEvent) {
        if (event.entity !is TNTPrimed) return

        val tnt = event.entity as TNTPrimed
        if (!EggBomb.bombs.contains(tnt)) return

        EggBomb.bombs.remove(tnt)
        val player = (tnt.source as Egg).shooter as org.bukkit.entity.Player
        val game = GamesHandler.playerInGames[player]!!

        for (block in event.blockList()) {
            game.mapResetter.blocks.add(MapResetter.createBlockString(block))
            event.yield = 0f

            val x = -5f + (Math.random() * (5 + 5 + 1)).toFloat()
            val y = -5f + (Math.random() * (6 + 6 + 1)).toFloat()
            val z = -5f + (Math.random() * (5 + 5 + 1)).toFloat()
            val fallingBlock = block.world.spawnFallingBlock(block.location, block.type.createBlockData())
            fallingBlock.velocity = Vector(x / 1.5, y / 1.5, z / 1.5)
            fallingBlock.dropItem = false
            block.type = Material.AIR
            GamesHandler.entitiesInGames[fallingBlock] = game
            game.gameEntities.add(fallingBlock)
        }
    }

    @EventHandler
    fun onBlockToSolid(event: EntityChangeBlockEvent) {
        if (event.entity !is FallingBlock)
            return
        val game = GamesHandler.entitiesInGames[event.entity] ?: return
        if (game.currentGameState !is IngameState)
            return
        val block = event.block

        if (GamesHandler.entitiesInGames.containsKey(event.entity))
            GamesHandler.entitiesInGames.remove(event.entity)
        if (game.gameEntities.contains(event.entity))
            game.gameEntities.remove(event.entity)
        val blockString = Material.AIR.toString() + ":" + block.world.name + ":" + block.x + ":" + block.y + ":" + block.z
        game.mapResetter.blocks.add(blockString)
    }

}
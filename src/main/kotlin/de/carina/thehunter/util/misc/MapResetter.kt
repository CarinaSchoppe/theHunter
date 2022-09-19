/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "MapResetter.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.util.misc

import de.carina.thehunter.util.game.Game
import org.bukkit.Bukkit
import org.bukkit.block.Block

class MapResetter(val game: Game) {

     val blocks = mutableListOf<Block>()

    companion object {
        fun setBlock(block: Block) {
            val other = Bukkit.getWorld(block.world.name)!!.getBlockAt(block.x, block.y, block.z)
            other.type = block.type
            other.blockData = block.blockData
        }
    }


    fun resetMap() {
        blocks.reverse()
        game.gameEntities.forEach {
            it.remove()
        }
        blocks.forEach {
            setBlock(it)
        }
        blocks.clear()
    }
}

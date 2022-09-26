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
import org.bukkit.Material
import org.bukkit.block.Block

class MapResetter(val game: Game) {

    private val blocks = mutableListOf<String>()

    fun addBlockToList(block: Block) {
        val blockString = block.type.name + ":" + block.blockData.asString + ":" + block.world.name + ":" + block.x + ":" + block.y + ":" + block.z
        blocks.add(blockString)
    }

    companion object {
        fun setBlock(blockString: String) {
            val data = blockString.split(":")
            val type = Material.getMaterial(data.first())!!
            val blockData = Bukkit.getServer().createBlockData(data[1])
            val world = Bukkit.getWorld(data[2])!!
            val x = data[3].toInt()
            val y = data[4].toInt()
            val z = data[5].toInt()

            world.getBlockAt(x, y, z).type = type
            world.getBlockAt(x, y, z).blockData = blockData
        }
    }


    fun resetMap() {
        blocks.reverse()
        game.gameEntities.forEach {
            it.remove()
        }
        game.gameEntities.clear()
        blocks.forEach {
            setBlock(it)
        }
        blocks.clear()
    }
}

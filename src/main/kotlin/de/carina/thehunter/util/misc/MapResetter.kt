/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "MapResetter.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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

    val blocks = mutableListOf<String>()

    companion object {
        fun createBlockString(block: Block): String {
            return block.type.toString() +
                    ":" + block.world.name +
                    ":" + block.x +
                    ":" + block.y +
                    ":" + block.z
        }

        fun setBlockFromBlockString(blockString: String) {
            val split = blockString.split(":")
            val type = split[0]
            val world = split[1]
            val x = split[2].toInt()
            val y = split[3].toInt()
            val z = split[4].toInt()
            val worldObj = Bukkit.getWorld(world)
            val block = worldObj!!.getBlockAt(x, y, z)
            block.type = org.bukkit.Material.valueOf(type)
        }
    }


    fun resetMap() {
        blocks.reverse()
        game.gameEntities.forEach {
            it.remove()
        }
        blocks.forEach {
            setBlockFromBlockString(it)
        }
        blocks.clear()
    }
}
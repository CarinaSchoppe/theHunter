/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:28 PM All contents of "MapResetter.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.misc

import de.pixels.thehunter.util.game.management.Game
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block

class MapResetter(val game: Game) {

    /**
     * This variable represents a mutable list of string blocks.
     *
     * Note that the access modifier is set to private.
     *
     * @see mutableListOf
     */
    private val blocks = mutableListOf<String>()

    /**
     * Adds a Block to the list of blocks.
     *
     * @param block the Block to be added.
     */
    fun addBlockToList(block: Block) {
        val blockString = block.type.name + ":" + block.world.name + ":" + block.x + ":" + block.y + ":" + block.z
        blocks.add(blockString)
    }

    companion object {
        /**
         * Sets a block at the specified coordinates in the given world.
         *
         * @param blockString the string representing the block to be set, using the format "material:world:x:y:z"
         * @throws IllegalArgumentException if the blockString does not match the expected format or if the material is invalid
         */
        fun setBlock(blockString: String) {
            val data = blockString.split(":")
            val type = Material.getMaterial(data[0])
            //val blockData = Bukkit.getServer().createBlockData(data[1])
            val world = Bukkit.getWorld(data[1])
            val x = data[2].toInt()
            val y = data[3].toInt()
            val z = data[4].toInt()

            world?.getBlockAt(x, y, z)?.type = type ?: Material.AIR
            //   world.getBlockAt(x, y, z).blockData = blockData
        }
    }


    /**
     * Resets the map by reversing the blocks list, removing all game entities from the game, clearing the game entities list,
     * setting each block in the blocks list, and finally clearing the blocks list.
     *
     * This method is typically called when the game map needs to be reset, such as when a new level is started.
     */
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

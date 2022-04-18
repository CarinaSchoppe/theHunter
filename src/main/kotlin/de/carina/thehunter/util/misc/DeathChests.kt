package de.carina.thehunter.util.misc

import de.carina.thehunter.util.game.Game
import org.bukkit.Bukkit
import org.bukkit.Location
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

        val location = player.location
        chests[location] = inventory
    }

}

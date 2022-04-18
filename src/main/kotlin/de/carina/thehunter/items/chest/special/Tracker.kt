package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.items.chest.ItemHandler
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class Tracker : Listener {
    fun createTrackerItem(): ItemStack {
        return ItemBuilder(Material.COMPASS).addDisplayName(TheHunter.PREFIX + "§6Tracker").addLore("§7Click to track a player!").addLore("§7Right-click to activate").addEnchantment(Enchantment.DURABILITY, 1).build()
    }

    @EventHandler
    fun onPlayerTrack(event: PlayerInteractEvent) {
        if (!ItemHandler.shouldInteractWithItem(event, createTrackerItem(), "Tracker"))
            return
        event.isCancelled = true
        //get the closest Player to the event.player based on the distance of their location
        val closestPlayers = GamesHandler.playerInGames[event.player]!!.players.filter { it.uniqueId != event.player.uniqueId }
        var closest = closestPlayers.first()
        for (players in closestPlayers) {
            if (event.player.location.distance(players.location) < event.player.location.distance(closest.location))
                closest = players
        }

        event.player.compassTarget = closest.location
        event.player.sendMessage(TheHunter.instance.messages.messagesMap["tracker-distance"]!!.replace("%player%", closest.name).replace("%distance%", event.player.location.distance(closest.location).toString()))


    }
}
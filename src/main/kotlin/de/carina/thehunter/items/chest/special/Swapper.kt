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

class Swapper : Listener {
    companion object {

        fun createSwapperItem(): ItemStack {
            return ItemBuilder(Material.TNT).addDisplayName(TheHunter.PREFIX + "§6Swapper").addLore("§7Click to swap with a player!").addLore("§7Right-click to activate").addEnchantment(Enchantment.DURABILITY, 1).build()
        }
    }

    @EventHandler
    fun onPlayerSwap(event: PlayerInteractEvent) {
        if (!ItemHandler.shouldInteractWithItem(event, createSwapperItem(), "Swapper"))
            return
        event.isCancelled = true

        val target = GamesHandler.playerInGames[event.player]!!.players.filter { it != event.player }.random()
        val targetLocation = target.location
        target.teleport(event.player.location)
        event.player.teleport(targetLocation)
        target.sendMessage(TheHunter.instance.messages.messagesMap["player-swapped"]!!.replace("%player%", event.player.name))
        event.player.sendMessage(TheHunter.instance.messages.messagesMap["player-swapped"]!!.replace("%player%", target.name))
    }
}
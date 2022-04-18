package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class Swapper : Listener {
    fun createSwapperItem(): ItemStack {
        return ItemBuilder(Material.TNT).addDisplayName(TheHunter.PREFIX + "§6Swapper").addLore("§7Click to swap with a player!").addLore("§7Right-click to activate").addEnchantment(Enchantment.DURABILITY, 1).build()
    }

    @EventHandler
    fun onPlayerSwap(event: PlayerInteractEvent) {
        if (event.item == null)
            return
        if (!event.item!!.hasItemMeta())
            return
        if (event.item!!.itemMeta!! != createSwapperItem().itemMeta)
            return
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        val game = GamesHandler.playerInGames[event.player]!!
        if (event.player.inventory.itemInMainHand.itemMeta != createSwapperItem().itemMeta)
            return
        if (!event.action.isRightClick)
            return
        event.isCancelled = true
        if (game.gameItems!!.items["Swapper"] == false)
            return
        if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
            return

        val target = GamesHandler.playerInGames[event.player]!!.players.filter { it != event.player }.random()
        val targetLocation = target.location
        target.teleport(event.player.location)
        event.player.teleport(targetLocation)
        target.sendMessage(TheHunter.instance.messages.messagesMap["player-swapped"]!!.replace("%player%", event.player.name))
        event.player.sendMessage(TheHunter.instance.messages.messagesMap["player-swapped"]!!.replace("%player%", target.name))
    }
}
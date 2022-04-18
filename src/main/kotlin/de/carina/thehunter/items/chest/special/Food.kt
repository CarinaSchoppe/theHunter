package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class Food : Listener {
    fun createFoodItem(): ItemStack {
        return ItemBuilder(Material.BEETROOT_SOUP).addDisplayName(TheHunter.PREFIX + "§6Food").addLore("§7Click to regenerate food!").addLore("§7Right-click to activate").addEnchantment(Enchantment.DURABILITY, 1).build()
    }

    @EventHandler
    fun onPlayerFood(event: PlayerInteractEvent) {
        if (event.item == null)
            return
        if (!event.item!!.hasItemMeta())
            return
        if (event.item!!.itemMeta!! != createFoodItem().itemMeta)
            return
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        if (!event.action.isRightClick)
            return
        val game = GamesHandler.playerInGames[event.player]!!
        if (event.player.inventory.itemInMainHand.itemMeta != createFoodItem().itemMeta)
            return
        event.isCancelled = true
        if (game.gameItems!!.items["Food"] == false)
            return
        if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
            return

        event.player.foodLevel += GamesHandler.playerInGames[event.player]!!.gameItems!!.items["food-recharge"] as Int

        event.player.sendMessage(TheHunter.instance.messages.messagesMap["food-recharge"]!!.replace("%recharge%", (GamesHandler.playerInGames[event.player]!!.gameItems!!.items["food-recharge"] as Int).toString()))

    }

    @EventHandler
    fun onPlayerFoodLoose(event: FoodLevelChangeEvent) {
        if (event.entity !is Player)
            return
        val player = event.entity as Player
        if (!GamesHandler.playerInGames.containsKey(player))
            return
        if (GamesHandler.playerInGames[player]!!.currentGameState !is IngameState)
            return
        if (GamesHandler.playerInGames[player]!!.gameItems!!.items["Food"] != false)
            return

        player.foodLevel = 20
        event.isCancelled = true

    }
}

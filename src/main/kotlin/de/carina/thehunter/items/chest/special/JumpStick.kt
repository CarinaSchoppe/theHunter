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
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class JumpStick : Listener {

    companion object {
        val uses = mutableMapOf<Player, Int>()

    }

    fun createJumpStick(): ItemStack {
        return ItemBuilder(Material.STICK).addDisplayName(TheHunter.PREFIX + "§6JumpStick").addLore("§aClick to jump into an direction with power").addLore("§7Right-click to activate").addEnchantment(Enchantment.DURABILITY, 1).build()
    }

    @EventHandler
    fun onJumpStickUse(event: PlayerInteractEvent) {
        if (event.item == null)
            return
        if (!event.item!!.hasItemMeta())
            return
        if (event.item!!.itemMeta!! != createJumpStick().itemMeta)
            return

        if (event.player.inventory.itemInMainHand.itemMeta != createJumpStick().itemMeta)
            return
        val player = event.player
        if (!GamesHandler.playerInGames.containsKey(player))
            return
        if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
            return
        if (!event.action.isRightClick)
            return
        event.isCancelled = true
        if (GamesHandler.playerInGames[event.player]!!.gameItems!!.items["Knife"] == false)
            return
        if (uses.containsKey(player)) {
            if (uses[player]!! >= GamesHandler.playerInGames[player]!!.gameItems!!.items["jumpstick-uses"] as Int)
                player.sendMessage(TheHunter.instance.messages.messagesMap["jumpstick-broke"]!!)
            player.inventory.itemInMainHand.amount -= 1
            uses[player] = 0
            return
        }

        player.velocity = player.eyeLocation.direction.multiply(GamesHandler.playerInGames[player]!!.gameItems!!.items["jumpstick-power"] as Int)
        if (uses.containsKey(player))
            uses[player] = 1
        else
            uses[player] = uses[player]!! + 1
    }

}
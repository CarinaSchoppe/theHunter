package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class Healer : Listener {

    fun createHealerItem(): ItemStack {
        return ItemBuilder(Material.RED_DYE).addDisplayName(TheHunter.PREFIX + "§7Healer").addLore("§7Heals you!").addLore("§7Right-click to activate").build()
    }

    @EventHandler
    fun onHealerItemClick(event: PlayerInteractEvent) {
        if (event.item != null)
            return
        if (!event.item!!.hasItemMeta())
            return
        if (event.item?.itemMeta != createHealerItem().itemMeta)
            return
        if (event.player.inventory.itemInMainHand.itemMeta != createHealerItem().itemMeta)
            return

        if (!GamesHandler.playerInGames.containsKey(event.player)) return

        if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
            return

        event.isCancelled = true
        if (!event.action.isRightClick)
            return
        if (GamesHandler.playerInGames[event.player]!!.gameItems!!.items["Healer"] == false)
            return
        event.player.health += TheHunter.instance.itemSettings.settingsMap["healer-amount"] as Int

        event.player.sendActionBar(LegacyComponentSerializer.legacySection().deserialize(TheHunter.instance.messages.messagesMap["healer-message"]!!.replace("%hearts%", (TheHunter.instance.settings.settingsMap["healer-amount"] as Int).toString())))
    }
}
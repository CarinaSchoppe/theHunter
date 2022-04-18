package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.items.chest.ItemHandler
import de.carina.thehunter.util.builder.ItemBuilder
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class Healer : Listener {
    companion object {
        fun createHealerItem(): ItemStack {
            return ItemBuilder(Material.RED_DYE).addDisplayName(TheHunter.PREFIX + "§7Healer").addLore("§7Heals you!").addLore("§7Right-click to activate").build()
        }
    }

    @EventHandler
    fun onHealerItemClick(event: PlayerInteractEvent) {
        if (!ItemHandler.shouldInteractWithItem(event, createHealerItem(), "Healer"))
            return
        event.isCancelled = true
        event.player.health += TheHunter.instance.itemSettings.settingsMap["healer-amount"] as Int

        event.player.sendActionBar(LegacyComponentSerializer.legacySection().deserialize(TheHunter.instance.messages.messagesMap["healer-message"]!!.replace("%hearts%", (TheHunter.instance.settings.settingsMap["healer-amount"] as Int).toString())))
    }
}
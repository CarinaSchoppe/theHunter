package de.carina.thehunter.events.misc.gameconfigurator

import de.carina.thehunter.util.misc.Util
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class SettingsConfigurator : Listener {

    //TODO: hier
    @EventHandler
    fun onInteractWithSettings(event: InventoryClickEvent) {
        if (!Util.currentGameSelected.containsKey(event.whoClicked as Player)) return

        if (PlainTextComponentSerializer.plainText().serialize(event.view.title()) != PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize("§d${Util.currentGameSelected[event.whoClicked as Player]!!.name}§6: Game Settings")))
            return

        if (event.currentItem == null)
            return

        if (event.currentItem!!.itemMeta == null)
            return

        //TODO: Hier weitermachen
    }
}
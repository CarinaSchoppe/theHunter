package de.carina.thehunter.events.misc.gameconfigurator

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.Items
import de.carina.thehunter.util.misc.Util
import de.carina.thehunter.util.misc.WorldboarderController
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class SettingsConfigurator : Listener {

    //TODO: hier
    @EventHandler
    fun onInteractWithSettings(event: InventoryClickEvent) {
        if (!Util.currentGameSelected.containsKey(event.whoClicked as Player)) return

        if (PlainTextComponentSerializer.plainText().serialize(event.view.title()) != PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize("§d${Util.currentGameSelected[event.whoClicked as Player]!!.name}§6: Game Settings")))
            return

        event.isCancelled = true
        if (event.currentItem == null)
            return

        if (event.currentItem!!.itemMeta == null)
            return

        val item = getItemObject(event) ?: return


        val type = if (event.currentItem?.type == Material.RED_WOOL) false else if (event.currentItem?.type == Material.GREEN_WOOL) true else return
        when (item.itemMeta) {
            Items.borderSize -> borderSize(type, event.whoClicked as Player)
        }


    }

    private fun borderSize(type: Boolean, player: Player) {
        if (type) {
            if (Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize + 10 > WorldboarderController.toHigh) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["bordersize-to-high".replace("%size%", WorldboarderController.toHigh.toString())]!!)
                return
            }
            Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize += 10
            player.sendMessage(TheHunter.instance.messages.messagesMap["bordersize-plus".replace("%size%", Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize.toString())]!!)
        } else {
            if (Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize + 10 > WorldboarderController.toHigh) {
                player.sendMessage(TheHunter.instance.messages.messagesMap["bordersize-to-high".replace("%size%", WorldboarderController.toHigh.toString())]!!)
                return
            }
            Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize -= 10
            player.sendMessage(TheHunter.instance.messages.messagesMap["bordersize-minus".replace("%size%", Util.currentGameSelected[player]!!.worldBoarderController.minBorderSize.toString())]!!)

        }
    }

    private fun getItemObject(event: InventoryClickEvent): ItemStack? {
        if (event.currentItem!!.type == Material.RED_WOOL) {
            return event.inventory.getItem(event.slot - 5)
        } else if (event.currentItem!!.type == Material.GREEN_WOOL) {
            return event.inventory.getItem(event.slot - 4)
        }
        return null
    }
}
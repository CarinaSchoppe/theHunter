package de.carina.thehunter.events.misc.gameconfigurator

import de.carina.thehunter.util.builder.Inventories
import de.carina.thehunter.util.builder.Items
import de.carina.thehunter.util.misc.Util
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class GameConfigurator : Listener {

    @EventHandler
    fun onGameConfigEdit(event: InventoryClickEvent) {
        //TODO: hier check
        if (PlainTextComponentSerializer.plainText().serialize(event.view.title()) != PlainTextComponentSerializer.plainText().serialize(LegacyComponentSerializer.legacySection().deserialize("§d${Util.currentGameSelected}§6: Game Setup")))
            return

        if (event.currentItem == null)
            return

        if (event.currentItem!!.itemMeta == null)
            return


        when (event.currentItem!!.itemMeta!!) {
            Items.addLobbyButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config lobbyspawn ${Util.currentGameSelected.name}")
            Items.addBackButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config backspawn ${Util.currentGameSelected.name}")
            Items.addEndButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config endspawn ${Util.currentGameSelected.name}")
            Items.finishButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config finish ${Util.currentGameSelected.name}")
            Items.addArenaCenterButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config arenacenter ${Util.currentGameSelected.name}")
            Items.addSpectatorButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config spectatorspawn ${Util.currentGameSelected.name}")
            Items.addSpawnButton.itemMeta -> (event.whoClicked as Player).performCommand("thehunter setup config playerspawn ${Util.currentGameSelected.name}")
            Items.settingsHead.itemMeta -> (event.whoClicked as Player).openInventory(Inventories.settingsGameInventory)
        }

    }
}
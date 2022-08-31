package de.carina.thehunter.events.misc

import de.carina.thehunter.items.AmmoItems
import de.carina.thehunter.items.special.Knife
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent

class IngameItemUse : Listener {

    @EventHandler
    fun playerUsesAmmoItems(event: PlayerInteractEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        if (event.item == null)
            return
        when (event.item!!.itemMeta) {
            AmmoItems.sniperAmmo.itemMeta -> event.isCancelled = true
            AmmoItems.minigunAmmo.itemMeta -> event.isCancelled = true
            AmmoItems.akAmmo.itemMeta -> event.isCancelled = true
            AmmoItems.pistolAmmo.itemMeta -> event.isCancelled = true
        }

    }

    @EventHandler
    fun onDropKnife(event: PlayerDropItemEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player)) return
        if (event.itemDrop.itemStack.itemMeta != Knife.knife.itemMeta) return
        event.isCancelled = true


    }
}

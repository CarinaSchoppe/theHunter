package de.carina.thehunter.events.game

import de.carina.thehunter.guns.Ak
import de.carina.thehunter.guns.Minigun
import de.carina.thehunter.guns.Pistol
import de.carina.thehunter.guns.Sniper
import de.carina.thehunter.util.game.GamesHandler
import net.kyori.adventure.text.Component
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent

class PlayerHotbarHover : Listener {


    @EventHandler
    fun onPlayerInventoryHover(event: PlayerItemHeldEvent) {

        if (!GamesHandler.playerInGames.containsKey(event.player))
            return

        if (!(event.player.inventory.getItem(event.newSlot) != null && event.player.inventory.getItem(event.newSlot)?.hasItemMeta() == true))
            return

        val item = event.player.inventory.getItem(event.newSlot)!!
        when (item.itemMeta) {
            Ak.ak.itemMeta -> {
                val maxAmmoAmount = GamesHandler.playerInGames[event.player]!!.gameItems.guns["ak-ammo"]!!
                val currentAmmo = Ak.magazine[event.player]
                event.player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
            }

            Pistol.pistol.itemMeta -> {
                val maxAmmoAmount = GamesHandler.playerInGames[event.player]!!.gameItems.guns["pistol-ammo"]!!
                val currentAmmo = Pistol.magazine[event.player]
                event.player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
            }

            Sniper.sniper.itemMeta -> {
                val maxAmmoAmount = GamesHandler.playerInGames[event.player]!!.gameItems.guns["sniper-ammo"]!!
                val currentAmmo = Sniper.magazine[event.player]
                event.player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
            }

            Minigun.minigun.itemMeta -> {
                val maxAmmoAmount = GamesHandler.playerInGames[event.player]!!.gameItems.guns["minigun-ammo"]!!
                val currentAmmo = Minigun.magazine[event.player]
                event.player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
            }
        }

    }

}

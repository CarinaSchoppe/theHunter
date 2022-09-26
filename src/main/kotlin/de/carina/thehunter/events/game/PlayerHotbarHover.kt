package de.carina.thehunter.events.game

import de.carina.thehunter.guns.Ak
import de.carina.thehunter.guns.Minigun
import de.carina.thehunter.guns.Pistol
import de.carina.thehunter.guns.Sniper
import de.carina.thehunter.util.game.GamesHandler
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack

class PlayerHotbarHover : Listener {


    companion object {

        fun updateHotbar(item: ItemStack?, player: Player) {
            when (item?.itemMeta) {
                Ak.ak.itemMeta -> {
                    val maxAmmoAmount = GamesHandler.playerInGames[player]!!.gameItems.guns["ak-ammo"]!!
                    val currentAmmo = Ak.magazine.getOrDefault(player, 0)
                    player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
                }

                Pistol.pistol.itemMeta -> {
                    val maxAmmoAmount = GamesHandler.playerInGames[player]!!.gameItems.guns["pistol-ammo"]!!
                    val currentAmmo = Pistol.magazine.getOrDefault(player, 0)
                    player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
                }

                Sniper.sniper.itemMeta -> {
                    val maxAmmoAmount = GamesHandler.playerInGames[player]!!.gameItems.guns["sniper-ammo"]!!
                    val currentAmmo = Sniper.magazine.getOrDefault(player, 0)
                    player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
                }

                Minigun.minigun.itemMeta -> {
                    val maxAmmoAmount = GamesHandler.playerInGames[player]!!.gameItems.guns["minigun-ammo"]!!
                    val currentAmmo = Minigun.magazine.getOrDefault(player, 0)
                    player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
                }

                else -> {
                    player.sendActionBar(Component.text(""))
                }
            }
        }
    }

    @EventHandler
    fun onPlayerInventoryHover(event: PlayerItemHeldEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        val item = event.player.inventory.getItem(event.newSlot)

        updateHotbar(item, event.player)

    }

}

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 11:02 PM All contents of "PlayerHotbarHover.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.events.player

import de.pixels.thehunter.guns.Minigun
import de.pixels.thehunter.guns.Pistol
import de.pixels.thehunter.guns.Rifle
import de.pixels.thehunter.guns.Sniper
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack

class PlayerHotbarHover : Listener {


    companion object {

        /**
         * Updates the hotbar display for the given player based on the item they are holding.
         *
         * @param item The item the player is holding.
         * @param player The player whose hotbar display will be updated.
         */
        fun updateHotbar(item: ItemStack?, player: Player) {
            when (item?.itemMeta) {
                Rifle.gun.itemMeta -> {
                    val maxAmmoAmount = GamesHandler.playerInGames[player]?.gameItems?.guns?.get("rifle-ammo") ?: return
                    val currentAmmo = Rifle.magazine.getOrDefault(player, 0)
                    player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
                }

                Pistol.gun.itemMeta -> {
                    val maxAmmoAmount =
                        GamesHandler.playerInGames[player]?.gameItems?.guns?.get(ConstantStrings.PISTOL_AMMO) ?: return
                    val currentAmmo = Pistol.magazine.getOrDefault(player, 0)
                    player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
                }

                Sniper.gun.itemMeta -> {
                    val maxAmmoAmount =
                        GamesHandler.playerInGames[player]?.gameItems?.guns?.get(ConstantStrings.SNIPER_AMMO) ?: return
                    val currentAmmo = Sniper.magazine.getOrDefault(player, 0)
                    player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
                }

                Minigun.gun.itemMeta -> {
                    val maxAmmoAmount =
                        GamesHandler.playerInGames[player]?.gameItems?.guns?.get(ConstantStrings.MINIGUN_AMMO) ?: return
                    val currentAmmo = Minigun.magazine.getOrDefault(player, 0)
                    player.sendActionBar(Component.text("[$currentAmmo/$maxAmmoAmount]"))
                }

                else -> {
                    player.sendActionBar(Component.text(""))
                }
            }
        }
    }

    /**
     * Handles when a player hovers over an item in their inventory.
     *
     * @param event The PlayerItemHeldEvent representing the event.
     */
    @EventHandler
    fun onPlayerInventoryHover(event: PlayerItemHeldEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        val item = event.player.inventory.getItem(event.newSlot)

        updateHotbar(item, event.player)

    }

}

/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 6:30 PM All contents of "IngameItemUse.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.events.ingame

import de.pixels.thehunter.guns.Minigun
import de.pixels.thehunter.guns.Pistol
import de.pixels.thehunter.guns.Rifle
import de.pixels.thehunter.guns.Sniper
import de.pixels.thehunter.items.general.AmmoItems
import de.pixels.thehunter.items.special.Knife
import de.pixels.thehunter.util.game.management.GamesHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent

class WeaponInteraction : Listener {

    /**
     * This method is an event handler for when a player uses ammo items.
     * It checks if the player is in a game and if the item used is not null.
     * If the item used is one of the predefined ammo items, the event is cancelled.
     *
     * @param event The PlayerInteractEvent that triggered this method.
     */
    @EventHandler
    fun playerUsesAmmoItems(event: PlayerInteractEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player) || event.item == null)
            return

        when (event.item?.itemMeta) {
            Sniper.gun.itemMeta -> event.isCancelled = true
            Rifle.gun.itemMeta -> event.isCancelled = true
            Pistol.gun.itemMeta -> event.isCancelled = true
            Minigun.gun.itemMeta -> event.isCancelled = true
            AmmoItems.sniperAmmo.itemMeta -> event.isCancelled = true
            AmmoItems.minigunAmmo.itemMeta -> event.isCancelled = true
            AmmoItems.rifleAmmo.itemMeta -> event.isCancelled = true
            AmmoItems.pistolAmmo.itemMeta -> event.isCancelled = true
        }

    }

    /**
     * Event handler for when a player drops a knife.
     *
     * @param event The PlayerDropItemEvent that triggered the handler.
     */
    @EventHandler
    fun onDropKnife(event: PlayerDropItemEvent) {
        if (!GamesHandler.playerInGames.containsKey(event.player) || event.itemDrop.itemStack.itemMeta != Knife.knife.itemMeta) return
        event.isCancelled = true
    }
}

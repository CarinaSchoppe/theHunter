/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "Ammo.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.misc

import de.carina.thehunter.items.chest.AmmoItems
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class AmmoHandler : Listener {

    @EventHandler
    fun onAmmoInteract(event: PlayerInteractEvent) {
        if (event.item == null)
            return
        if (!event.item!!.hasItemMeta())
            return
        if (event.item!!.itemMeta != AmmoItems.sniperAmmo.itemMeta)
            return
        else if (event.item!!.itemMeta != AmmoItems.pistolAmmo.itemMeta)
            return
        else if (event.item!!.itemMeta != AmmoItems.minigunAmmo.itemMeta)
            return
        else if (event.item!!.itemMeta != AmmoItems.akAmmo.itemMeta)
            return
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return

        event.isCancelled = true
    }


}
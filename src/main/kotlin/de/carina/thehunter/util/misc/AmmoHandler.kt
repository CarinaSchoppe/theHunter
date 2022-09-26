/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "AmmoHandler.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.util.misc

import de.carina.thehunter.items.AmmoItems
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

/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "GunHandler.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.guns

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerDropItemEvent

class GunHandler : Listener {

    @EventHandler
    fun onGunDrop(event: PlayerDropItemEvent) {
        if (event.itemDrop.itemStack == null)
            return
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        if (!event.itemDrop.itemStack.hasItemMeta())
            return
        if (event.itemDrop.itemStack.itemMeta == Ak.createAkGunItem().itemMeta ||
            event.itemDrop.itemStack.itemMeta == Minigun.createMiniGunItem().itemMeta ||
            event.itemDrop.itemStack.itemMeta == Sniper.createSniperGunItem().itemMeta ||
            event.itemDrop.itemStack.itemMeta == Pistol.createPistolGunItem().itemMeta
        ) {
            event.isCancelled = true
            event.player.sendMessage(TheHunter.instance.messages.messagesMap["cant-drop-item"]!!.replace("%item%", event.itemDrop.itemStack.type.toString()))
            return
        }
    }

    @EventHandler
    fun onPlayerHitEvent(event: EntityDamageByEntityEvent) {
        if (event.damager !is Arrow)
            return
        val arrow = event.damager as Arrow
        if (arrow.shooter !is Player)
            return
        val player = arrow.shooter as Player
        if (!GamesHandler.playerInGames.containsKey(player))
            return
        if (Ak.shotBullets.containsKey(player)) {
            if (Ak.shotBullets[player]!!.contains(arrow)) {
                event.damage = GamesHandler.playerInGames[player]!!.gameItems.guns["ak-damage"] as Double
                return
            }
        } else if (Minigun.shotBullets.containsKey(player)) {
            if (Minigun.shotBullets[player]!!.contains(arrow)) {
                event.damage = GamesHandler.playerInGames[player]!!.gameItems.guns["minigun-damage"] as Double
                return
            }

        } else if (Sniper.shotBullets.containsKey(player)) {
            if (Sniper.shotBullets[player]!!.contains(arrow)) {
                event.damage = GamesHandler.playerInGames[player]!!.gameItems.guns["sniper-damage"] as Double
                return
            }
        } else if (Pistol.shotBullets.containsKey(player)) {
            if (Pistol.shotBullets[player]!!.contains(arrow)) {
                event.damage = GamesHandler.playerInGames[player]!!.gameItems.guns["pistol-damage"] as Double
                return
            }
        }
    }
}
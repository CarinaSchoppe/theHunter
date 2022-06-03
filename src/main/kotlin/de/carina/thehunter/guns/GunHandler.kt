/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "GunHandler.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.guns

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Sound
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.ItemMeta

class GunHandler : Listener {

    @EventHandler
    fun onPlayerShoot(event: PlayerInteractEvent) {
        if (event.item == null) return

        if (!event.player.inventory.itemInMainHand.hasItemMeta())
            return
        if (!event.item!!.hasItemMeta()) return
        if (event.action.isLeftClick) {
            if (whenLeftClick(event))
                return
        } else {
            if (whenRightClick(event))
                return

        }
    }

    private fun whenLeftClick(event: PlayerInteractEvent): Boolean {
        when (event.item!!.itemMeta) {
            Ak.createAkGunItem().itemMeta -> {
                if (!event.player.hasPermission("thehunter.ak")) return true
                Ak.reloadGun(event.player)
                return true
            }

            Minigun.createMiniGunItem().itemMeta -> {
                if (!event.player.hasPermission("thehunter.minigun")) return true
                Minigun.reloadGun(event.player)
                return true
            }

            Pistol.createPistolGunItem().itemMeta -> {
                if (!event.player.hasPermission("thehunter.pistol")) return true
                Pistol.reloadGun(event.player)
                return true
            }

            Sniper.createSniperGunItem().itemMeta -> {
                if (!event.player.hasPermission("thehunter.sniper")) return true

                Sniper.reloadGun(event.player)
                return true
            }
        }
        return true

    }

    private fun whenRightClick(event: PlayerInteractEvent): Boolean {
        when (event.item!!.itemMeta) {
            Ak.createAkGunItem().itemMeta -> {
                if (!event.player.hasPermission("thehunter.ak")) return true
                if (event.player.isSneaking) {
                    Ak.reloadGun(event.player)
                    return true
                }
                Ak.shoot(event.player)
            }

            Minigun.createMiniGunItem().itemMeta -> {
                if (!event.player.hasPermission("thehunter.minigun")) return true
                if (event.player.isSneaking) {
                    Minigun.reloadGun(event.player)
                    return true
                }
                Minigun.shoot(event.player)
            }

            Pistol.createPistolGunItem().itemMeta -> {
                if (!event.player.hasPermission("thehunter.pistol")) return true
                if (event.player.isSneaking) {
                    Pistol.reloadGun(event.player)
                    return true
                }
                Pistol.shoot(event.player)
            }

            Sniper.createSniperGunItem().itemMeta -> {
                if (!event.player.hasPermission("thehunter.sniper")) return true
                if (event.player.isSneaking) {
                    Sniper.reloadGun(event.player)
                    return true
                }
                Sniper.shoot(event.player)
            }
        }
        return true
    }

    @EventHandler
    fun onGunDrop(event: PlayerDropItemEvent) {


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

    companion object {
        fun removeAmmo(player: Player, amount: Int, itemMeta: ItemMeta) {
            for (itemStack in player.inventory.contents!!) {
                if (itemStack != null && itemStack.itemMeta == itemMeta) {
                    if (player.inventory.itemInMainHand.itemMeta == itemStack.itemMeta) {
                        itemStack.subtract(amount)
                        return
                    }
                    itemStack.subtract(amount)
                    return
                }
            }
            player.updateInventory()
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
        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
        (event.entity as Player).playSound(event.entity.location, Sound.ENTITY_PLAYER_HURT, 1f, 1f)
        if (Ak.shotBullets.containsKey(player)) {
            if (Ak.shotBullets[player]!!.contains(arrow)) {
                event.damage = GamesHandler.playerInGames[player]!!.gameItems.guns["ak-damage"]?.plus(0.0) ?: 3.0
                return
            }
        } else if (Minigun.shotBullets.containsKey(player)) {
            if (Minigun.shotBullets[player]!!.contains(arrow)) {
                event.damage = GamesHandler.playerInGames[player]!!.gameItems.guns["minigun-damage"]?.plus(0.0) ?: 1.0
                return
            }

        } else if (Sniper.shotBullets.containsKey(player)) {
            if (Sniper.shotBullets[player]!!.contains(arrow)) {
                event.damage = GamesHandler.playerInGames[player]!!.gameItems.guns["sniper-damage"]?.plus(0.0) ?: 5.0
                return
            }
        } else if (Pistol.shotBullets.containsKey(player)) {
            if (Pistol.shotBullets[player]!!.contains(arrow)) {
                event.damage = GamesHandler.playerInGames[player]!!.gameItems.guns["pistol-damage"]?.plus(0.0) ?: 2.0
                return
            }
        }
    }
}
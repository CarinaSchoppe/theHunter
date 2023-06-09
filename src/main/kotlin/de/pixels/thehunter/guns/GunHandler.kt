/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:42 PM All contents of "GunHandler.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.guns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.general.AmmoItems
import de.pixels.thehunter.util.game.management.GamesHandler
import org.bukkit.Sound
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent

class GunHandler : Listener {

    /**
     * Handles the player shooting event for different types of guns in the game.
     *
     * This event fires when a player interact with their gun (left or right click). Based on the player's action and the type of gun,
     * it calls the appropriate shooting or reloading functionality for the corresponding gun if the player has the required permission.
     */
    @EventHandler
    fun onPlayerShoot(event: PlayerInteractEvent) {
        if (event.item == null || !event.player.inventory.itemInMainHand.hasItemMeta() || !event.item!!.hasItemMeta() || !GamesHandler.playerInGames.containsKey(event.player)) return

        if (event.action.isLeftClick) {
            whenLeftClick(event)
        } else {
            whenRightClick(event)
        }
    }


    private fun whenLeftClick(event: PlayerInteractEvent) {
        when (event.item!!.itemMeta) {
            Rifle.gun.itemMeta -> {
                if (!event.player.hasPermission("thehunter.rifle")) return 
                Rifle.reload(event.player)
            }

            Minigun.gun.itemMeta -> {
                if (!event.player.hasPermission("thehunter.minigun")) return
                Minigun.reload(event.player)
            }

            Pistol.gun.itemMeta -> {
                if (!event.player.hasPermission("thehunter.pistol")) return 
                Pistol.reload(event.player)
            }

            Sniper.gun.itemMeta -> {
                if (!event.player.hasPermission("thehunter.sniper")) return 

                Sniper.reload(event.player)
            }
        }
    }

    private fun whenRightClick(event: PlayerInteractEvent) {
        when (event.item?.itemMeta) {
            Rifle.gun.itemMeta -> {
                if (!event.player.hasPermission("thehunter.Rifle")) return
                if (event.player.isSneaking) {
                    Rifle.reload(event.player)
                    return 
                }
                Rifle.shoot(event.player)
            }

            Minigun.gun.itemMeta -> {
                if (!event.player.hasPermission("thehunter.minigun")) return 
                if (event.player.isSneaking) {
                    Minigun.reload(event.player)
                    return
                }
                Minigun.shoot(event.player)
            }

            Pistol.gun.itemMeta -> {
                if (!event.player.hasPermission("thehunter.pistol")) return 
                if (event.player.isSneaking) {
                    Pistol.reload(event.player)
                    return
                }
                Pistol.shoot(event.player)
            }

            Sniper.gun.itemMeta -> {
                if (!event.player.hasPermission("thehunter.sniper")) return 
                if (event.player.isSneaking) {
                    Sniper.reload(event.player)
                    return
                }
                Sniper.shoot(event.player)
            }
        }
        return 
    }

    @EventHandler
    fun onGunDrop(event: PlayerDropItemEvent) {
        if (!event.itemDrop.itemStack.hasItemMeta()) return
        if (event.itemDrop.itemStack.itemMeta == Rifle.gun.itemMeta || event.itemDrop.itemStack.itemMeta == Minigun.gun.itemMeta || event.itemDrop.itemStack.itemMeta == Sniper.gun.itemMeta || event.itemDrop.itemStack.itemMeta == Pistol.gun.itemMeta) {
            event.isCancelled = true
            TheHunter.instance.messages.messagesMap["cant-drop-item"]?.let {
                event.player.sendMessage(
                    it.replace(
                        "%item%", event.itemDrop.itemStack.type.toString()
                    )
                )
            }
            return
        }
    }

    companion object {
        fun removeAmmo(player: Player, gun: Gun) {
            for (itemStack in player.inventory.contents) {
                if (itemStack == null) continue
                when (gun) {
                    is Rifle -> {
                        if (itemStack.itemMeta != AmmoItems.rifleAmmo.itemMeta) continue
                        itemStack.subtract(1)

                    }

                    is Minigun -> {
                        if (itemStack.itemMeta != AmmoItems.minigunAmmo.itemMeta)
                            continue
                        itemStack.subtract(1)
                    }

                    is Sniper -> {
                        if (itemStack.itemMeta != AmmoItems.sniperAmmo.itemMeta)
                            continue
                        itemStack.subtract(1)
                    }

                    is Pistol -> {
                        if (itemStack.itemMeta != AmmoItems.pistolAmmo.itemMeta)
                            continue
                        itemStack.subtract(1)
                    }
                }
            }
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
        println("shot was hit")
        when {
            Rifle.shotBullets[player]?.contains(arrow) == true -> {
                event.damage = GamesHandler.playerInGames[player]?.gameItems?.guns?.get("rifle-damage")?.plus(0.0) ?: 3.0
                Rifle.shotBullets[player]?.remove(arrow)
                return
            }

            Minigun.shotBullets[player]?.contains(arrow) == true -> {

                event.damage = GamesHandler.playerInGames[player]?.gameItems?.guns?.get("minigun-damage")?.plus(0.0) ?: 1.0
                Minigun.shotBullets[player]?.remove(arrow)

                return


            }

            Sniper.shotBullets[player]?.contains(arrow) == true -> {
                event.damage = GamesHandler.playerInGames[player]?.gameItems?.guns?.get("sniper-damage")?.plus(0.0) ?: 6.0
                Sniper.shotBullets[player]?.remove(arrow)
                return
            }

            Pistol.shotBullets[player]?.contains(arrow) == true -> {
                event.damage = GamesHandler.playerInGames[player]?.gameItems?.guns?.get("pistol-damage")?.plus(0.0) ?: 2.0
                Pistol.shotBullets[player]?.remove(arrow)

                return
            }
        }
    }
}

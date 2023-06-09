/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 9:58 PM All contents of "Gun.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.guns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.events.player.PlayerHotbarHover
import de.pixels.thehunter.util.game.management.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Gun {

    protected abstract var gunName: String
    protected abstract var ammoString: String
    protected abstract var ammo: ItemStack

    abstract var gun: ItemStack
    private val bulletDelay = mutableMapOf<Player, Boolean>()
    val shotBullets = mutableMapOf<Player, MutableSet<Arrow>>()
    private val reloading = mutableMapOf<Player, Boolean>()
    val magazine = mutableMapOf<Player, Int>()


    private fun bulletDelayMaker(player: Player) {
        bulletDelay[player] = true
        Bukkit.getScheduler().runTaskLater(TheHunter.instance, Runnable {
            bulletDelay[player] = false
        }, 5L * GamesHandler.playerInGames[player]?.gameItems?.guns?.get("$gunName-speed") as Int)
    }

    private fun shootProjectile(player: Player) {
        bulletDelayMaker(player)
        val arrow = player.launchProjectile(
            Arrow::class.java, player.location.direction.multiply(
                GamesHandler.playerInGames[player]?.gameItems?.guns?.get("$gunName-power") ?: return
            )
        )
        arrow.damage = 0.0
        player.playSound(player, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1f, 1f)
        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f)
        magazine[player] = magazine[player] as Int - 1
        arrow.shooter = player
        PlayerHotbarHover.updateHotbar(gun, player)

        if (shotBullets.containsKey(player)) {
            shotBullets[player]?.add(arrow)
        } else {
            shotBullets[player] = mutableSetOf(arrow)
        }
    }

    fun shoot(player: Player): Boolean {
        if (!reloading.containsKey(player)) {
            reloading[player] = false
        }
        if (!bulletDelay.containsKey(player))
            bulletDelay[player] = false
        if (!magazine.containsKey(player)) {
            magazine[player] = 0
            return false
        }
        if (magazine[player]!! <= 0) {
            reload(player)
            return false
        }

        if (bulletDelay[player] == false) shootProjectile(player)
        return true
    }

    private fun checkAmmoPossible(player: Player): Boolean {
        if (!player.inventory.containsAtLeast(ammo, 1)) {
            TheHunter.instance.messages.messagesMap["gun-out-of-ammo"]?.let { player.sendMessage(it) }
            return false
        }
        return true
    }


    fun reload(player: Player) {
        if (magazine.getOrDefault(
                player,
                0
            ) >= (GamesHandler.playerInGames[player]?.gameItems?.guns?.get(ammoString) ?: return)
        )
            return
        player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1f, 1f)
        if (reloading[player] == true) {
            TheHunter.instance.messages.messagesMap["gun-reloading"]?.let { player.sendMessage(it) }
            return
        }
        if (!checkAmmoPossible(player))
            return

        TheHunter.instance.messages.messagesMap["gun-reloading"]?.let { player.sendMessage(it) }
        reloading[player] = true
        Bukkit.getScheduler().scheduleSyncDelayedTask(TheHunter.instance, {
            reloading[player] = false
            val amount = getAmmoAmount(player, ammo)
            val old = magazine.getOrDefault(player, 0)
            if (amount + old >= GamesHandler.playerInGames[player]!!.gameItems.guns[ammoString]!!) {
                magazine[player] = GamesHandler.playerInGames[player]?.gameItems?.guns?.get(ammoString) ?: return@scheduleSyncDelayedTask
            } else
                magazine[player] = amount + old
            repeat(magazine[player]!! - old) {
                GunHandler.removeAmmo(player, this)
            }
            PlayerHotbarHover.updateHotbar(gun, player)

            player.playSound(player.location, Sound.BLOCK_ANVIL_USE, 1f, 1f)

            TheHunter.instance.messages.messagesMap["gun-reload-done"]?.let { player.sendMessage(it) }
        }, 20L * (GamesHandler.playerInGames[player]?.gameItems?.guns?.get("$gunName-reload") ?: return))
    }


    private fun getAmmoAmount(player: Player, ammo: ItemStack): Int {
        var amount = 0
        for (item in player.inventory.contents) {
            if (item == null || !item.hasItemMeta())
                continue
            if (item.itemMeta == ammo.itemMeta)
                amount += item.amount
        }
        return amount
    }
}

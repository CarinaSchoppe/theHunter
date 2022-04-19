/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 20:04 by Carina The Latest changes made by Carina on 19.04.22, 20:04 All contents of "Sniper.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.guns

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object Sniper {


    val shotBullets = mutableMapOf<Player, MutableSet<Arrow>>()
    var reloading = mutableMapOf<Player, Boolean>()
    var magazine = mutableMapOf<Player, Int>()
    fun createSniperGunItem(): ItemStack {
        return ItemBuilder(Material.DIAMOND_HOE).addDisplayName(TheHunter.PREFIX + "§7Sniper").addEnchantment(Enchantment.DURABILITY, 1).addLore("§7Right-click to shoot").build()
    }


    private fun shootProjectile(player: Player) {
        val arrow = player.launchProjectile(
            Arrow::class.java, player.location.direction.multiply(
                GamesHandler.playerInGames[player]!!.gameItems.guns["sniper-power"]!!
            )
        )
        arrow.damage = 0.0
        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f)
        GunHandler.removeAmmo(player, 1, de.carina.thehunter.items.chest.ammo.Sniper.createSniperAmmo().itemMeta)

        arrow.shooter = player
        if (shotBullets.containsKey(player)) {
            shotBullets[player]!!.add(arrow)
        } else {
            shotBullets[player] = mutableSetOf(arrow)
        }
    }

    fun shoot(player: Player): Boolean {
        if (!reloading.containsKey(player)) {
            reloading[player] = false
        }

        if (!magazine.containsKey(player)) {
            magazine[player] = 0
            return false
        }
        if (magazine[player]!! <= 0) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-out-of-ammo"]!!)
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reloading"]!!)
            reloadGun(player)
            return false
        }

        shootProjectile(player)
        return true
    }

    private fun checkAmmoPossible(player: Player): Boolean {
        if (!hasAmmo(player)) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-out-of-ammo"]!!)
            return false
        }

        return true
    }

    fun reloadGun(player: Player) {
        player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1f, 1f)

        if (reloading[player] == true) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reloading"]!!)
            return
        }
        if (!checkAmmoPossible(player))
            return
        player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reloading"]!!)
        reloading[player] = true
        reload(player)
    }

    private fun reload(player: Player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(TheHunter.instance, {
            reloading[player] = false
            val amount = getAmmoAmount(player, de.carina.thehunter.items.chest.ammo.Sniper.createSniperAmmo())
            if (amount < GamesHandler.playerInGames[player]!!.gameItems.guns["sniper-ammo"]!!)
                magazine[player] = GamesHandler.playerInGames[player]!!.gameItems.guns["sniper-ammo"]!!
            else
                magazine[player] = amount
            player.playSound(player.location, Sound.BLOCK_LAVA_POP, 1f, 1f)

            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reload-done"]!!)
        }, 20L * GamesHandler.playerInGames[player]!!.gameItems.guns["sniper-reload"]!!)
    }

    private fun hasAmmo(player: Player): Boolean {
        return return player.inventory.containsAtLeast(de.carina.thehunter.items.chest.ammo.Sniper.createSniperAmmo(), 1)

    }

    private fun getAmmoAmount(player: Player, ammo: ItemStack): Int {
        var amount = 0
        for (item in player.inventory.contents!!) {
            if (item == null)
                continue
            if (!item.hasItemMeta())
                continue
            if (item.itemMeta == ammo.itemMeta)
                amount += item.amount
        }
        return amount
    }
}
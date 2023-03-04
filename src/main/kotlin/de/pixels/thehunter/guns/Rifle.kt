/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 7:22 PM All contents of "Ak.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.guns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.events.player.PlayerHotbarHover
import de.pixels.thehunter.items.AmmoItems
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object Rifle : Gun {

    val shotBullets = mutableMapOf<Player, MutableSet<Arrow>>()
    private val reloading = mutableMapOf<Player, Boolean>()
    val magazine = mutableMapOf<Player, Int>()
    val rifle = ItemBuilder(Material.IRON_HOE).addDisplayName(TheHunter.prefix + "§7AK-47")
        .addEnchantment(Enchantment.DURABILITY, 1).addLore("§7Right-click to shoot").build()
    private val bulletDelay = mutableMapOf<Player, Boolean>()

    private fun bulletDelayMaker(player: Player) {
        bulletDelay[player] = true
        Bukkit.getScheduler().runTaskLater(TheHunter.instance, Runnable {
            bulletDelay[player] = false
        }, 5L * GamesHandler.playerInGames[player]!!.gameItems.guns["rifle-speed"] as Int)
    }

    private fun shootProjectile(player: Player) {
        bulletDelayMaker(player)
        val arrow = player.launchProjectile(
            Arrow::class.java,
            player.location.direction.multiply(GamesHandler.playerInGames[player]!!.gameItems.guns["rifle-power"]!!)
        )
        arrow.damage = 0.0
        player.playSound(player, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1f, 1f)
        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f)
        arrow.shooter = player
        magazine[player] = magazine[player]!! - 1
        PlayerHotbarHover.updateHotbar(rifle, player)

        if (shotBullets.containsKey(player)) {
            shotBullets[player]!!.add(arrow)
        } else {
            shotBullets[player] = mutableSetOf(arrow)
        }
    }


    override fun shoot(player: Player): Boolean {
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

        if (!bulletDelay[player]!!) shootProjectile(player)

        return true
    }

    private fun checkAmmoPossible(player: Player): Boolean {
        if (!player.inventory.containsAtLeast(AmmoItems.akAmmo, 1)) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-out-of-ammo"]!!)
            return false
        }
        return true
    }


    override fun reload(player: Player) {
        if (magazine.getOrDefault(player, 0) >= GamesHandler.playerInGames[player]!!.gameItems.guns["rifle-ammo"]!!)
            return
        player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1f, 1f)
        if (reloading[player] == true) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reloading"]!!)
            return
        }
        if (!checkAmmoPossible(player)) return
        player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reloading"]!!)
        reloading[player] = true
        Bukkit.getScheduler().scheduleSyncDelayedTask(TheHunter.instance, {
            reloading[player] = false
            val amount = getAmmoAmount(player, AmmoItems.akAmmo)
            val old = magazine.getOrDefault(player, 0)
            if (amount + old >= GamesHandler.playerInGames[player]!!.gameItems.guns["rifle-ammo"]!!) {
                magazine[player] = GamesHandler.playerInGames[player]!!.gameItems.guns["rifle-ammo"]!!
            } else
                magazine[player] = amount + old
            repeat(magazine[player]!! - old) {
                GunHandler.removeAmmo(player, Sniper)
            }
            PlayerHotbarHover.updateHotbar(rifle, player)
            player.playSound(player.location, Sound.BLOCK_ANVIL_USE, 1f, 1f)
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reload-done"]!!)
        }, 20L * GamesHandler.playerInGames[player]!!.gameItems.guns["rifle-reload"]!!)
    }


    private fun getAmmoAmount(player: Player, ammo: ItemStack): Int {
        var amount = 0
        for (item in player.inventory.contents) {
            if (item == null) continue
            if (!item.hasItemMeta()) continue
            if (item.itemMeta == ammo.itemMeta) amount += item.amount
        }
        return amount
    }
}

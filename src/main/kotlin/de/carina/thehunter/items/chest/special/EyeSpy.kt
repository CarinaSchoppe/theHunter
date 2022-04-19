/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 19:43 by Carina The Latest changes made by Carina on 19.04.22, 19:43 All contents of "EyeSpy.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.items.chest.ItemHandler
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.title.Title
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class EyeSpy : Listener {

    companion object {
        val inEyeSpy = mutableSetOf<Player>()
        val lastPlayerLocation = mutableMapOf<Player, Location>()
        private val mapPlayerTime = mutableMapOf<Player, Int>()
        fun createEyeSpyItem(): ItemStack {
            return ItemBuilder(Material.ENDER_EYE).addDisplayName(TheHunter.PREFIX + "§aEye Spy").addLore("§7This Article will let you see the other player for 10 sec").addLore("§7Right-click to activate").build()
        }
    }


    @EventHandler
    fun onEyeSpyUse(event: PlayerInteractEvent) {
        if (!ItemHandler.shouldInteractWithItem(event, createEyeSpyItem(), "EyeSpy"))
            return
        if (inEyeSpy.contains(event.player))
            return
        val targets = GamesHandler.playerInGames[event.player]!!.players.filter { it != event.player }
        if (targets.isEmpty())
            return
        val target = targets.random()
        ItemHandler.removeOneItemOfPlayer(event.player)
        event.isCancelled = true

        setCamera(event.player, target)
    }


    private fun setCamera(player: Player, target: Player) {
        lastPlayerLocation[player] = player.location
        player.teleport(target.location)
        inEyeSpy.add(player)
        player.gameMode = GameMode.SPECTATOR
        player.spectatorTarget = target
        mapPlayerTime[player] = (TheHunter.instance.itemSettings.settingsMap["eye-spy-duration"]!! as Long).toInt()
        TheHunter.instance.server.scheduler.scheduleSyncRepeatingTask(TheHunter.instance, {
            mapPlayerTime[player] = mapPlayerTime[player]!! - 1
            when (mapPlayerTime[player]!!) {
                3 -> {
                    player.showTitle(Title.title(LegacyComponentSerializer.legacySection().deserialize("§6${mapPlayerTime[player]}"), Component.text("")))
                }
                2 -> {
                    player.showTitle(Title.title(LegacyComponentSerializer.legacySection().deserialize("§6${mapPlayerTime[player]}"), Component.text("")))
                }
                1 -> {
                    player.showTitle(Title.title(LegacyComponentSerializer.legacySection().deserialize("§6${mapPlayerTime[player]}"), Component.text("")))
                }
            }
        }, 20L, 20L)

        TheHunter.instance.server.scheduler.scheduleSyncDelayedTask(TheHunter.instance, {
            player.teleport(lastPlayerLocation[player]!!)
            player.gameMode = GameMode.SURVIVAL
            player.spectatorTarget = null
            inEyeSpy.remove(player)
        }, 20L * TheHunter.instance.itemSettings.settingsMap["eye-spy-duration"]!! as Long)
    }

}

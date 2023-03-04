/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:46 PM All contents of "PlayerDies.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.events.player

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.builder.Items
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.game.ingame.DeathHandler
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeath : Listener {

    @EventHandler
    fun onPlayerKillsOther(event: PlayerDeathEvent) {
        if (event.entity.killer != null && event.entity.killer is Player)
            playerKilledOther(event, event.entity, event.entity.killer as Player)
        else
            DeathHandler(event.player).deathPreChecks()
                ?.squeezeIns { run { event.deathMessage(Component.text { "" }) } }
                ?.deathMessageToAll()?.playerDiedStatsHandling()?.deathChestCreation()?.afterDeathPlayerHandling()
                ?.afterDeathChecks()
    }

    private fun playerKilledOther(event: PlayerDeathEvent, player: Player, killer: Player) {
        if (!GamesHandler.playerInGames.containsKey(killer))
            return
        if (GamesHandler.playerInGames[player] != GamesHandler.playerInGames[killer])
            return
        val game = GamesHandler.playerInGames[player]!!
        killer.level += 1
        game.currentGameKills[killer] = game.currentGameKills.getOrDefault(killer, 0) + 1
        killer.playSound(killer, Sound.ITEM_GOAT_HORN_SOUND_7, 1f, 1f)
        DeathHandler(player, killer).deathPreChecks()
            ?.squeezeIns { run { event.deathMessage(Component.text { "" }) } }
            ?.deathMessageToAll()?.playerDiedStatsHandling()?.deathChestCreation()?.afterDeathPlayerHandling()
            ?.afterDeathChecks()


    }


    companion object {
        fun generalHandling(player: Player, game: Game) {
            game.players.remove(player)
            game.spectators.add(player)
            GamesHandler.playerInGames.remove(player)
            GamesHandler.spectatorInGames[player] = game
            player.inventory.clear()
            player.inventory.setItem(8, Items.leaveItem)
            player.inventory.setItem(9, Items.leaveItem)
            player.teleport(game.spectatorLocation!!)
            player.allowFlight = true
            Bukkit.getOnlinePlayers().forEach {
                it.hidePlayer(TheHunter.instance, player)
                if (!game.players.contains(it))
                    player.hidePlayer(TheHunter.instance, it)
                if (game.players.contains(it) || game.spectators.contains(it))
                    game.scoreBoard.createNewScoreboard(it)
            }
        }
    }

}

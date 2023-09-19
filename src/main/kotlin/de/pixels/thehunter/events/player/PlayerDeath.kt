/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:46 PM All contents of "PlayerDies.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.events.player

import de.pixels.thehunter.util.misc.DeathHandler
import de.pixels.thehunter.util.game.management.GamesHandler
import net.kyori.adventure.text.Component
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeath : Listener {

    /**
     * Handles the event when a player kills another player.
     *
     * @param event The PlayerDeathEvent associated with the player kill.
     */
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

    /**
     * Handles the logic when a player is killed by another player.
     *
     * @param event The PlayerDeathEvent triggered when the player is killed.
     * @param player The Player who was killed.
     * @param killer The Player who killed the other player.
     */
    private fun playerKilledOther(event: PlayerDeathEvent, player: Player, killer: Player) {
        if (!GamesHandler.playerInGames.containsKey(killer) || GamesHandler.playerInGames[player] != GamesHandler.playerInGames[killer])
            return

        val game = GamesHandler.playerInGames[player] ?: return
        killer.level += 1
        game.currentGameKills[killer] = game.currentGameKills.getOrDefault(killer, 0) + 1
        killer.playSound(killer, Sound.ITEM_GOAT_HORN_SOUND_7, 1f, 1f)
        DeathHandler(player, killer).deathPreChecks()
            ?.squeezeIns { run { event.deathMessage(Component.text { "" }) } }
            ?.deathMessageToAll()?.playerDiedStatsHandling()?.deathChestCreation()?.afterDeathPlayerHandling()
            ?.afterDeathChecks()


    }


}

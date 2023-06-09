/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 9:25 PM All contents of "PlayerChat.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.events.player

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.game.management.GamesHandler
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class PlayerChat : Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncChatEvent) {
        if (!(GamesHandler.playerInGames.containsKey(event.player) || GamesHandler.spectatorInGames.containsKey(event.player))) {
            event.viewers().removeAll(GamesHandler.playerInGames.keys)
            event.viewers().removeAll(GamesHandler.spectatorInGames.keys)
            return
        }

        event.isCancelled = true
        if (LegacyComponentSerializer.builder().build().serialize(event.message()).startsWith("@Team")) {
            val message =
                TheHunter.prefix + "§7[TEAM] ${if (GamesHandler.spectatorInGames.containsKey(event.player)) "[Spectator] " else ""}[§6${event.player.name}§7]§f " + LegacyComponentSerializer.builder()
                    .build().serialize(event.message()).replace("@Team", "")

            val team = GamesHandler.playerInGames[event.player]?.teams?.find {
                it.teamMembers.contains(event.player)
            } ?: GamesHandler.spectatorInGames[event.player]?.teams?.find {
                it.teamMembers.contains(event.player)
            }

            if (team != null) {
                team.teamMembers.forEach {
                    if (GamesHandler.playerInGames.containsKey(event.player) || GamesHandler.spectatorInGames.containsKey(it)) 
                        it.sendMessage(message)
                }
                return
            }
        }
        if (GamesHandler.playerInGames.containsKey(event.player)) {
            val message =
                TheHunter.prefix + "§7${if (GamesHandler.spectatorInGames.containsKey(event.player)) "[Spectator] " else ""}[§6${event.player.name}§7]§f " + LegacyComponentSerializer.builder()
                    .build().serialize(event.message()).replace("@Team", "")
            if (!GamesHandler.spectatorInGames.containsKey(event.player))
                GamesHandler.playerInGames[event.player]?.players?.forEach {
                    it.sendMessage(message)
                }
            GamesHandler.playerInGames[event.player]?.spectators?.forEach {
                it.sendMessage(message)
            }
            return
        }
        val message =
            TheHunter.prefix + "§7[Dead] " + "§7[§6${event.player.name}§7]§f " + LegacyComponentSerializer.builder()
                .build().serialize(event.message())
        GamesHandler.spectatorInGames[event.player]?.spectators?.forEach {
            it.sendMessage(message)
        }


    }
}

/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 16.04.22, 12:01 by Carina The Latest changes made by Carina on 16.04.22, 12:01 All contents of "Messages.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.files

import de.carina.thehunter.TheHunter
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class Messages(filePath: String) : BaseFile(filePath) {

    val messagesMap = mutableMapOf<String, String>()
    override fun addData(): Messages {
        yml.addDefault("start-up-message-successfully", "&aThe Plugin was successfully loaded!")
        yml.addDefault("shutdown-message-successfully", "&cThe Plugin was successfully unloaded!")
        yml.addDefault("team-player-not-leader", "&cYou are not the leader of a team!")
        yml.addDefault("team-player-already-in-own-team", "&The player &6%player%&c is already in your team!")
        yml.addDefault("team-player-already-in-other-team", "&cThe player &c%player%&6 is already in an other team!")
        yml.addDefault("player-not-permissions", "&cYou don't have the permissions to do this!")
        yml.addDefault("player-not-online", "&cThe player &6%player% &cis not online!")
        yml.addDefault("player-not-in-game", "&cThe player &6%player%&c is not in the game!")
        yml.addDefault("player-already-invited", "&cThe player &6%player%&c is already invited!")
        yml.addDefault("player-invited", "&aThe player &6%player% &agot invited!")
        yml.addDefault("player-joined-team", "&aYou joined the team &6%team%&a!")
        yml.addDefault("player-left-team", "&aYou left the team!")
        yml.addDefault("player-not-in-team", "&cYou are not in a team!")
        yml.addDefault("player-is-invited", "&aYou are invited to join the team of &6%leader%&7!")
        yml.addDefault("player-joined-team-all", "&aThe player &6%player% &ajoined the team!")
        yml.addDefault("player-new-leader", "&aYou are now the leader of the team!")
        yml.addDefault("player-new-leader-all", "&aThe player &6%player% &ais now the leader of the team!")
        yml.addDefault("player-not-in-team", "&The player &6%player% is not in your team!")
        yml.addDefault("player-promote-leader", "&aYou promoted the player &6%player% &ato leader!")
        yml.addDefault("team-full", "&Your team is full!")
        yml.addDefault("player-left-team-all", "&cThe player &6%player% &cleft the team!")
        yml.addDefault("teams-not-allowed", "&cTeams are not allowed!")
        yml.addDefault("game-waiting-for-players", "&7Waiting for players to join the game [&6%current%&7/&6%max%&7]!")
        yml.addDefault("game-starting-in", "&7The game will start in &6%time%&7 seconds!")
        yml.addDefault("game-starting", "&aThe game starts!")
        yml.addDefault("no-permission", "&cYou don't have the permissions to do this!")
        yml.addDefault("not-enough-arguments", "&cYou need at least %arguments% arguments!")
        yml.addDefault("stats-not-found", "&cThe stats of the player &6%player% &cwas not found!")
        yml.addDefault("no-command-found", "&cThe command &6%command% &cwas not found!")
        yml.addDefault("not-a-player", "&cYou must be a player to do this!")
        yml.addDefault("egg-bomb-message", "&7Egg createsa an explosion with &6%power%&7 power!")
        yml.addDefault("stats-message-own", "&7Your stats: &6%kills% &7Kills, &6%deaths% &7Deaths, &6%kd% &7K/D, &6%wins% &7Wins, &6%losses% &7Losses, &6%points% &7Points, &6%games% &7Games!")
        yml.addDefault("stats-message-other", "&7The stats of &6%player% &7: &6%kills% &7Kills, &6%deaths% &7Deaths, &6%kd% &7K/D, &6%wins% &7Wins, &6%losses% &7Losses, &6%points% &7Points, &6%games% &7Games!")
        yml.addDefault("stats-system-saved", "&7The stats of all players were saved!")
        yml.addDefault("healer-message", "&7You have been healed with &6%heal%&7!")
        yml.addDefault("eye-spy-message", "&7You are now spying on &6%player%&7!")
        yml.addDefault("eye-spy-message-back", "&7You are now back in your view!")
        super.addData()
        loadMessagesToMap()
        return this
    }

    private fun loadMessagesToMap() {
        for (key in yml.getKeys(false)) {
            messagesMap[key] = getMessageWithPrefix(key)
        }
    }

    fun sendMessageToPlayer(player: Player, messagePath: String) {
        player.sendMessage(messagesMap[messagePath]!!)
    }

    fun sendMessageToConsole(messagePath: String) {
        Bukkit.getConsoleSender().sendMessage(messagesMap[messagePath]!!)
    }


    private fun getMessageWithPrefix(path: String): String {
        return TheHunter.PREFIX + getColorCodedString(path)

    }

}
/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 19.04.22, 10:59 by Carina The Latest changes made by Carina on 19.04.22, 10:59 All contents of "Messages.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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
        yml.addDefault("egg-bomb-message", "&7Egg creates an explosion with &6%power%&7 power!")
        yml.addDefault("stats-message-own", "&7Your stats: &6%kills% &7Kills, &6%deaths% &7Deaths, &6%kd% &7K/D, &6%wins% &7Wins, &6%losses% &7Losses, &6%points% &7Points, &6%games% &7Games!")
        yml.addDefault("stats-message-other", "&7The stats of &6%player% &7: &6%kills% &7Kills, &6%deaths% &7Deaths, &6%kd% &7K/D, &6%wins% &7Wins, &6%losses% &7Losses, &6%points% &7Points, &6%games% &7Games!")
        yml.addDefault("stats-system-saved", "&7The stats of all players were saved!")
        yml.addDefault("healer-message", "&7You have been healed with &6%heal%&7!")
        yml.addDefault("eye-spy-message", "&7You are now spying on &6%player%&7!")
        yml.addDefault("eye-spy-message-back", "&7You are now back in your view!")
        yml.addDefault("jumpstick-broke", "&cYou can't use this item anymore!")
        yml.addDefault("player-swapped", "&aYou swapped the place with &6%player%&7!")
        yml.addDefault("tracker-distance", "&7The player &6%player%&7 is now &6%distance%&7 blocks away!")
        yml.addDefault("food-recharge", "&7Your food has been recharged with &6%recharge%&7!")
        yml.addDefault("energydrink-consumed", "&7You consumed an energy drink!")
        yml.addDefault("wrong-config", "&cThe config for the game &6%game%&c is wrong! Please check it!")
        yml.addDefault("gun-reload-done", "&7You reloaded your gun!")
        yml.addDefault("gun-reloading", "&7You are reloading your gun!")
        yml.addDefault("gun-out-of-ammo", "&cYou don't have any ammo left for that gun!")
        yml.addDefault("cant-drop-item", "&cYou can't drop &6%item%&7!")
        yml.addDefault("cant-team-damage", "&cYou can't damage your team members!")
        yml.addDefault("cant-break-block", "&cYou can't break &6%block%&7!")
        yml.addDefault("cant-place-block", "&cYou can't place &6%block%&7!")
        yml.addDefault("immunity-off", "&7You are no longer immune!")
        yml.addDefault("immunity-message", "&7You are immune for &6%time%&7 seconds!")
        yml.addDefault("player-won", "&aThe player &6%player% &awon the game!")
        yml.addDefault("team-won", "&aThe team with leader &6%leader% &awon the game!")
        yml.addDefault("game-over", "&aThe game is over!")
        yml.addDefault("endcountdown-message", "&7The game ends in &6%time%&7 seconds!")
        yml.addDefault("player-own-died", "&cYou died!")
        yml.addDefault("player-died", "&cThe player &6%player% &cdied!")
        yml.addDefault("player-own-killed-by-other", "&cYou were killed by &6%player%&c!")
        yml.addDefault("player-killed-by-other", "&cThe player &6%player% &cwas killed by &6%killer%&c!")
        yml.addDefault("player-quit", "&cThe player &6%player% &cquit the game!")
        yml.addDefault("player-own-quit", "&cYou quit the game!")
        yml.addDefault("game-speedup", "&7The game will start soon!")
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
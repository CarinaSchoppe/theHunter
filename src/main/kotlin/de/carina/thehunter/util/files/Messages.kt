/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 07.04.22, 20:18 by Carina The Latest changes made by Carina on 07.04.22, 20:18 All contents of "Messages.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.files

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class Messages(filePath: String) : BaseFile(filePath) {

    override fun addData() {
        yml.addDefault("start-up-message-successfully", "&aThe Plugin was successfully loaded!")
        yml.addDefault("shutdown-message-successfully", "&cThe Plugin was successfully unloaded!")
        yml.addDefault("team-player-not-leader", "&cYou are not the leader of a team!")
        yml.addDefault("team-player-already-in-own-team", "&The player &6%player%&c is already in your team!")
        yml.addDefault("team-player-already-in-other-team", "&cThe player &c%player%&6 is already in an other team!")
        yml.addDefault("player-not-permissions", "&cYou don't have the permissions to do this!")
        yml.addDefault("player-not-online", "&cThe player &6%player% &cis not online!")
        yml.addDefault("player-not-in-game", "&cThe player &6%player%&c is not in the game!")
        yml.addDefault("player-allready-inivited", "&cThe player &6%player%&c is already invited!")
        yml.addDefault("player-invited", "&aThe player &6%player% &agot invited!")
        yml.addDefault("player-joined-team", "&aYou joined the team &6%team%&a!")
        yml.addDefault("player-left-team", "&aYou left the team &6%team%&a!")
        yml.addDefault("player-is-invited", "&aYou are invited to join the team of &6%leader%&7!")
        yml.addDefault("player-joined-team-all", "&aThe player &6%player% &ajoined the team!")
        yml.addDefault("player-left-team-all", "&cThe player &6%player% &cleft the team!")
        super.addData()
    }

    fun sendMessageToPlayer(player: Player, messagePath: String) {
        player.sendMessage(getMessageWithPrefix(messagePath))
    }

    fun sendMessageToConsole(messagePath: String) {
        Bukkit.getConsoleSender().sendMessage(getMessageWithPrefix(messagePath))
    }


}
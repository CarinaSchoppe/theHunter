/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 07.04.22, 15:06 by Carina The Latest changes made by Carina on 07.04.22, 15:06 All contents of "Messages.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
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

        super.addData()
    }

    fun sendMessageToPlayer(player: Player, messagePath: String) {
        player.sendMessage(getMessageWithPrefix(messagePath))
    }

    fun sendMessageToConsole(messagePath: String) {
        Bukkit.getConsoleSender().sendMessage(getMessageWithPrefix(messagePath))
    }


}
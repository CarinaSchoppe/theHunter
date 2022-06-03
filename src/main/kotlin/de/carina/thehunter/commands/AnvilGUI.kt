/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/3/22, 1:45 PM by Carina The Latest changes made by Carina on 6/3/22, 1:45 PM All contents of "AnvilGUI.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.Items
import de.carina.thehunter.util.misc.Permissions
import net.wesjd.anvilgui.AnvilGUI
import net.wesjd.anvilgui.AnvilGUI.Response
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.function.BiFunction

class AnvilGUI {
    fun create(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, "gui", 0, Permissions.ANVIL_GUI))
            return

        (sender as Player).closeInventory()
        AnvilGUI.Builder().itemLeft(Items.nameTag).title("§6Game Name").plugin(TheHunter.instance).onComplete(BiFunction { player, text ->
            player.performCommand("theHunter setup create $text")
            return@BiFunction Response.close()
        }).open(sender)

    }

}
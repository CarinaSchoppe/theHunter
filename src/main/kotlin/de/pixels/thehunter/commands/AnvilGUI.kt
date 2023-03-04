/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 10:30 PM All contents of "AnvilGUI.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.commands

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.builder.Items
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import net.wesjd.anvilgui.AnvilGUI
import net.wesjd.anvilgui.AnvilGUI.Response
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.function.BiFunction

class AnvilGUI {
    fun create(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.GUI_COMMAND,
                0,
                Permissions.ANVIL_GUI
            )
        )
            return

        (sender as Player).closeInventory()
        AnvilGUI.Builder().itemLeft(Items.nameTag).title("Game Name").plugin(TheHunter.instance)
            .onComplete(BiFunction { player, text ->
                player.performCommand("theHunter setup create $text")
                return@BiFunction Response.close()
            }).open(sender)

    }

}

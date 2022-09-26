/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 6:08 PM All contents of "GameSigns.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.game

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.EndState
import de.carina.thehunter.util.misc.Permissions
import de.carina.thehunter.util.misc.Util
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.SignChangeEvent
import org.bukkit.event.player.PlayerInteractEvent

class GameSigns : Listener {
    @EventHandler
    fun onSignClick(event: PlayerInteractEvent) {
        if (!event.action.isRightClick)
            return
        if (event.clickedBlock == null)
            return
        if (event.clickedBlock!!.state !is Sign)
            return
        val sign = event.clickedBlock!!.state as Sign
        val game = GamesHandler.games.find { it.name == PlainTextComponentSerializer.plainText().serialize(sign.line(1)) } ?: return
        if (game.currentGameState is EndState || !event.player.hasPermission(Permissions.SIGN_JOIN))
            return

        if (sign.line(0) == LegacyComponentSerializer.legacySection().deserialize(TheHunter.prefix)) {
            event.isCancelled = true
            event.player.performCommand("theHunter join " + PlainTextComponentSerializer.plainText().serialize(sign.line(1)))
            game.signs.add(sign)
            Util.updateGameSigns(game)
        }
    }

    @EventHandler
    fun onSignCreate(event: SignChangeEvent) {
        if (!event.player.hasPermission("theHunter.signcreate"))
            return
        if (PlainTextComponentSerializer.plainText().serialize(event.line(0)!!).lowercase() != "[thehunter]")
            return
        if (event.line(1) == null)
            return

        val game = GamesHandler.games.find { it.name == PlainTextComponentSerializer.plainText().serialize(event.line(1)!!) } ?: return

        event.line(0, LegacyComponentSerializer.legacySection().deserialize(TheHunter.prefix))
        event.line(1, LegacyComponentSerializer.legacySection().deserialize("§6" + game.name))
        event.line(2, LegacyComponentSerializer.legacySection().deserialize("§7[§6" + game.players.size + " §7|§6 " + game.maxPlayers + "§7]"))
        event.line(3, LegacyComponentSerializer.legacySection().deserialize("§aLOBBY"))
        val sign = event.block.state as Sign
        game.signs.add(sign)
        sign.update(true)

    }
}

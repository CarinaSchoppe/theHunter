package de.pixels.thehunter.commands.ingame

import de.pixels.thehunter.commands.util.CommandUtil
import de.pixels.thehunter.gamestates.LobbyState
import de.pixels.thehunter.util.builder.Inventories
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class PerksMenu {


    fun openPerkMenu(sender: CommandSender, command: String, args: Array<out String>) {
        if (sender !is Player || !GamesHandler.playerInGames.containsKey(sender))
            return
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.PERK_MENU_COMAND,
                0,
                Permissions.PERKS_MENU_COMMAND
            ) || !GamesHandler.playerInGames.containsKey(sender)
        )
            return

        val game = GamesHandler.playerInGames[sender] ?: return
        if (game.currentGameState !is LobbyState)
            return
        sender.openInventory(Inventories.perksInventory)
    }

}

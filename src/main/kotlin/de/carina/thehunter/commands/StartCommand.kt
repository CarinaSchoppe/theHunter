package de.carina.thehunter.commands

import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class StartCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player)
            return false

        if (!GamesHandler.playerInGames.containsKey(sender) && !GamesHandler.spectatorInGames.containsKey(sender))
            return false
        sender.performCommand("thehunter start")
        return false
    }
}

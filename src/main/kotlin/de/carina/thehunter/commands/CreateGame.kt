/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "CreateGame.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.commands

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.Inventories
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.game.GamesHandler
import de.carina.thehunter.util.misc.Permissions
import de.carina.thehunter.util.misc.Util
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CreateGame {
    fun create(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, "setup", 2, Permissions.SETUP_COMMAND))
            return

        when (args[0].lowercase()) {
            "create" -> {
                if (GamesHandler.games.find { it.name == args[1] } != null) {
                    sender.sendMessage(TheHunter.instance.messages.messagesMap["game-already-exists"]!!.replace("%game%", args[1]))
                    return
                }
                val game = Game(args[1])
                game.create()
                Util.currentGameSelected[sender as Player] = game
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-successfully-created"]!!.replace("%game%", args[1]))
                sender.openInventory(Inventories.createSettingsInventory(game))
            }

            "select" -> {
                val game = GamesHandler.games.find { it.name == args[1] }
                if (game == null) {
                    sender.sendMessage(TheHunter.instance.messages.messagesMap["game-not-exists"]!!.replace("%game%", args[1]))
                    return
                }
                Util.currentGameSelected[sender as Player] = game
            }

            "config" -> {
                configGame(args, sender as Player)
            }

            else -> {
                sender.sendMessage(TheHunter.instance.messages.messagesMap["no-command-found"]!!.replace("%command%", args[0]))
            }
        }
    }

    private fun configGame(args: Array<out String>, sender: CommandSender) {
        if (args.size != 3) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["not-enough-arguments"]!!.replace("%arguments%", 3.toString()))
            return
        }
        val game = GamesHandler.setupGames.find { it.name == args[2] }
        if (game == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["game-not-exists"]!!.replace("%game%", args[2]))
            return
        }
        when (args[1].lowercase()) {
            "lobbyspawn" -> {
                game.lobbyLocation = (sender as Player).location
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-lobby-set"]!!.replace("%game%", args[2]))
            }

            "spectatorspawn" -> {
                game.spectatorLocation = (sender as Player).location
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-spectator-set"]!!.replace("%game%", args[2]))
            }

            "backspawn" -> {
                game.backLocation = (sender as Player).location
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-back-set"]!!.replace("%game%", args[2]))
            }

            "endspawn" -> {
                game.endLocation = (sender as Player).location
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-end-set"]!!.replace("%game%", args[2]))
            }

            "arenacenter" -> {
                game.arenaCenter = (sender as Player).location
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-arena center-set"]!!.replace("%game%", args[2]))

            }

            "playerspawn" -> {
                if (game.playerSpawns.size < game.maxPlayers) {
                    game.playerSpawns.add((sender as Player).location)
                    sender.sendMessage(TheHunter.instance.messages.messagesMap["game-spawn-set"]!!.replace("%id%", (game.playerSpawns.size - 1).toString()).replace("%game%", args[2]))
                } else
                    sender.sendMessage(TheHunter.instance.messages.messagesMap["game-spawns-to-much"]!!.replace("%game%", args[2]).replace("%max%", game.maxPlayers.toString()))
            }

            "finish" -> {
                if (game.isGameInvalidConfigured()) {
                    sender.sendMessage(TheHunter.instance.messages.messagesMap["wrong-config"]!!.replace("%game%", args[2]))
                    return
                }
                game.finish()
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-successfully-created"]!!.replace("%game%", args[2]))
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-successfully-saved"]!!.replace("%game%", args[2]))
            }
        }

    }
}
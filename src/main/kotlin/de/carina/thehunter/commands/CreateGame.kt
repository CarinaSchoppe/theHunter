/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carivalna Sophie Schoppe Any2022
 * File created on 8/9/22, 1:24 PM by Carina The Latest changes made by Carina on 8/9/22, 1:19 PM All contents of "CreateGame.kt" are protected by copyright.
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
import de.carina.thehunter.util.misc.ConstantStrings
import de.carina.thehunter.util.misc.Permissions
import de.carina.thehunter.util.misc.Util
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CreateGame {
    fun create(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(sender, command, args, ConstantStrings.SETUP_COMMAND, 2, Permissions.SETUP_COMMAND)) return

        when (args[0].lowercase()) {
            ConstantStrings.CREATE_COMMAND -> {
                if (GamesHandler.games.find { it.name == args[1] } != null) {
                    sender.sendMessage(TheHunter.instance.messages.messagesMap["game-already-exists"]!!.replace(ConstantStrings.GAME_PERCENT, args[1]))
                    return
                }
                if (GamesHandler.setupGames.find { it.name == args[1] } != null) {
                    Util.currentGameSelected[sender as Player] = GamesHandler.setupGames.find { it.name == args[1] }!!
                    sender.openInventory(Inventories.setupGameInventory(Util.currentGameSelected[sender]!!))
                    return
                }
                val game = Game(args[1])
                game.create()
                Util.currentGameSelected[sender as Player] = game
                sender.playSound(sender.location, Sound.BLOCK_ANVIL_USE, 1f, 1f)
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-successfully-created"]!!.replace(ConstantStrings.GAME_PERCENT, args[1]))
                sender.openInventory(Inventories.setupGameInventory(game))
            }

            ConstantStrings.SELECT_COMMAND -> {
                val game = GamesHandler.games.find { it.name == args[1] }
                if (game == null) {
                    sender.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.GAME_NOT_EXISTS]!!.replace(ConstantStrings.GAME_PERCENT, args[1]))
                    return
                }
                (sender as Player).playSound(sender.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)

                Util.currentGameSelected[sender] = game
            }

            ConstantStrings.CONFIG_COMMAND -> {
                configGame(args, sender as Player)
            }

            ConstantStrings.REMOVE_COMMAND -> {
                removeLocation(args, sender as Player)
            }

            else -> {
                sender.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.COMMAND_NOT_FOUND]!!.replace(ConstantStrings.COMMAND_PERCENT, args[0]))
            }
        }
    }

    private fun removeLocation(args: Array<out String>, sender: CommandSender) {
        if (args.size != 3) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.NOT_ENOUGH_ARGUMENTS]!!.replace(ConstantStrings.ARGUMENTS_PERCENT, 3.toString()))
            return
        }
        val game = GamesHandler.setupGames.find { it.name == args[2] }
        if (game == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap[ConstantStrings.GAME_NOT_EXISTS]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            return
        }
        (sender as Player).playSound(sender.location, Sound.BLOCK_ANVIL_BREAK, 1f, 1f)
        when (args[1].lowercase()) {

            ConstantStrings.LOBBY_SPAWN -> {
                game.lobbyLocation = null
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-lobby-removed"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            }

            ConstantStrings.SPECTATOR_SPAWN -> {
                game.spectatorLocation = null
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-spectator-removed"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            }

            ConstantStrings.BACK_SPAWN -> {
                game.backLocation = null
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-back-removed"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            }

            ConstantStrings.END_SPAWN -> {
                game.endLocation = null
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-end-removed"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            }

            ConstantStrings.ARENA_CENTER -> {
                game.arenaCenter = null
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-arena-center-removed"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))

            }

            ConstantStrings.PLAYER_SPAWN -> {
                if (game.playerSpawns.isNotEmpty()) game.playerSpawns.remove(game.playerSpawns.last())
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-spawn-removed"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            }
        }
    }

    private fun configGame(args: Array<out String>, sender: CommandSender) {
        if (args.size != 3) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["not-enough-arguments"]!!.replace(ConstantStrings.ARGUMENTS_PERCENT, 3.toString()))
            return
        }
        val game = GamesHandler.setupGames.find { it.name == args[2] }
        if (game == null) {
            sender.sendMessage(TheHunter.instance.messages.messagesMap["game-not-exists"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            return
        }
        (sender as Player).playSound(sender.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)

        when (args[1].lowercase()) {
            ConstantStrings.LOBBY_SPAWN -> {
                game.lobbyLocation = (sender).location
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-lobby-set"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            }

            ConstantStrings.SPECTATOR_SPAWN -> {
                game.spectatorLocation = (sender).location
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-spectator-set"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            }

            ConstantStrings.BACK_SPAWN -> {
                game.backLocation = (sender).location
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-back-set"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            }

            ConstantStrings.END_SPAWN -> {
                game.endLocation = (sender).location
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-end-set"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            }

            ConstantStrings.ARENA_CENTER -> {
                game.arenaCenter = (sender).location
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-arena-center-set"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))

            }

            ConstantStrings.PLAYER_SPAWN -> {
                if (game.playerSpawns.size < game.maxPlayers) {
                    game.playerSpawns.add((sender).location)
                    sender.sendMessage(TheHunter.instance.messages.messagesMap["game-spawn-set"]!!.replace("%id%", (game.playerSpawns.size - 1).toString()).replace("%game%", args[2]))
                } else sender.sendMessage(TheHunter.instance.messages.messagesMap["game-spawns-to-much"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]).replace("%max%", game.maxPlayers.toString()))
            }

            ConstantStrings.FINISH_COMMAND -> {
                if (game.isGameInvalidConfigured()) {
                    sender.sendMessage(TheHunter.instance.messages.messagesMap["wrong-config"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
                    return
                }
                game.finish()
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-successfully-created"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
                sender.sendMessage(TheHunter.instance.messages.messagesMap["game-successfully-saved"]!!.replace(ConstantStrings.GAME_PERCENT, args[2]))
            }
        }

    }
}

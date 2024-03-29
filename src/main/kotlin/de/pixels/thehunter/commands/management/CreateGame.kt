/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/2/22, 2:07 PM All contents of "CreateGame.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.commands.management

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.commands.util.CommandUtil
import de.pixels.thehunter.util.builder.Inventories.setupGameInventory
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import de.pixels.thehunter.util.misc.Util
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CreateGame {
    /**
     * Creates, configures and sets up a new game instance.
     * Processes the creation, selection, configuration, and location removal commands.
     *
     * @param sender - The CommandSender executing the command
     * @param command - The command that is being executed
     * @param args - Array of String parameters containing additional command information
     */
    
    fun create(sender: CommandSender, command: String, args: Array<out String>) {
        if (!CommandUtil.checkCommandBasics(
                sender,
                command,
                args,
                ConstantStrings.SETUP_COMMAND,
                2,
                Permissions.SETUP_COMMAND
            )
        ) return

        when (args[0].lowercase()) {
            ConstantStrings.CREATE_COMMAND -> {
                if (GamesHandler.games.find { it.name == args[1] } != null) {
                    TheHunter.instance.messagesFile.messagesMap["game-already-exists"]?.let {
                        sender.sendMessage(
                            it.replace(
                                ConstantStrings.GAME_PERCENT,
                                args[1]
                            )
                        )
                    }
                    return
                }
                if (GamesHandler.setupGames.find { it.name == args[1] } != null) {
                    Util.currentGameSelected[sender as Player] = GamesHandler.setupGames.find { it.name == args[1] } ?: return

                    Util.currentGameSelected[sender].let {
                        it?.let { inv -> sender.openInventory(setupGameInventory(inv)) }

                    }
                    return
                }
                val game = Game(args[1])
                game.create()
                Util.currentGameSelected[sender as Player] = game
                sender.playSound(sender.location, Sound.BLOCK_ANVIL_USE, 1f, 1f)
                TheHunter.instance.messagesFile.messagesMap["game-successfully-created"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[1]
                        )
                    )
                }
                sender.openInventory(setupGameInventory(game))
            }

            ConstantStrings.SELECT_COMMAND -> {
                val game = GamesHandler.games.find { it.name == args[1] }
                if (game == null) {
                    TheHunter.instance.messagesFile.messagesMap[ConstantStrings.GAME_NOT_EXISTS]?.let {
                        sender.sendMessage(
                            it.replace(
                                ConstantStrings.GAME_PERCENT,
                                args[1]
                            )
                        )
                    }
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
                TheHunter.instance.messagesFile.messagesMap[ConstantStrings.COMMAND_NOT_FOUND]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.COMMAND_PERCENT,
                            args[0]
                        )
                    )
                }
            }
        }
    }

    /**
     * Removes a specific location from a game.
     *
     * @param args An array of strings representing the command arguments.
     *             It should include the game name and the type of location to remove.
     * @param sender The command sender who executed the command.
     */
    private fun removeLocation(args: Array<out String>, sender: CommandSender) {
        if (args.size != 3) {
            TheHunter.instance.messagesFile.messagesMap[ConstantStrings.NOT_ENOUGH_ARGUMENTS]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.ARGUMENTS_PERCENT,
                        3.toString()
                    )
                )
            }
            return
        }
        val game = GamesHandler.setupGames.find { it.name == args[2] }
        if (game == null) {
            TheHunter.instance.messagesFile.messagesMap[ConstantStrings.GAME_NOT_EXISTS]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        args[2]
                    )
                )
            }
            return
        }
        (sender as Player).playSound(sender.location, Sound.BLOCK_ANVIL_BREAK, 1f, 1f)
        when (args[1].lowercase()) {
            ConstantStrings.LOBBY_SPAWN -> {
                game.lobbyLocation = null
                TheHunter.instance.messagesFile.messagesMap["game-lobby-removed"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
            }

            ConstantStrings.SPECTATOR_SPAWN -> {
                game.spectatorLocation = null
                TheHunter.instance.messagesFile.messagesMap["game-spectator-removed"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
            }

            ConstantStrings.BACK_SPAWN -> {
                game.backLocation = null
                TheHunter.instance.messagesFile.messagesMap["game-back-removed"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
            }

            ConstantStrings.END_SPAWN -> {
                game.endLocation = null
                TheHunter.instance.messagesFile.messagesMap["game-end-removed"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
            }

            ConstantStrings.ARENA_CENTER -> {
                game.arenaCenter = null
                TheHunter.instance.messagesFile.messagesMap["game-arena-center-removed"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }

            }

            ConstantStrings.PLAYER_SPAWN -> {
                if (game.playerSpawns.isNotEmpty()) game.playerSpawns.remove(game.playerSpawns.last())
                TheHunter.instance.messagesFile.messagesMap["game-spawn-removed"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
            }
        }
    }

    /**
     * Configures the game based on the given arguments.
     *
     * @param args The array of arguments for configuring the game.
     * @param sender The CommandSender who executed the configuration command.
     */
    private fun configGame(args: Array<out String>, sender: CommandSender) {
        if (args.size != 3) {
            TheHunter.instance.messagesFile.messagesMap["not-enough-arguments"]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.ARGUMENTS_PERCENT,
                        3.toString()
                    )
                )
            }
            return
        }
        val game = GamesHandler.setupGames.find { it.name == args[2] }
        if (game == null) {
            TheHunter.instance.messagesFile.messagesMap["game-not-exists"]?.let {
                sender.sendMessage(
                    it.replace(
                        ConstantStrings.GAME_PERCENT,
                        args[2]
                    )
                )
            }
            return
        }
        (sender as Player).playSound(sender.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)

        when (args[1].lowercase()) {
            ConstantStrings.LOBBY_SPAWN -> {
                game.lobbyLocation = (sender).location
                TheHunter.instance.messagesFile.messagesMap["game-lobby-set"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
            }

            ConstantStrings.SPECTATOR_SPAWN -> {
                game.spectatorLocation = (sender).location
                TheHunter.instance.messagesFile.messagesMap["game-spectator-set"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
            }

            ConstantStrings.BACK_SPAWN -> {
                game.backLocation = (sender).location
                TheHunter.instance.messagesFile.messagesMap["game-back-set"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
            }

            ConstantStrings.END_SPAWN -> {
                game.endLocation = (sender).location
                TheHunter.instance.messagesFile.messagesMap["game-end-set"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
            }

            ConstantStrings.ARENA_CENTER -> {
                game.arenaCenter = (sender).location
                TheHunter.instance.messagesFile.messagesMap["game-arena-center-set"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }

            }

            ConstantStrings.PLAYER_SPAWN -> {
                if (game.playerSpawns.size < game.maxPlayers) {
                    game.playerSpawns.add((sender).location)
                    TheHunter.instance.messagesFile.messagesMap["game-spawn-set"]?.replace(
                        "%id%",
                        (game.playerSpawns.size - 1).toString()
                    )?.let {
                        sender.sendMessage(
                            it.replace("%game%", args[2])
                        )
                    }
                } else TheHunter.instance.messagesFile.messagesMap["game-spawns-to-much"]?.replace(
                    ConstantStrings.GAME_PERCENT,
                    args[2]
                )?.let {
                    sender.sendMessage(
                        it.replace("%max%", game.maxPlayers.toString())
                    )
                }
            }

            ConstantStrings.FINISH_COMMAND -> {
                if (game.isGameInvalidConfigured()) {
                    TheHunter.instance.messagesFile.messagesMap["wrong-config"]?.let {
                        sender.sendMessage(
                            it.replace(
                                ConstantStrings.GAME_PERCENT,
                                args[2]
                            )
                        )
                    }
                    return
                }
                game.finish()
                TheHunter.instance.messagesFile.messagesMap["game-successfully-created"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
                TheHunter.instance.messagesFile.messagesMap["game-successfully-saved"]?.let {
                    sender.sendMessage(
                        it.replace(
                            ConstantStrings.GAME_PERCENT,
                            args[2]
                        )
                    )
                }
            }
        }
    }
}

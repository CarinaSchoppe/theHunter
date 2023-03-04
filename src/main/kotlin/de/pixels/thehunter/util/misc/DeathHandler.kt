package de.pixels.thehunter.util.misc

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.util.builder.Items
import de.pixels.thehunter.util.game.Game
import de.pixels.thehunter.util.game.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.function.Consumer

class DeathHandler(private val player: Player) {

    private var killer: Player? = null
    private lateinit var game: Game

    constructor(player: Player, killer: Player) : this(player) {
        this.killer = killer
    }

    fun squeezeIns(squeezeIn: () -> Unit): DeathHandler {
        squeezeIn()
        return this
    }

    fun deathPreChecks(): DeathHandler? {
        if (!GamesHandler.playerInGames.containsKey(player))
            return null
        game = GamesHandler.playerInGames[player]!!

        if (game.currentGameState !is IngameState)
            return null

        player.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f)
        return this
    }

    fun deathMessageToAll(): DeathHandler {
        if (killer != null) {
            return deathMessageKilledToAll()
        }

        game.players.forEach {
            it.sendMessage(
                TheHunter.instance.messages.messagesMap["player-died"]!!.replace(
                    ConstantStrings.PLAYER_PERCENT,
                    player.name
                )
            )
        }
        game.spectators.filter { it.name != player.name }.forEach {
            it.sendMessage(
                TheHunter.instance.messages.messagesMap["player-died"]!!.replace(
                    ConstantStrings.PLAYER_PERCENT,
                    player.name
                )
            )
        }
        try {
            player.sendMessage(TheHunter.instance.messages.messagesMap["player-own-died"]!!)
        } catch (_: Exception) {
        }

        return this
    }

    private fun deathMessageKilledToAll(): DeathHandler {
        game.players.forEach {
            it.sendMessage(
                TheHunter.instance.messages.messagesMap["player-killed-by-other"]!!.replace(
                    ConstantStrings.PLAYER_PERCENT,
                    player.name
                ).replace(ConstantStrings.KILLER_PERCENT, killer!!.name)
            )

        }
        game.spectators.filter { it.name != player.name }.forEach {
            it.sendMessage(
                TheHunter.instance.messages.messagesMap["player-killed-by-other"]!!.replace(
                    ConstantStrings.PLAYER_PERCENT,
                    player.name
                ).replace(ConstantStrings.KILLER_PERCENT, killer!!.name)
            )

        }
        player.sendMessage(
            TheHunter.instance.messages.messagesMap["player-own-killed-by-other"]!!.replace(
                ConstantStrings.PLAYER_PERCENT,
                killer!!.name
            )
        )
        return this
    }

    fun playerDiedStatsHandling(): DeathHandler {
        if (killer != null)
            return playerKilledByOthersStatsHandling()
        TheHunter.instance.statsSystem.playerDied(player)
        return this
    }

    private fun playerKilledByOthersStatsHandling(): DeathHandler {
        TheHunter.instance.statsSystem.playerKilledOtherPlayer(killer!!, player)

        return this
    }

    fun deathChestCreation(): DeathHandler {
        game.deathChest.createDeathChest(player)
        return this
    }


    fun afterDeathChecks(): DeathHandler {
        if (game.checkWinning())
            game.nextGameState()
        return this
    }

    fun afterDeathPlayerHandling(): DeathHandler {
        game.players.remove(player)
        game.spectators.add(player)
        GamesHandler.playerInGames.remove(player)
        GamesHandler.spectatorInGames[player] = game

        Bukkit.getScheduler().runTaskLater(TheHunter.instance, Consumer {
            try {
                player.spigot().respawn()
            } catch (_: Exception) {
            }
            player.inventory.clear()
            player.inventory.setItem(8, Items.leaveItem)
            player.inventory.setItem(9, Items.leaveItem)
            player.teleport(game.spectatorLocation!!)
        }, 1)

        PlayerHiding.hidePlayerToAll(player)
        PlayerHiding.showOnlyActiveGamePlayingPlayersToPlayer(player)

        player.allowFlight = true
        Bukkit.getOnlinePlayers().forEach {
            if (game.players.contains(it) || game.spectators.contains(it))
                game.scoreBoard.createNewScoreboard(it)
        }
        return this
    }

}
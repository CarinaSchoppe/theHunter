package de.pixels.thehunter.util.misc

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.game.Game
import de.pixels.thehunter.util.game.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object PlayerHiding {

    fun showPlayerOnlyToNonPlayingPlayers(player: Player) {
        Bukkit.getOnlinePlayers().forEach { players ->
            if (GamesHandler.playerInGames.containsKey(players) || GamesHandler.spectatorInGames.containsKey(players)) players.hidePlayer(
                TheHunter.instance,
                player
            ) else
                players.showPlayer(TheHunter.instance, player)
        }
    }

    fun showOnlyNonPlayingPlayersToPlayer(player: Player) {
        Bukkit.getOnlinePlayers().forEach { players ->
            if (GamesHandler.playerInGames.containsKey(players) || GamesHandler.spectatorInGames.containsKey(players)) player.hidePlayer(
                TheHunter.instance,
                players
            )
            else player.showPlayer(TheHunter.instance, players)
        }
    }

    fun showOnlyActiveGamePlayingPlayersToPlayer(player: Player) {
        val game = getGameFromPlayer(player) ?: return
        Bukkit.getOnlinePlayers().forEach { players ->
            if (game.players.contains(players)) player.showPlayer(TheHunter.instance, players)
            else
                player.hidePlayer(TheHunter.instance, players)
        }
    }

    fun showPlayerToOnlyActiveGamePlayingPlayers(player: Player) {
        val game = getGameFromPlayer(player) ?: return
        Bukkit.getOnlinePlayers().forEach { players ->
            if (game.players.contains(players)) players.showPlayer(TheHunter.instance, player)
        }
    }

    fun showPlayerToOnlyGamePlayingPlayers(player: Player) {
        val game = getGameFromPlayer(player) ?: return
        Bukkit.getOnlinePlayers().forEach { players ->
            if (game.players.contains(players) || game.spectators.contains(players)) players.showPlayer(
                TheHunter.instance,
                player
            ) else
                players.hidePlayer(TheHunter.instance, player)
        }
    }

    fun showGamePlayingPlayersToPlayer(player: Player) {
        val game = getGameFromPlayer(player) ?: return
        Bukkit.getOnlinePlayers().forEach { players ->
            if (game.players.contains(players) || game.spectators.contains(players)) player.showPlayer(
                TheHunter.instance,
                players
            ) else
                player.hidePlayer(TheHunter.instance, players)
        }
    }


    fun hidePlayerToAll(player: Player) {
        Bukkit.getOnlinePlayers().forEach { players ->
            players.hidePlayer(TheHunter.instance, player)
        }
    }


    private fun getGameFromPlayer(player: Player): Game? {
        return GamesHandler.playerInGames.entries.firstOrNull { it.key == player }?.value
    }

}
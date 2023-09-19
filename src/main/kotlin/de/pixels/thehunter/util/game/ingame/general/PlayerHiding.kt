package de.pixels.thehunter.util.game.ingame.general

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.game.management.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object PlayerHiding {

    /**
     * Shows the given player only to non-playing players.
     *
     * @param player the player to be shown only to non-playing players
     */
    fun showPlayerOnlyToNonPlayingPlayers(player: Player) {
        Bukkit.getOnlinePlayers().forEach { players ->
            if (GamesHandler.playerInGames.containsKey(players) || GamesHandler.spectatorInGames.containsKey(players)) players.hidePlayer(
                TheHunter.instance,
                player
            ) else
                players.showPlayer(TheHunter.instance, player)
        }
    }

    /**
     * Shows only non-playing players to the specified player.
     *
     * @param player the player to show the non-playing players to
     */
    fun showOnlyNonPlayingPlayersToPlayer(player: Player) {
        Bukkit.getOnlinePlayers().forEach { players ->
            if (GamesHandler.playerInGames.containsKey(players) || GamesHandler.spectatorInGames.containsKey(players)) player.hidePlayer(
                TheHunter.instance,
                players
            )
            else player.showPlayer(TheHunter.instance, players)
        }
    }

    /**
     * Shows only the active game playing players to the given player.
     *
     * @param player the player to whom the active game playing players will be shown
     */
    fun showOnlyActiveGamePlayingPlayersToPlayer(player: Player) {
        val game = getGameFromPlayer(player) ?: return
        Bukkit.getOnlinePlayers().forEach { players ->
            if (game.players.contains(players)) player.showPlayer(TheHunter.instance, players)
            else
                player.hidePlayer(TheHunter.instance, players)
        }
    }

    /**
     * Shows the specified player to only active game-playing players.
     *
     * @param player The player to show.
     */
    fun showPlayerToOnlyActiveGamePlayingPlayers(player: Player) {
        val game = getGameFromPlayer(player) ?: return
        Bukkit.getOnlinePlayers().forEach { players ->
            if (game.players.contains(players)) players.showPlayer(TheHunter.instance, player)
        }
    }

    /**
     * This method shows a player to only the players currently playing the game.
     *
     * @param player The player to be shown.
     */
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

    /**
     * Shows the game-playing players to the given player.
     *
     * @param player the player to whom the game-playing players will be shown
     */
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


    /**
     * Hides the specified player from all online players.
     *
     * @param player The player to hide.
     */
    fun hidePlayerToAll(player: Player) {
        Bukkit.getOnlinePlayers().forEach { players ->
            players.hidePlayer(TheHunter.instance, player)
        }
    }


    /**
     * Retrieves the Game object associated with the given player.
     *
     * @param player The player for which to retrieve the Game.
     * @return The Game object associated with the player, or null if not found.
     */
    private fun getGameFromPlayer(player: Player): Game? {
        return GamesHandler.playerInGames.entries.firstOrNull { it.key == player }?.value
    }

}

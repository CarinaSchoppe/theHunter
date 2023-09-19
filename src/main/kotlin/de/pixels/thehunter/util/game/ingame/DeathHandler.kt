package de.pixels.thehunter.util.game.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.util.builder.Items
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.function.Consumer

class DeathHandler(private val player: Player) {

    /**
     * The killer variable represents the player who killed another player.
     *
     * It is a nullable variable that can hold a reference to a Player object.
     * If no player has been identified as the killer yet, the variable is set to null.
     *
     * @property killer The player who killed another player, or null if no player has been identified.
     * @since 1.0.0
     */
    private var killer: Player? = null

    /**
     * A private lateinit variable representing the game in progress.
     *
     * This variable is used to store the instance of the Game class that represents the game being played.
     * The variable is declared with the 'lateinit' modifier, which means it is initially uninitialized but
     * will be assigned a value before it is accessed. The variable is private to restrict direct access
     * from outside the class.
     *
     * @since (version number or date)
     */
    private lateinit var game: Game

    /**
     * Constructs a new instance of the class with the given parameters.
     *
     * @param player the player object.
     * @param killer the killer object.
     */
    constructor(player: Player, killer: Player) : this(player) {
        this.killer = killer
    }

    /**
     * Invokes the given `squeezeIn` block of code and returns a `DeathHandler` instance.
     *
     * @param squeezeIn the block of code to be executed.
     *
     * @return the `DeathHandler` instance.
     */
    fun squeezeIns(squeezeIn: () -> Unit): DeathHandler {
        squeezeIn()
        return this
    }

    /**
     * Performs pre-checks before handling player death.
     *
     * @return The DeathHandler object if the pre-checks pass, null otherwise.
     */
    fun deathPreChecks(): DeathHandler? {
        if (!GamesHandler.playerInGames.containsKey(player))
            return null
        game = GamesHandler.playerInGames[player] ?: return null

        if (game.currentGameState !is IngameState)
            return null

        player.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_GROWL, 1f, 1f)
        return this
    }

    /**
     * Sends death messages to all players and spectators.
     * If the killer is known, it sends a specific death message to all players and spectators.
     *
     * @return The DeathHandler instance.
     */
    fun deathMessageToAll(): DeathHandler {
        if (killer != null) {
            return deathMessageKilledToAll()
        }

        game.players.forEach {
            TheHunter.instance.messagesFile.messagesMap["player-died"]?.let { message ->
                it.sendMessage(
                    message.replace(
                        ConstantStrings.PLAYER_PERCENT,
                        player.name
                    )
                )
            }
        }
        game.spectators.filter { it.name != player.name }.forEach {
            TheHunter.instance.messagesFile.messagesMap["player-died"]?.let { message ->
                it.sendMessage(
                    message.replace(
                        ConstantStrings.PLAYER_PERCENT,
                        player.name
                    )
                )
            }
        }

        TheHunter.instance.messagesFile.messagesMap["player-own-died"]?.let { player.sendMessage(it) }
     

        return this
    }

    /**
     * Sends death messages to all players and spectators when a player is killed by another player.
     * The death messages are retrieved from the messageMap in TheHunter instance.
     *
     * @return The DeathHandler instance to allow method chaining.
     */
    private fun deathMessageKilledToAll(): DeathHandler {
        game.players.forEach {
            TheHunter.instance.messagesFile.messagesMap["player-killed-by-other"]?.replace(
                ConstantStrings.PLAYER_PERCENT,
                player.name
            )?.let { str ->
                it.sendMessage(
                    str.replace(ConstantStrings.KILLER_PERCENT, killer?.name ?: "")
                )
            }

        }
        game.spectators.filter { it.name != player.name }.forEach {
            TheHunter.instance.messagesFile.messagesMap["player-killed-by-other"]?.replace(
                ConstantStrings.PLAYER_PERCENT,
                player.name
            )?.let { str ->
                it.sendMessage(
                    str.replace(ConstantStrings.KILLER_PERCENT, killer?.name ?: "")
                )
            }

        }
        TheHunter.instance.messagesFile.messagesMap["player-own-killed-by-other"]?.let {
            player.sendMessage(
                it.replace(
                    ConstantStrings.PLAYER_PERCENT,
                    killer?.name ?: ""
                )
            )
        }
        return this
    }

    /**
     * Handles the statistics when the player dies.
     *
     * @return A DeathHandler instance.
     */
    fun playerDiedStatsHandling(): DeathHandler {
        if (killer != null)
            return playerKilledByOthersStatsHandling()
        TheHunter.instance.statsSystem.playerDied(player)
        return this
    }

    /**
     * Handles the statistics for when the player is killed by others.
     *
     * @return The DeathHandler instance.
     */
    private fun playerKilledByOthersStatsHandling(): DeathHandler {
        killer?.let { TheHunter.instance.statsSystem.playerKilledOtherPlayer(it, player) }

        return this
    }

    /**
     * Creates a death chest for the player.
     *
     * @return the DeathHandler instance.
     */
    fun deathChestCreation(): DeathHandler {
        game.deathChest.createDeathChest(player)
        return this
    }


    /**
     * Perform necessary checks after death in the game.
     *
     * @return The current DeathHandler instance.
     */
    fun afterDeathChecks(): DeathHandler {
        if (game.checkWinning())
            game.nextGameState()
        return this
    }

    /**
     * Handles player actions after death.
     *
     * @return The DeathHandler instance.
     */
    fun afterDeathPlayerHandling(): DeathHandler {
        game.players.remove(player)
        game.spectators.add(player)
        GamesHandler.playerInGames.remove(player)
        GamesHandler.spectatorInGames[player] = game

        Bukkit.getScheduler().runTaskLater(TheHunter.instance, Consumer {
            player.spigot().respawn()
            player.inventory.clear()
            player.inventory.setItem(8, Items.leaveItem)
            player.inventory.setItem(9, Items.leaveItem)
            game.spectatorLocation?.let { user -> player.teleport(user) }
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

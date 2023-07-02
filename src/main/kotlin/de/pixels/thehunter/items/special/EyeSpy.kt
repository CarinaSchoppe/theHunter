/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 7:05 PM All contents of "EyeSpy.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.items.special

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.items.util.ItemHandler
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.game.management.GamesHandler
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.title.Title
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class EyeSpy : Listener {

    companion object {
        /**
         * Represents a variable named 'inEyeSpy' which is a mutable set of players.
         *
         * @property inEyeSpy The mutable set of players in the Eye Spy game.
         */
        val inEyeSpy = mutableSetOf<Player>()

        /**
         * Represents the mapping of the last known location of each player.
         *
         * The `lastPlayerLocation` variable is a mutable map that stores the last known location of each player
         * in the form of a key-value pair. The key represents a `Player` object, and the value represents
         * the corresponding `Location` object.
         *
         * To update the last known location of a player, simply assign a new `Location` object to the corresponding
         * player key. The existing key-value pair will be automatically updated or added if it doesn't exist.
         *
         * Example usage:
         * ```
         * val player1 = Player("John")
         * val player2 = Player("Jane")
         *
         * // Assign the last known location of each player
         * lastPlayerLocation[player1] = Location(10, 10)
         * lastPlayerLocation[player2] = Location(-5, 3)
         *
         * // Update the location of player1
         * lastPlayerLocation[player1] = Location(0, 0)
         *
         * // Retrieve the location of player2
         * val location2 = lastPlayerLocation[player2]
         * ```
         *
         * @since 1.0.0
         */
        val lastPlayerLocation = mutableMapOf<Player, Location>()

        /**
         * Represents a mutable map that holds the time duration played by each player.
         *
         * The keys of this map are Player objects, and the values represent the duration of playtime for each player.
         */
        private val mapPlayerTime = mutableMapOf<Player, Int>()

        /**
         * Represents the "Eye Spy" item.
         *
         * This item allows the player to see the other player for 10 seconds when activated.
         * It is created using the ItemBuilder and has a display name and lore associated with it.
         * The Eye Spy item is represented by an Ender Eye material.
         *
         * Usage:
         * - Right-click to activate the Eye Spy effect.
         *
         * Example:
         * ```
         * val eyeSpy = ItemBuilder(Material.ENDER_EYE).addDisplayName(TheHunter.prefix + "§aEye Spy")
         *               .addLore("§7This Article will let you see the other player for 10 sec")
         *               .addLore("§7Right-click to activate").build()
         * ```
         *
         * @see ItemBuilder
         * @see Material
         */
        val eyeSpy =
            ItemBuilder(Material.ENDER_EYE).addDisplayName(TheHunter.prefix + "§aEye Spy")
                .addLore("§7This Article will let you see the other player for 10 sec")
                .addLore("§7Right-click to activate").build()
    }


    /**
     * Handles the event when a player uses the EyeSpy item.
     *
     * @param event The PlayerInteractEvent representing the interaction event.
     */
    @EventHandler
    fun onEyeSpyUse(event: PlayerInteractEvent) {
        if (ItemHandler.shouldNotInteractWithItem(event, eyeSpy, "EyeSpy") || inEyeSpy.contains(event.player) || !GamesHandler.playerInGames.containsKey(event.player))
            return

        val game = GamesHandler.playerInGames[event.player]
        val targets =
            GamesHandler.playerInGames[event.player]?.players?.filter { it != event.player && it.gameMode != GameMode.SPECTATOR }
        if (targets != null && targets.isEmpty()) {
            return
        }
        val target = targets?.random()
        ItemHandler.removeOneItemOfPlayer(event.player)
        event.isCancelled = true
        setCamera(game ?: return, event.player, target ?: return)
    }


    /**
     * Sets the camera for a player in the Eye Spy game.
     *
     * @param game The Eye Spy game instance.
     * @param player The player to set the camera for.
     * @param target The player to set as the camera target.
     */
    private fun setCamera(game: Game, player: Player, target: Player) {
        lastPlayerLocation[player] = player.location
        player.teleport(target.location)
        player.playSound(player, Sound.ITEM_GOAT_HORN_SOUND_1, 1f, 1f)
        inEyeSpy.add(player)
        player.gameMode = GameMode.SPECTATOR
        player.spectatorTarget = target
        mapPlayerTime[player] = (game.itemSettings.settingsMap["eye-spy-duration"] as Int)

        showingTitle(player)
        TheHunter.instance.server.scheduler.scheduleSyncDelayedTask(TheHunter.instance, {
            lastPlayerLocation[player]?.let { player.teleport(it) }
            player.gameMode = GameMode.SURVIVAL
            inEyeSpy.remove(player)
            player.playSound(player, Sound.ITEM_GOAT_HORN_SOUND_4, 1f, 1f)
        }, 20L * game.itemSettings.settingsMap["eye-spy-duration"] as Int)
    }

    /**
     * Shows a title to the given player based on the remaining time in the map.
     *
     * @param player the player to show the title to
     *
     * @since 1.0.0
     */
    private fun showingTitle(player: Player) {
        TheHunter.instance.server.scheduler.scheduleSyncRepeatingTask(TheHunter.instance, {
            mapPlayerTime[player] = mapPlayerTime[player]?.minus(1) ?: return@scheduleSyncRepeatingTask
            when (mapPlayerTime[player]) {
                1 or 2 or 3 -> {
                    player.showTitle(
                        Title.title(
                            LegacyComponentSerializer.legacySection().deserialize("§6${mapPlayerTime[player]}"),
                            Component.text("")
                        )
                    )
                }
            }
        }, 20L, 20L)
    }

}

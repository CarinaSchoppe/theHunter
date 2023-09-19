/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:46 PM All contents of "EggBomb.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.items.special

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.IngameState
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.management.Game
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.ConstantStrings
import de.pixels.thehunter.util.misc.Permissions
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Egg
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

class EggBomb : Listener {

    companion object {
        /**
         * Represents a collection of TNT primed bombs.
         *
         * This mutable set holds instances of the `TNTPrimed` class, which represents a lit TNT block in Minecraft.
         *
         * @property bombs The mutable set of `TNTPrimed` instances.
         */
        val bombs = mutableSetOf<TNTPrimed>()

        /**
         * Creates an Egg Bomb item for the given game.
         *
         * @param game The game to create the Egg Bomb item for.
         * @return The Egg Bomb item.
         */
        fun eggBomb(game: Game) = ItemBuilder(Material.EGG).addDisplayName(TheHunter.prefix + "§eEggBomb")
            .addEnchantment(Enchantment.DURABILITY, 1).addLore(
                listOf(
                    TheHunter.instance.messagesFile.messagesMap["egg-bomb-message"]?.replace(
                        ConstantStrings.POWER_PERCENT,
                        game.itemSettings.settingsMap["egg-bomb-radius"].toString()
                    ) ?: "",
                )
            ).build()
    }


    /**
     * Handles the logic when an egg bomb is hit by a projectile.
     *
     * @param event The ProjectileHitEvent triggered when the egg bomb hits something.
     */
    @EventHandler
    fun onEggBomb(event: ProjectileHitEvent) {
        if (event.entity !is Egg)
            return
        val egg = event.entity as Egg
        if (egg.shooter !is org.bukkit.entity.Player)
            return
        val player = egg.shooter as org.bukkit.entity.Player
        //Check if the egg is the same egg as an EggBomb item
        if (!player.hasPermission(Permissions.EGG_BOMB) || !GamesHandler.playerInGames.containsKey(player))
            return

        val game = GamesHandler.playerInGames[player] ?: return

        if (game.currentGameState !is IngameState || !egg.item.hasItemMeta() || egg.item.itemMeta != eggBomb(game).itemMeta || GamesHandler.playerInGames[player]?.gameItems?.items?.get("EggBomb") == false || GamesHandler.playerInGames[player]?.currentGameState !is IngameState)
            return

        eggBombBoom(game, event, egg)
    }


    /**
     * Spawns a TNT entity at the location of the projectile hit event after a delay specified in the game settings.
     *
     * @param game The current game instance.
     * @param event The ProjectileHitEvent that triggered the method.
     * @param egg The Egg projectile that hit the target.
     */
    private fun eggBombBoom(game: Game, event: ProjectileHitEvent, egg: Egg) {
        Bukkit.getScheduler().runTaskLater(TheHunter.instance, Runnable {
            val tnt = event.entity.location.world.spawn(event.entity.location, org.bukkit.entity.TNTPrimed::class.java)
            tnt.fuseTicks = 0
            tnt.source = egg
            bombs.add(tnt)
            GamesHandler.entitiesInGames[tnt] = game
        }, (game.itemSettings.settingsMap["egg-bomb-delay"] as Int) * 20L)
    }
}

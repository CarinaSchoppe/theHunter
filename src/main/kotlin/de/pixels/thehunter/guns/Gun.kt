/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/31/22, 9:58 PM All contents of "Gun.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.guns

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.events.player.PlayerHotbarHover
import de.pixels.thehunter.util.game.management.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Gun {

    /**
     * This variable represents the name of the gun.
     *
     * It is a protected abstract variable of type String.
     *
     * Subclasses must provide an implementation for this variable.
     *
     * @see SubclassName
     */
    protected abstract var gunName: String

    /**
     * The ammoString represents the string format of the ammunition.
     *
     * This variable is declared as abstract and protected, which means it should be implemented
     * by subclasses and cannot be accessed directly from outside the class or its subclasses.
     *
     * @see Subclass1
     * @see Subclass2
     * @see Subclass3
     */
    protected abstract var ammoString: String

    /**
     * Represents the ammunition for a weapon.
     *
     * This variable is declared as `protected abstract` meaning that it needs to be implemented by subclasses.
     * The `ammo` variable holds an instance of the `ItemStack` class, representing the ammunition item.
     *
     * @see ItemStack
     */
    protected abstract var ammo: ItemStack

    /**
     * Represents a gun item in the game.
     *
     * Guns are ItemStacks that can be equipped by players and used to shoot projectiles.
     * This variable is declared as abstract to indicate that its exact implementation
     * depends on the specific gun type being used.
     *
     * @property gun The ItemStack representing the gun item.
     */
    abstract var gun: ItemStack

    /**
     * Keeps track of the bullet delay for each player.
     * The delay indicates whether a player can currently shoot a bullet or not.
     *
     * @property bulletDelay The map that holds the bullet delay status for each player.
     *                      The key represents the player, and the value indicates whether the player can shoot a bullet.
     *                      The boolean value `true` indicates that the player can shoot a bullet,
     *                      while `false` indicates that the player has to wait for the delay to end.
     */
    private val bulletDelay = mutableMapOf<Player, Boolean>()

    /**
     * Represents the collection of shot bullets for each player.
     *
     * This variable is a mutable map that associates players with the set of arrows they have shot.
     *
     * @property shotBullets The map that holds players as keys and their corresponding set of arrows as values.
     */
    val shotBullets = mutableMapOf<Player, MutableSet<Arrow>>()

    /**
     * Represents the reloading status of players.
     *
     * This mutable map stores the reloading status of players, where the player is the key and
     * a boolean value indicating if the player is reloading or not.
     */
    private val reloading = mutableMapOf<Player, Boolean>()

    /**
     * Represents a magazine that contains the number of bullets for each player.
     *
     * A magazine is implemented as a mutable map with [Player] as the key and [Int] as the value.
     * The key represents the player, and the value represents the number of bullets they have in their magazine.
     *
     * @property magazine The mutable map representing the magazine, where the key is the player and the value is the number of bullets.
     */
    val magazine = mutableMapOf<Player, Int>()


    /**
     * Sets a delay for shooting bullets for the given player.
     *
     * @param player The player for whom the bullet delay will be set.
     */
    private fun bulletDelayMaker(player: Player) {
        bulletDelay[player] = true
        Bukkit.getScheduler().runTaskLater(TheHunter.instance, Runnable {
            bulletDelay[player] = false
        }, 5L * GamesHandler.playerInGames[player]?.gameItems?.guns?.get("$gunName-speed") as Int)
    }

    /**
     * Shoots a projectile from the specified player.
     *
     * @param player The player shooting the projectile.
     */
    private fun shootProjectile(player: Player) {
        bulletDelayMaker(player)
        val arrow = player.launchProjectile(
            Arrow::class.java, player.location.direction.multiply(
                GamesHandler.playerInGames[player]?.gameItems?.guns?.get("$gunName-power") ?: return
            )
        )
        arrow.damage = 0.0
        player.playSound(player, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1f, 1f)
        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f)
        magazine[player] = magazine[player] as Int - 1
        arrow.shooter = player
        PlayerHotbarHover.updateHotbar(gun, player)

        if (shotBullets.containsKey(player)) {
            shotBullets[player]?.add(arrow)
        } else {
            shotBullets[player] = mutableSetOf(arrow)
        }
    }

    /**
     * Shoots a projectile for the given player.
     *
     * @param player the player to shoot the projectile for
     *
     * @return true if the projectile was successfully shot, false otherwise
     */
    fun shoot(player: Player): Boolean {
        if (!reloading.containsKey(player)) {
            reloading[player] = false
        }
        if (!bulletDelay.containsKey(player))
            bulletDelay[player] = false
        if (!magazine.containsKey(player)) {
            magazine[player] = 0
            return false
        }
        val mag = magazine[player] ?: return false
        if (mag <= 0) {
            reload(player)
            return false
        }

        if (bulletDelay[player] == false) shootProjectile(player)
        return true
    }

    /**
     * Checks if the player has enough ammo in their inventory.
     *
     * @param player the player to check
     * @return true if the player has enough ammo, false otherwise
     */
    private fun checkAmmoPossible(player: Player): Boolean {
        if (!player.inventory.containsAtLeast(ammo, 1)) {
            TheHunter.instance.messages.messagesMap["gun-out-of-ammo"]?.let { player.sendMessage(it) }
            return false
        }
        return true
    }


    /**
     * Reloads the gun for the specified player.
     *
     * @param player The player who will reload the gun.
     */
    fun reload(player: Player) {
        if (magazine.getOrDefault(
                player,
                0
            ) >= (GamesHandler.playerInGames[player]?.gameItems?.guns?.get(ammoString) ?: return)
        )
            return
        player.playSound(player.location, Sound.BLOCK_ANVIL_LAND, 1f, 1f)
        if (reloading[player] == true) {
            TheHunter.instance.messages.messagesMap["gun-reloading"]?.let { player.sendMessage(it) }
            return
        }
        if (!checkAmmoPossible(player))
            return

        TheHunter.instance.messages.messagesMap["gun-reloading"]?.let { player.sendMessage(it) }
        reloading[player] = true
        Bukkit.getScheduler().scheduleSyncDelayedTask(TheHunter.instance, {
            reloading[player] = false
            val amount = getAmmoAmount(player, ammo)
            val old = magazine.getOrDefault(player, 0)
            if (amount + old >= (GamesHandler.playerInGames[player]?.gameItems?.guns?.get(ammoString) ?: return@scheduleSyncDelayedTask)) {
                magazine[player] = GamesHandler.playerInGames[player]?.gameItems?.guns?.get(ammoString) ?: return@scheduleSyncDelayedTask
            } else
                magazine[player] = amount + old
            val mag = magazine[player] ?: return@scheduleSyncDelayedTask
            repeat(mag - old) {
                GunHandler.removeAmmo(player, this)
            }
            PlayerHotbarHover.updateHotbar(gun, player)

            player.playSound(player.location, Sound.BLOCK_ANVIL_USE, 1f, 1f)

            TheHunter.instance.messages.messagesMap["gun-reload-done"]?.let { player.sendMessage(it) }
        }, 20L * (GamesHandler.playerInGames[player]?.gameItems?.guns?.get("$gunName-reload") ?: return))
    }


    /**
     * Returns the amount of ammo items that a player has in their inventory.
     *
     * @param player The player whose inventory will be checked.
     * @param ammo The ammo item to count.
     * @return The total amount of ammo items that the player has in their inventory.
     */
    private fun getAmmoAmount(player: Player, ammo: ItemStack): Int {
        var amount = 0
        for (item in player.inventory.contents) {
            if (item == null || !item.hasItemMeta())
                continue
            if (item.itemMeta == ammo.itemMeta)
                amount += item.amount
        }
        return amount
    }
}

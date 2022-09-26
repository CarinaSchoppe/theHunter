/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/6/22, 10:54 PM by Carina The Latest changes made by Carina on 6/6/22, 10:54 PM All contents of "PlayerTeamHead.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.util.misc

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.LobbyState
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.ItemStack

class PlayerTeamHead : Listener {

    companion object {
        val createPlayerHead: ItemStack =
            ItemBuilder(Material.PLAYER_HEAD).addDisplayName(TheHunter.prefix + "§7Team-Inviter").addEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 1).addLore("§aLeftclick to accept").addLore("§aRightclick to invite").build()

    }

    @EventHandler
    fun onItemAccept(event: PlayerInteractAtEntityEvent) {

        if (!event.player.inventory.itemInMainHand.hasItemMeta())
            return
        if (event.player.inventory.itemInMainHand.itemMeta != createPlayerHead.itemMeta)
            return
        if (!event.player.hasPermission(Permissions.PLAYER_INVITER))
            return
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        val game = GamesHandler.playerInGames[event.player]!!
        if (game.currentGameState !is LobbyState)
            return
        if (event.rightClicked !is Player)
            return
        val player = event.rightClicked as Player
        if (!game.players.contains(player))
            return
        event.player.performCommand("thehunter team accept " + player.name)
    }

    @EventHandler
    fun onPlayerInvite(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player)
            return
        if (event.damager !is Player)
            return

        if (!event.damager.hasPermission(Permissions.PLAYER_INVITER))
            return
        if (!GamesHandler.playerInGames.containsKey(event.damager))
            return
        val game = GamesHandler.playerInGames[event.damager]!!
        if (game.currentGameState !is LobbyState)
            return

        if (!game.players.contains(event.entity as Player))
            return

        if ((event.damager as Player).inventory.itemInMainHand.itemMeta != createPlayerHead.itemMeta)
            return
        (event.damager as Player).performCommand("theHunter team invite " + event.entity.name)


    }
}

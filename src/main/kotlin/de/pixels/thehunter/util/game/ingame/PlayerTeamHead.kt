/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 10:23 PM All contents of "PlayerTeamHead.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.game.ingame

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.gamestates.LobbyState
import de.pixels.thehunter.util.builder.ItemBuilder
import de.pixels.thehunter.util.game.management.GamesHandler
import de.pixels.thehunter.util.misc.Permissions
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
            ItemBuilder(Material.PLAYER_HEAD).addDisplayName(TheHunter.prefix + "§7Team-Inviter")
                .addEnchantment(org.bukkit.enchantments.Enchantment.DURABILITY, 1).addLore("§aLeftclick to accept")
                .addLore("§aRightclick to invite").build()

    }

    @EventHandler
    fun onItemAccept(event: PlayerInteractAtEntityEvent) {


        if (!event.player.inventory.itemInMainHand.hasItemMeta() || event.player.inventory.itemInMainHand.itemMeta != createPlayerHead.itemMeta || !event.player.hasPermission(Permissions.PLAYER_INVITER) || !GamesHandler.playerInGames.containsKey(event.player))
            return

        val game = GamesHandler.playerInGames[event.player] ?: return
        if (game.currentGameState !is LobbyState || event.rightClicked !is Player)
            return

        val player = event.rightClicked as Player
        if (!game.players.contains(player))
            return
        event.player.performCommand("thehunter team accept " + player.name)
    }

    @EventHandler
    fun onPlayerInvite(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player || event.damager !is Player || !event.damager.hasPermission(Permissions.PLAYER_INVITER) || !GamesHandler.playerInGames.containsKey(event.damager))
            return
        val game = GamesHandler.playerInGames[event.damager] ?: return
        if (game.currentGameState !is LobbyState || !game.players.contains(event.entity as Player) || (event.damager as Player).inventory.itemInMainHand.itemMeta != createPlayerHead.itemMeta)
            return
        (event.damager as Player).performCommand("theHunter team invite " + event.entity.name)


    }
}

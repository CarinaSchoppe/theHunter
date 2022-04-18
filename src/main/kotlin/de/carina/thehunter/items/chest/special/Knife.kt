package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack

class Knife : Listener {

    fun createKnifeItem(): ItemStack {
        return ItemBuilder(Material.IRON_SWORD)
            .addDisplayName(TheHunter.PREFIX + "§eKnife")
            .addLore(listOf("§Knife to kill", "§eLeft-Click to use")).addEnchantment(Enchantment.DURABILITY, 1)
            .build()
    }

    @EventHandler
    fun onKnifeUse(event: EntityDamageByEntityEvent) {
        if (event.entity !is Player)
            return
        if (event.damager !is Player)
            return
        if ((event.damager as Player).inventory.itemInMainHand.itemMeta != createKnifeItem().itemMeta)
            return
        if ((event.damager as Player).inventory.itemInMainHand.itemMeta != createKnifeItem().itemMeta)
            return
        if (!GamesHandler.playerInGames.containsKey(event.entity as Player))
            return
        if (!GamesHandler.playerInGames[event.entity as Player]!!.players.contains(event.damager as Player))
            return

        if (GamesHandler.playerInGames[event.entity as Player]!!.currentGameState !is IngameState)
            return
        if (GamesHandler.playerInGames[event.damager as Player]!!.gameItems!!.items["Knife"] == false)
            return
        event.damage = TheHunter.instance.itemSettings.settingsMap["knife-damage"] as Double
    }


}
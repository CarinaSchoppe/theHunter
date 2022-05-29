/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "Inventories.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.builder

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.Game
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object Inventories {
    fun setupGameInventory(game: Game): Inventory =
        InventoryBuilder("§d${game.name}§6: Game Setup", 18).setItem(0, Items.addLobbyButton).setItem(1, Items.addSpectatorButton).setItem(2, Items.addArenaCenterButton).setItem(3, Items.addSpawnButton).setItem(4, Items.addBackButton).setItem(5, Items.addEndButton).setItem(7, Items.settingsHead).setItem(8, Items.finishButton).fillInventory(ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1).build()).create()

    val gamesInventory = InventoryBuilder(TheHunter.prefix + "§6Games", 54).addGamesToInventory().fillInventory(ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1).build()).create()

    fun createSettingsInventory(game: Game): Inventory {
        val builder = InventoryBuilder("§d${game.name}§6: Game Settings", 54)
        val teamsHead = ItemBuilder(Material.PLAYER_HEAD).addDisplayName("§6Teams Allowed").addLore("§Teams allowed or not").build()
        builder.setItem(3, teamsHead)
        addSettingButtonsInColorToInventory(builder, 0, game.teamsAllowed)
        val teamsSize = ItemBuilder(Material.DIAMOND_SWORD).addDisplayName("§6Team Size").addLore("§Teamsize add or reduce").build()
        builder.setItem(3 + 9 * 1, teamsSize)
        if (game.teamsAllowed) addSettingButtonsInColorToInventory(builder, 1, game.teamsAllowed)
        val teamsDamage = ItemBuilder(Material.REDSTONE).addDisplayName("§6Team Damage").addLore("§TeamDamage allowed or not").build()
        if (game.teamsAllowed) addSettingButtonsInColorToInventory(builder, 2, game.teamDamage)
        builder.setItem(3 + 9 * 2, teamsDamage)
        //TODO: Weiter machen
        return builder.fillInventory(ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1).build()).create()

        /*
        Steps: Create inventory
        fill contents of inventory
        create itemstacks
        add itemstacks according
        return inventory using builder
         */
    }

    fun itemEnchantmentSwitcher(event: InventoryClickEvent) {
        val item = event.currentItem!!
        val meta = item.itemMeta!!
        if (item.enchantments.isNotEmpty()) return

        if (!(item.type == Material.RED_WOOL || item.type == Material.GREEN_WOOL)) return
        meta.addEnchant(Enchantment.DURABILITY, 1, true)
        item.itemMeta = meta
        event.inventory.setItem(event.slot, item)
        val other = (if (item.type == Material.GREEN_WOOL) event.inventory.getItem(event.slot + 1) else if (item.type == Material.RED_WOOL) event.inventory.getItem(event.slot - 1) else null) ?: return
        val otherMeta = other.itemMeta!!
        //get the slot of the other item
        val otherSlot = if (other.type == Material.RED_WOOL) event.slot + 1 else if (other.type == Material.GREEN_WOOL) event.slot - 1 else null ?: return
        otherMeta.removeEnchant(Enchantment.DURABILITY)
        other.itemMeta = otherMeta
        event.inventory.setItem(otherSlot, other)

        (event.whoClicked as Player).updateInventory()
    }

    private fun addSettingButtonsInColorToInventory(builder: InventoryBuilder, row: Int, value: Boolean) {
        var itemGreen: ItemStack = if (value) Items.settingsGreen.clone().addEnchantment(Enchantment.DURABILITY, 1).build() else Items.settingsGreen.clone().build()
        var itemRed: ItemStack = if (value) Items.settingsRed.clone().addEnchantment(Enchantment.DURABILITY, 1).build() else Items.settingsRed.clone().build()
        builder.setItem(row * 9 + 6, itemGreen)
        builder.setItem(row * 9 + 7, itemRed)
    }
}




/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "Inventories.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
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
    fun setupGameInventory(game: Game): Inventory = InventoryBuilder("§d${game.name}§6: Game Setup", 18).setItem(0, Items.addLobbyButton).setItem(1, Items.addSpectatorButton).setItem(2, Items.addArenaCenterButton).setItem(3, Items.addSpawnButton).setItem(4, Items.addBackButton).setItem(5, Items.addEndButton).setItem(7, Items.settingsHead).setItem(8, Items.finishButton).fillInventory(ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1).build()).create()

    val gamesInventory = InventoryBuilder(TheHunter.prefix + "§6Games", 54).addGamesToInventory().fillInventory(ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1).build()).create()
    fun createSettingsInventory(game: Game): Inventory {
        //TODO: Testing
        val builder = InventoryBuilder("§d${game.name}§6: Game Settings", 54)
        builder.setItem(3 + 9 * 0, Items.teamsAllowedHead)
        addSettingButtonsInColorToInventory(builder, 0, game.teamsAllowed)
        builder.setItem(3 + 9 * 1, Items.teamsSize)
        addSettingButtonsInColorToInventory(builder, 1, game.teamsAllowed)
        addSettingButtonsInColorToInventory(builder, 2, game.teamDamage)
        builder.setItem(3 + 9 * 2, Items.teamsDamage)
        builder.setItem(3 + 9 * 3, Items.minPlayers)
        addColoredWool(builder, 3)
        builder.setItem(3 + 9 * 4, Items.maxPlayers)
        addColoredWool(builder, 4)
        builder.setItem(3 + 9 * 5, Items.borderSize)
        builder.setItem(53, Items.saveButton)
        addColoredWool(builder, 5)
        return builder.fillInventory(ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1).build()).create()

    }


    fun itemEnchantmentSwitcher(event: InventoryClickEvent) {
        val item = event.currentItem!!
        val meta = item.itemMeta!!
        if (item.enchantments.isNotEmpty()) return

        if (item.type != Material.RED_WOOL && item.type != Material.GREEN_WOOL) return
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

    private fun addColoredWool(builder: InventoryBuilder, row: Int) {
        builder.setItem(row * 9 + 6, Items.settingsWoolGreen.build())
        builder.setItem(row * 9 + 7, Items.settingsWoolRed.build())
    }


    private fun addSettingButtonsInColorToInventory(builder: InventoryBuilder, row: Int, value: Boolean) {
        val itemGreen: ItemStack = if (value) Items.settingsWoolGreen.clone().addEnchantment(Enchantment.DURABILITY, 1).build() else Items.settingsWoolGreen.clone().build()
        val itemRed: ItemStack = if (value) Items.settingsWoolRed.clone().addEnchantment(Enchantment.DURABILITY, 1).build() else Items.settingsWoolRed.clone().build()
        builder.setItem(row * 9 + 6, itemGreen)
        builder.setItem(row * 9 + 7, itemRed)
    }
}




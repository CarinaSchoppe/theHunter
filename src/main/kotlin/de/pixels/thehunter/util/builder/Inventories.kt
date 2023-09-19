/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "Inventories.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */
package de.pixels.thehunter.util.builder

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.game.management.Game
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object Inventories {
    /**
     * Sets up the game inventory for the given game.
     *
     * @param game The game for which the inventory is being set up.
     * @return The created game inventory.
     */
    fun setupGameInventory(game: Game): Inventory =
        InventoryBuilder("§d${game.name}§6: Game Setup", 9).setItem(0, Items.addLobbyButton)
            .setItem(1, Items.addSpectatorButton).setItem(2, Items.addArenaCenterButton)
            .setItem(3, Items.addSpawnButton).setItem(4, Items.addBackButton).setItem(5, Items.addEndButton)
            .setItem(7, Items.settingsHead).setItem(8, Items.finishButton).fillInventory(
                ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("")
                    .addEnchantment(Enchantment.DURABILITY, 1)
                    .build()
            ).create()

    /**
     * Represents a game inventory for the player.
     * The inventory is constructed using the InventoryBuilder class and filled with game items.
     *
     * @property gamesInventory The constructed game inventory.
     *
     * @constructor Creates a game inventory using the InventoryBuilder class and fills it with game items.
     */
    val gamesInventory = InventoryBuilder(TheHunter.prefix + "§6Games", 54).addGamesToInventory().fillInventory(
        ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1)
            .build()
    ).create()


    /**
     * Variable representing the perks inventory.
     *
     * This inventory is created using an instance of the InventoryBuilder class and initialized with the prefix "TheHunter§6Perks"
     * and a size of 27 slots. It sets specific items at the desired slots using the setItem() method of the InventoryBuilder instance.
     * The items are retrieved from the Items class and include the batPerkItem, kangarooPerkItem, bearPerkItem, sonicPerkItem,
     * backpackerPerkItem, bloodhoundPerkItem, bombermanPerkItem, ninjaPerkItem, pigPerkItem, piratePerkItem, angelPerkItem,
     * and gamblerPerkItem.
     *
     * The inventory is then filled with a white stained glass pane item, created using the ItemBuilder class, with no display name
     * and an enchantment of DURABILITY level 1. This is accomplished using the fillInventory() method of the InventoryBuilder instance.
     *
     * Finally, the inventory is created with the create() method of the InventoryBuilder instance and assigned to the perksInventory variable.
     */
    val perksInventory = InventoryBuilder(TheHunter.prefix + "§6Perks", 27).setItem(1, Items.batPerkItem).setItem(3, Items.kangarooPerkItem).setItem(5, Items.bearPerkItem).setItem(7, Items.sonicPerkItem).setItem(10, Items.backpackerPerkItem).setItem(12, Items.bloodhoundPerkItem).setItem(14, Items.bombermanPerkItem).setItem(18, Items.ninjaPerkItem).setItem(20, Items.pigPerkItem).setItem(22, Items.piratePerkItem).setItem(26, Items.angelPerkItem).setItem(30, Items.gamblerPerkItem)
        .fillInventory(
            ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1)
                .build()
        ).create()

    /**
     * Creates an inventory for game settings.
     *
     * @param game the game for which the settings inventory is created
     * @return the inventory containing game settings
     */
    fun createSettingsInventory(game: Game): Inventory {
        val builder = InventoryBuilder("§d${game.name}§6: Game Settings", 54)
        builder.setItem(3 + 9 * 0, Items.teamsAllowedHead)
        addSettingButtonsInColorToInventory(builder, 0, game.teamsAllowed)
        builder.setItem(3 + 9 * 1, Items.teamsSize)
        addColoredWool(builder, 1)
        addSettingButtonsInColorToInventory(builder, 2, game.teamDamage)
        builder.setItem(3 + 9 * 2, Items.teamsDamage)
        builder.setItem(3 + 9 * 3, Items.minPlayers)
        addColoredWool(builder, 3)
        builder.setItem(3 + 9 * 4, Items.maxPlayers)
        addColoredWool(builder, 4)
        builder.setItem(3 + 9 * 5, Items.borderSize)
        builder.setItem(53, Items.saveButton)
        addColoredWool(builder, 5)
        return builder.fillInventory(
            ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1)
                .build()
        ).create()

    }


    /**
     * Switches the enchantment of the clicked item with the adjacent item in the inventory.
     *
     * @param event The InventoryClickEvent triggered when the item is clicked.
     */
    fun itemEnchantmentSwitcher(event: InventoryClickEvent) {
        val item = event.currentItem ?: return
        val meta = item.itemMeta ?: return

        if (item.enchantments.isNotEmpty() || item.type != Material.RED_WOOL && item.type != Material.GREEN_WOOL) return
        meta.addEnchant(Enchantment.DURABILITY, 1, true)
        item.itemMeta = meta
        event.inventory.setItem(event.slot, item)
        val other =
            (when (item.type) {
                Material.GREEN_WOOL -> event.inventory.getItem(event.slot + 1)
                Material.RED_WOOL -> event.inventory.getItem(
                    event.slot - 1
                )

                else -> null
            }) ?: return
        val otherMeta = other.itemMeta ?: return
        //get the slot of the other item
        val otherSlot =
            when (other.type) {
                Material.RED_WOOL -> event.slot + 1
                Material.GREEN_WOOL -> event.slot - 1
                else -> null
                    ?: return
            }
        otherMeta.removeEnchant(Enchantment.DURABILITY)
        other.itemMeta = otherMeta
        event.inventory.setItem(otherSlot, other)
    }

    /**
     * Adds colored wool items to the given inventory builder at the specified row.
     *
     * @param builder the inventory builder to add the colored wool items to
     * @param row the row index where the colored wool items should be added
     */
    private fun addColoredWool(builder: InventoryBuilder, row: Int) {
        val green = Items.settingsWoolGreen.clone().addDisplayName("§aPlus").build()
        green.removeEnchantment(Enchantment.DURABILITY)
        val red = Items.settingsWoolRed.clone().addDisplayName("§cMinus").build()
        red.removeEnchantment(Enchantment.DURABILITY)
        builder.setItem(row * 9 + 6, green)
        builder.setItem(row * 9 + 7, red)
    }


    /**
     * Adds setting buttons in color to the inventory.
     *
     * @param builder The inventory builder to add the buttons to.
     * @param row The row in the inventory to add the buttons.
     * @param value The value indicating whether to add the buttons in activated or deactivated state.
     */
    private fun addSettingButtonsInColorToInventory(builder: InventoryBuilder, row: Int, value: Boolean) {
        val itemGreen: ItemStack = if (value)
            Items.settingsWoolGreen.clone().addEnchantment(Enchantment.DURABILITY, 1).addDisplayName("§aActivate")
                .build()
        else Items.settingsWoolGreen.clone().addDisplayName("§aActivate").build()
        if (!value) itemGreen.removeEnchantment(Enchantment.DURABILITY)
        val itemRed: ItemStack = if (!value)
            Items.settingsWoolRed.clone().addEnchantment(Enchantment.DURABILITY, 1).addDisplayName("§cDeactivate")
                .build()
        else Items.settingsWoolRed.clone().addDisplayName("§cDeactivate").build()

        if (value) itemRed.removeEnchantment(Enchantment.DURABILITY)
        builder.setItem(row * 9 + 6, itemGreen)
        builder.setItem(row * 9 + 7, itemRed)
    }
}

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
import de.carina.thehunter.util.misc.Util
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment

object Inventories {
    val setupGameInventory = InventoryBuilder("§d${Util.currentGameSelected.name}§6: Game Setup", 18)
        .setItem(0, Items.addLobbyButton)
        .setItem(1, Items.addSpectatorButton)
        .setItem(2, Items.addArenaCenterButton)
        .setItem(3, Items.addSpawnButton)
        .setItem(4, Items.addBackButton)
        .setItem(5, Items.addEndButton)
        .setItem(7, Items.settingsHead)
        .setItem(8, Items.finishButton)
        .fillInventory(ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1).build()).create()

    val settingsGameInventory = InventoryBuilder("§d${Util.currentGameSelected.name}§6: Game Settings", 54).create()

    val gamesInventory = InventoryBuilder(TheHunter.prefix + "§6Games", 54).addGamesToInventory().fillInventory(ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).addDisplayName("").addEnchantment(Enchantment.DURABILITY, 1).build()).create()


}




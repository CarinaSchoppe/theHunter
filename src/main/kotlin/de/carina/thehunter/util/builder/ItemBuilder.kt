/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 6/1/22, 4:01 PM All contents of "ItemBuilder.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.util.builder

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class ItemBuilder(material: Material) {

    constructor(builder: ItemBuilder) : this(builder.itemStack.type) {
        this.itemStack = builder.itemStack
        this.itemMeta = builder.itemMeta
    }

    private var itemStack = ItemStack(material)
    private var itemMeta = itemStack.itemMeta

    fun clone(): ItemBuilder {
        return ItemBuilder(this)
    }

    fun addDisplayName(name: String): ItemBuilder {
        itemMeta.displayName(LegacyComponentSerializer.legacySection().deserialize(name))
        itemStack.itemMeta = itemMeta
        return this
    }

    fun addAmount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }


    fun addLore(lore: List<String>): ItemBuilder {
        val myLore = lore.map { LegacyComponentSerializer.legacySection().deserialize(it) }
        itemMeta.lore(myLore)
        itemStack.itemMeta = itemMeta
        return this
    }

    fun addLore(text: String): ItemBuilder {
        val lore = itemMeta.lore() ?: mutableListOf()
        lore.add(LegacyComponentSerializer.legacySection().deserialize(text))
        itemMeta.lore(lore)
        itemStack.itemMeta = itemMeta
        return this
    }

    fun addEnchantment(enchantment: Enchantment, level: Int): ItemBuilder {
        itemMeta.addEnchant(enchantment, level, true)
        itemStack.itemMeta = itemMeta
        return this
    }

    fun build(): ItemStack {
        itemStack.itemMeta = itemMeta
        return itemStack
    }

    companion object {
        fun getDisplayName(item: ItemStack): String {
            return LegacyComponentSerializer.legacySection().serialize(item.itemMeta.displayName() ?: Component.text(""))
        }

        fun getLores(item: ItemStack): List<String> {
            return item.itemMeta.lore()?.map { LegacyComponentSerializer.legacySection().serialize(it) } ?: listOf()
        }
    }

}
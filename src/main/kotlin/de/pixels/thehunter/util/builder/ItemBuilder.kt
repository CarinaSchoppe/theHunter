/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "ItemBuilder.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.builder

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class ItemBuilder(material: Material) {

    /**
     * Constructs an instance of Item using an ItemBuilder.
     *
     * @param builder The ItemBuilder used to construct the Item.
     */
    constructor(builder: ItemBuilder) : this(builder.itemStack.type) {
        this.itemStack = builder.itemStack
        this.itemMeta = builder.itemMeta
    }

    /**
     * Represents an item stack with a specific material.
     *
     * @property itemStack The instance of the item stack.
     */
    private var itemStack = ItemStack(material)

    /**
     * Represents the metadata associated with an item.
     * This metadata provides additional properties and behaviors for the item.
     *
     * @property itemMeta The metadata associated with the item stack.
     */
    private var itemMeta = itemStack.itemMeta

    /**
     * Returns a clone of the current instance of ItemBuilder.
     *
     * @return A new instance of ItemBuilder created by cloning the current instance.
     */
    fun clone(): ItemBuilder {
        return ItemBuilder(this)
    }


    /**
     * Sets the display name of the item.
     *
     * @param name the display name of the item
     * @return the instance of ItemBuilder
     */
    fun addDisplayName(name: String): ItemBuilder {
        itemMeta.displayName(LegacyComponentSerializer.legacySection().deserialize(name))
        itemStack.itemMeta = itemMeta
        return this
    }

    /**
     * Sets the amount of the item in the item stack.
     *
     * @param amount the new amount of the item
     * @return the ItemBuilder instance with the updated amount
     */
    fun addAmount(amount: Int): ItemBuilder {
        itemStack.amount = amount
        return this
    }


    /**
     * Adds lore to the item.
     *
     * @param lore The list of lore lines to be added.
     * @return The ItemBuilder instance with the updated lore.
     */
    fun addLore(lore: List<String>): ItemBuilder {
        val myLore = lore.map { LegacyComponentSerializer.legacySection().deserialize(it) }
        itemMeta.lore(myLore)
        itemStack.itemMeta = itemMeta
        return this
    }

    /**
     * Adds a lore to the item.
     *
     * @param text The text to be added as lore.
     * @return This ItemBuilder instance.
     */
    fun addLore(text: String): ItemBuilder {
        val lore = itemMeta.lore() ?: mutableListOf()
        lore.add(LegacyComponentSerializer.legacySection().deserialize(text))
        itemMeta.lore(lore)
        itemStack.itemMeta = itemMeta
        return this
    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment the enchantment to add
     * @param level the level of the enchantment
     * @return the updated ItemBuilder object
     */
    fun addEnchantment(enchantment: Enchantment, level: Int): ItemBuilder {
        itemMeta.addEnchant(enchantment, level, true)
        itemStack.itemMeta = itemMeta
        return this
    }

    /**
     * Builds an ItemStack using the provided itemMeta and returns it.
     *
     * @return The created ItemStack.
     */
    fun build(): ItemStack {
        itemStack.itemMeta = itemMeta
        return itemStack
    }

    companion object {
        /**
         * Retrieves the display name of a given ItemStack.
         *
         * @param item The ItemStack to retrieve the display name from.
         * @return The display name of the ItemStack as a string. If the display name is null, an empty string is returned.
         */
        fun getDisplayName(item: ItemStack): String {
            return LegacyComponentSerializer.legacySection()
                .serialize(item.itemMeta.displayName() ?: Component.text(""))
        }

        /**
         * Retrieves the lore of the given item.
         *
         * @param item The item from which to retrieve the lore.
         * @return The lore of the item as a list of strings.
         */
        fun getLores(item: ItemStack): List<String> {
            return item.itemMeta.lore()?.map { LegacyComponentSerializer.legacySection().serialize(it) } ?: listOf()
        }
    }

}

package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType


class EnergyDrink : Listener {

    fun createEnergyDrinkItem(): ItemStack {
        return ItemBuilder(Material.POTION).addDisplayName(TheHunter.PREFIX + "§6Energy-Drink").addLore("§7Energie- gives you some effects on right-clicking").addEnchantment(Enchantment.DURABILITY, 1).build()
    }

    @EventHandler
    fun onEnergyDinkDrink(event: PlayerInteractEvent) {
        if (event.item == null)
            return
        if (!event.item!!.hasItemMeta()) return
        if (event.item!!.itemMeta != createEnergyDrinkItem().itemMeta)
            return
        if (event.player.inventory.itemInMainHand.itemMeta != createEnergyDrinkItem().itemMeta)
            return
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        if (!event.action.isRightClick)
            return

        if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
            return

        if (EyeSpy.inEyeSpy.contains(event.player))
            return


        event.isCancelled = true
        if (GamesHandler.playerInGames[event.player]!!.gameItems!!.items["EnergyDrink"] == false)
            return

        event.player.addPotionEffects(createAndAddPotionEffects())
        event.player.sendMessage(TheHunter.instance.messages.messagesMap["energydrink-consumed"]!!)

    }

    private fun createAndAddPotionEffects(): MutableSet<PotionEffect> {
        val potionsEffects = mutableSetOf<PotionEffect>()
        val saturation = PotionEffect(PotionEffectType.SATURATION, 2 * 20, 2)
        val speed = PotionEffect(PotionEffectType.SPEED, 22 * 20, 1)
        val regeneration = PotionEffect(PotionEffectType.REGENERATION, 3 * 20, 1)
        val absorption = PotionEffect(PotionEffectType.ABSORPTION, 25 * 20, 1)
        val nightVision = PotionEffect(PotionEffectType.NIGHT_VISION, 35 * 20, 1)
        val jump = PotionEffect(PotionEffectType.JUMP, 25 * 20, 2)
        val dolphinsGrace = PotionEffect(PotionEffectType.DOLPHINS_GRACE, 30 * 20, 2)
        val glowing = PotionEffect(PotionEffectType.GLOWING, 25 * 20, 1)
        val waterBreathing = PotionEffect(PotionEffectType.WATER_BREATHING, 25 * 20, 1)
        potionsEffects.add(saturation)
        potionsEffects.add(speed)
        potionsEffects.add(regeneration)
        potionsEffects.add(absorption)
        potionsEffects.add(nightVision)
        potionsEffects.add(jump)
        potionsEffects.add(dolphinsGrace)
        potionsEffects.add(glowing)
        potionsEffects.add(waterBreathing)
        return potionsEffects
    }
}
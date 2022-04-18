package de.carina.thehunter.guns

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Ak {
    companion object {
        val shotBullets = mutableMapOf<Player, MutableSet<Arrow>>()
        var reloading = mutableMapOf<Player, Boolean>()
        var magazine = mutableMapOf<Player, Int>()

        fun createAkGunItem(): ItemStack {
            return ItemBuilder(Material.IRON_HOE).addDisplayName(TheHunter.PREFIX + "§7AK-47").addEnchantment(Enchantment.DURABILITY, 1).addLore("§7Right-click to shoot").build()
        }
    }

    fun shootProjectile(player: Player) {
        val arrow = player.launchProjectile(Arrow::class.java, player.location.direction.multiply(GamesHandler.playerInGames[player]!!.gameItems.guns["ak-power"]!!))
        arrow.damage = 0.0
        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f)
        removeAmmo(player, 1)
        arrow.shooter = player
        shotBullets[player]!!.add(arrow)
    }

    fun shoot(player: Player): Boolean {
        if (reloading[player] == true) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reloading"]!!)
            return false
        }
        if (magazine[player]!! <= 0) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-out-of-ammo"]!!)
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reloading"]!!)
            reloadGun(player)
            return false
        }

        shootProjectile(player)
        return true
    }

    fun checkAmmoPossible(player: Player): Boolean {
        if (!hasAmmo(player, de.carina.thehunter.items.chest.ammo.Ak.createAKAmmo())) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-out-of-ammo"]!!)
            return false
        }
        return true
    }

    fun reloadGun(player: Player) {
        if (reloading[player] == true) {
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reloading"]!!)
            return
        }
        if (!checkAmmoPossible(player)) return

        reloading[player] = true
        reload(player)
    }

    private fun reload(player: Player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(TheHunter.instance, {
            reloading[player] = false
            val amount = getAmmoAmount(player, de.carina.thehunter.items.chest.ammo.Ak.createAKAmmo())
            if (amount < GamesHandler.playerInGames[player]!!.gameItems.guns["ak-magazine"]!!) magazine[player] = GamesHandler.playerInGames[player]!!.gameItems.guns["ak-ammo"]!!
            else magazine[player] = amount
            player.sendMessage(TheHunter.instance.messages.messagesMap["gun-reloaded"]!!)
        }, 20L * GamesHandler.playerInGames[player]!!.gameItems.guns["ak-reload"]!!)
    }

    fun hasAmmo(player: Player, ammo: ItemStack): Boolean {
        return getAmmoAmount(player, ammo) > 0
    }

    fun removeAmmo(player: Player, amount: Int) {
        var ammo: ItemStack = createAkGunItem()
        for ((slot, item) in player.inventory.contents!!.withIndex()) {
            if (item == null) continue
            if (!item.hasItemMeta()) continue
            if (item.itemMeta != ammo.itemMeta) continue

            if (item.amount - amount > 0) item.amount -= amount
            else player.inventory.setItem(slot, ItemStack(Material.AIR))
            return


        }
    }

    fun getAmmoAmount(player: Player, ammo: ItemStack): Int {
        var amount = 0
        for (item in player.inventory.contents!!) {
            if (item == null) continue
            if (!item.hasItemMeta()) continue
            if (item.itemMeta == ammo.itemMeta) amount += item.amount
        }
        return amount
    }
}
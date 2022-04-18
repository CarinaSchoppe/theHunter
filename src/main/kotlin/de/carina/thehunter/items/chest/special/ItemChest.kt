package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.game.Game
import de.carina.thehunter.util.game.GamesHandler
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*

class ItemChest(private val game: Game) {

    val chests = mutableMapOf<Location, Inventory>()
    fun createItemInventory(): Inventory {
        val inventory = Bukkit.createInventory(null, 54, LegacyComponentSerializer.legacySection().deserialize(TheHunter.PREFIX + "§7Chest"))
        val items = mutableListOf<ItemStack>()
        if (game.gameItems.items["EggBomb"] == true) {
            val item = EggBomb.createEggBomb()
            item.amount = game.gameItems.items["eggbomb-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["EnergyDrink"] == true) {
            val item = EnergyDrink.createEnergyDrinkItem()
            item.amount = game.gameItems.items["energydrink-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["EyeSpy"] == true) {
            val item = EyeSpy.createEyeSpyItem()
            item.amount = game.gameItems.items["eyespy-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["Food"] == true) {
            val item = Food.createFoodItem()
            item.amount = game.gameItems.items["food-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["Healer"] == true) {
            val item = Healer.createHealerItem()
            item.amount = game.gameItems.items["healer-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["JumpStick"] == true) {
            val item = JumpStick.createJumpStick()
            item.amount = game.gameItems.items["jumpstick-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["Knife"] == true) {
            val item = Knife.createKnifeItem()
            item.amount = game.gameItems.items["knife-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["Swapper"] == true) {
            val item = Swapper.createSwapperItem()
            item.amount = game.gameItems.items["swapper-amount"] as Int
            items.add(item)
        }
        if (game.gameItems.items["Tracker"] == true) {
            val item = Tracker.createTrackerItem()
            item.amount = game.gameItems.items["tracker-amount"] as Int
            items.add(item)
        }

        repeat(Random().nextInt(game.gameItems.items["item-amounts"] as Int) + 1) {
            val item = items[Random().nextInt(items.size)]
            while (true) {
                val place = Random().nextInt(54)
                if (inventory.getItem(place) == null) {
                    inventory.setItem(place, item)
                    break
                }
            }
        }
        return inventory
    }

    fun makeChestsFall() {
        if (!game.chestFall)
            return
        repeat(game.chestAmount) {
            //create a random x and y coordinate based on the game radius
            val x = Random().nextInt(game.worldBoarderController.worldBoarderSize * 2) - game.worldBoarderController.worldBoarderSize
            val y = Random().nextInt(game.worldBoarderController.worldBoarderSize * 2) - game.worldBoarderController.worldBoarderSize
            val location = game.arenaCenter!!.clone().add(x as Double, y as Double, 255.0)
            val block = location.world.spawnFallingBlock(location, Material.BEACON.createBlockData())
            game.gameEntities.add(block)
            GamesHandler.entitiesInGames[block] = game

        }
    }

}




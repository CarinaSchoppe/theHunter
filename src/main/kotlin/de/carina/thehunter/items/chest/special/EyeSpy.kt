package de.carina.thehunter.items.chest.special

import de.carina.thehunter.TheHunter
import de.carina.thehunter.gamestates.IngameState
import de.carina.thehunter.util.builder.ItemBuilder
import de.carina.thehunter.util.game.GamesHandler
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.title.Title
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class EyeSpy : Listener {

    companion object {
        val inEyeSpy = mutableSetOf<Player>()
        val lastPlayerLocation = mutableMapOf<Player, Location>()
        private val mapPlayerTime = mutableMapOf<Player, Int>()
    }


    fun createEyeSpyItem(): ItemStack {
        return ItemBuilder(Material.ENDER_EYE).addDisplayName(TheHunter.PREFIX + "§aEye Spy").addLore("§7This Article will let you see the other player for 10 sec").addLore("§7Right-click to activate").build()
    }

    @EventHandler
    fun onEyeSpyUse(event: PlayerInteractEvent) {
        if (event.item == null)
            return
        if (!event.item!!.hasItemMeta()) return
        if (event.item!!.itemMeta != createEyeSpyItem().itemMeta)
            return
        if (event.player.inventory.itemInMainHand.itemMeta != createEyeSpyItem().itemMeta)
            return
        if (!GamesHandler.playerInGames.containsKey(event.player))
            return
        if (!event.action.isRightClick)
            return

        if (GamesHandler.playerInGames[event.player]!!.currentGameState !is IngameState)
            return

        if (inEyeSpy.contains(event.player))
            return

        val target = GamesHandler.playerInGames[event.player]!!.players.filter { it != event.player }.random()

        event.isCancelled = true
        if (GamesHandler.playerInGames[event.player]!!.gameItems!!.items["EyeSpy"] == false)
            return
        setCamera(event.player, target)
    }


    private fun setCamera(player: Player, target: Player) {
        lastPlayerLocation[player] = player.location
        player.teleport(target.location)
        inEyeSpy.add(player)
        player.gameMode = GameMode.SPECTATOR
        player.spectatorTarget = target
        mapPlayerTime[player] = (TheHunter.instance.itemSettings.settingsMap["eye-spy-duration"]!! as Long).toInt()
        TheHunter.instance.server.scheduler.scheduleSyncRepeatingTask(TheHunter.instance, {
            mapPlayerTime[player] = mapPlayerTime[player]!! - 1
            when (mapPlayerTime[player]!!) {
                3 -> {
                    player.showTitle(Title.title(LegacyComponentSerializer.legacySection().deserialize("§6${mapPlayerTime[player]}"), Component.text("")))
                }
                2 -> {
                    player.showTitle(Title.title(LegacyComponentSerializer.legacySection().deserialize("§6${mapPlayerTime[player]}"), Component.text("")))
                }
                1 -> {
                    player.showTitle(Title.title(LegacyComponentSerializer.legacySection().deserialize("§6${mapPlayerTime[player]}"), Component.text("")))
                }
            }
        }, 20L, 20L)

        TheHunter.instance.server.scheduler.scheduleSyncDelayedTask(TheHunter.instance, {
            player.teleport(lastPlayerLocation[player]!!)
            player.gameMode = GameMode.SURVIVAL
            player.spectatorTarget = null
            inEyeSpy.remove(player)
        }, 20L * TheHunter.instance.itemSettings.settingsMap["eye-spy-duration"]!! as Long)
    }

}

package de.pixels.thehunter.util.game.ingame

import net.minecraft.network.chat.ChatComponentText
import net.minecraft.server.level.EntityPlayer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import java.util.*


class NPC(val id: UUID, val skinPlayer: Player, val location: Location) : Listener {

    companion object {
        private val NPCs = mutableMapOf<UUID, NPC>()
    }

    init {
        NPCs[id] = this
    }

    fun despawn() {
        NPCs.remove(id)
        val infoPacket = PacketPlayOutPlayerInfo(
            PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER,
            (skinPlayer as CraftPlayer).getHandle()
        )
        Bukkit.getOnlinePlayers().forEach { p: Player? ->
            (p as CraftPlayer?).getHandle().playerConnection.sendPacket(
                infoPacket
            )
        }
    }

    @EventHandler
    fun onPlayerInteractEntity(event: PlayerInteractEntityEvent) {
        if (event.rightClicked is EntityPlayer) {
            val npc: EntityPlayer = event.rightClicked as EntityPlayer
            if (NPCs.containsKey(npc.getUniqueID())) {
                event.player.sendMessage(ChatComponentText("Hello!"))
                event.isCancelled = true
            }
        }
    }


}
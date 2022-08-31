package de.carina.thehunter.guns

import org.bukkit.entity.Player

interface Gun {

    fun shoot(player: Player): Boolean
}

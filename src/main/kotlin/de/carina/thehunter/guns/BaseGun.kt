/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 13.04.22, 14:11 by Carina The Latest changes made by Carina on 13.04.22, 14:11 All contents of "BaseGun.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.guns

import org.bukkit.entity.Player
import org.bukkit.entity.Snowball

abstract class BaseGun {
    val shotBullets = mutableMapOf<Player, MutableSet<Snowball>>()
    var reloading = mutableMapOf<Player, Boolean>()
    abstract var magazineSize: Int


    fun shoot(player: Player) {
        TODO("Implement")
    }
}
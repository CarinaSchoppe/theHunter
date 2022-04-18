/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 18.04.22, 23:29 by Carina The Latest changes made by Carina on 18.04.22, 23:29 All contents of "IngameCountdown.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */

package de.carina.thehunter.countdowns

import de.carina.thehunter.util.game.Game

class IngameCountdown(game: Game) : Countdown(game) {

    override var duration: Int = 60


    override fun idle() {

    }

    override fun start() {

    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override var isIdle: Boolean = false
    override val id: Int = Countdowns.INGAME_COUNTDOWN.id
}
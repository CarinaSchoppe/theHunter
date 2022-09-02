package de.carina.thehunter.util.database

import de.carina.thehunter.TheHunter

class MySQL {


    fun createDatabaseIfNotExists(): Boolean {
        if (!(TheHunter.instance.settings.settingsMap["mysql"]!! as Boolean))
            return false

        if (!(TheHunter.instance.settings.settingsMap["sql-lite"]!! as Boolean))
            return false

        //check if the .db file exists


        return true
    }

    fun addAllPlayersFromDataBaseToPLayerList() {

    }

}

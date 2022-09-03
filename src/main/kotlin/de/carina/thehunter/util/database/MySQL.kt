package de.carina.thehunter.util.database

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.misc.StatsPlayer
import de.carina.thehunter.util.misc.StatsSystem
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class MySQL {

    init {
        if (TheHunter.instance.settings.settingsMap["mysql"] as Boolean) {
            createDatabaseIfNotExists()
        }
    }

    companion object {
        lateinit var connection: Connection
    }

    private fun createDatabaseIfNotExists(): Boolean {
        if (!(TheHunter.instance.settings.settingsMap["mysql"]!! as Boolean))
            return false


        //check if the .db file exists
        val databaseFile = File(TheHunter.instance.settings.settingsMap["sqlite-path"]!! as String)
        if (!databaseFile.exists()) {
            databaseFile.createNewFile()
        }

        val host = TheHunter.instance.settings.settingsMap["mysql-host"]!! as String
        val port = TheHunter.instance.settings.settingsMap["mysql-port"]!! as String
        val database = TheHunter.instance.settings.settingsMap["mysql-database"]!! as String
        val username = TheHunter.instance.settings.settingsMap["mysql-user"]!! as String
        val password = TheHunter.instance.settings.settingsMap["mysql-password"]!! as String
        //create a connection to the mysql database
        connection = if (TheHunter.instance.settings.settingsMap["sqlite-enable"] as Boolean) DriverManager.getConnection("jdbc:sqlite:${databaseFile.absolutePath}")
        else DriverManager.getConnection("jdbc:mysql://$host:$port/$database?useSSL=false", username, password)

        createTableStatsPlayer()

        addAllPlayersFromDataBaseToPLayerList()


        return true
    }

    private fun createTableStatsPlayer(): Boolean {
        val sqlCommand = "CREATE TABLE IF NOT EXISTS statsPlayer (" +
                "uuid VARCHAR(36) NOT NULL," +
                "kills INT NOT NULL," +
                "deaths INT NOT NULL," +
                "points INT NOT NULL," +
                "kdr DOUBLE NOT NULL," +
                "wins INT NOT NULL," +
                "loses INT NOT NULL," +
                "games INT NOT NULL," +
                "PRIMARY KEY (uuid)" +
                ");"
        //execute the sqlCommand on the connection
        val statement = connection.prepareStatement(sqlCommand)
        statement.execute(sqlCommand)
        return true
    }

    private fun addAllPlayersFromDataBaseToPLayerList(): Boolean {
        if (!(TheHunter.instance.settings.settingsMap["mysql"]!! as Boolean))
            return false


        //check if the .db file exists
        val databaseFile = File(TheHunter.instance.settings.settingsMap["sqlite-path"]!! as String)
        if (!databaseFile.exists()) {
            databaseFile.createNewFile()
        }

        //create sqlite connection to databaseFile
        val connection = DriverManager.getConnection("jdbc:sqlite:${databaseFile.absolutePath}")
        val sqlCommand = "SELECT * FROM statsPlayer"
        val statement = connection.prepareStatement(sqlCommand)
        val result = statement.executeQuery(sqlCommand)
        while (result.next()) {
            StatsSystem.playerStats[UUID.fromString(result.getString("uuid"))] = StatsPlayer(result.getInt("kills"), result.getInt("deaths"), result.getInt("points"), result.getDouble("kdr"), result.getInt("wins"), result.getInt("loses"), result.getInt("games"))

        }
        return true

    }

}

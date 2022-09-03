package de.carina.thehunter.util.database

import de.carina.thehunter.TheHunter
import de.carina.thehunter.util.files.BaseFile
import de.carina.thehunter.util.misc.StatsPlayer
import de.carina.thehunter.util.misc.StatsSystem
import org.bukkit.Bukkit
import org.bukkit.ChatColor
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
        if (!(TheHunter.instance.settings.settingsMap["mysql"]!! as Boolean)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["prefix"] as String) + "§7Using YML-Settings file")

            return false
        }


        //check if the .db file exists
        //get plugins folderpath

        lateinit var databaseFile: File
        if (TheHunter.instance.settings.settingsMap["sqlite-enable"] as Boolean) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["prefix"] as String) + "§aUsing SQLite-Settings file")

            databaseFile = try {
                val databaseFile = File(TheHunter.instance.settings.settingsMap["sqlite-path"]!! as String)
                if (!databaseFile.exists()) {
                    databaseFile.createNewFile()
                }
                databaseFile
            } catch (e: Exception) {
                val file = File(BaseFile.gameFolder + "/database.db")
                if (!file.exists()) {
                    file.createNewFile()
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["prefix"] as String) + "§aCreating own database-file...")

                }
                file
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["prefix"] as String) + "§aUsing MySQL database connection!")

        }

        val host = TheHunter.instance.settings.settingsMap["mysql-host"]!! as String
        val port = TheHunter.instance.settings.settingsMap["mysql-port"]!! as Int
        val database = TheHunter.instance.settings.settingsMap["mysql-database"]!! as String
        val username = TheHunter.instance.settings.settingsMap["mysql-user"]!! as String
        val password = TheHunter.instance.settings.settingsMap["mysql-password"]!! as String
        //create a connection to the mysql database
        connection = if (TheHunter.instance.settings.settingsMap["sqlite-enable"] as Boolean) DriverManager.getConnection("jdbc:sqlite:${databaseFile.absolutePath}")
        else DriverManager.getConnection("jdbc:mysql://$host:$port/$database?useSSL=false", username, password)
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["prefix"] as String) + "§aConnected to database!")

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
        statement.execute()
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["prefix"] as String) + "§aCreating table statsPlayer...")

        return true
    }

    private fun addAllPlayersFromDataBaseToPLayerList(): Boolean {
        if (!(TheHunter.instance.settings.settingsMap["mysql"]!! as Boolean))
            return false


        //create sqlite connection to databaseFile
        val sqlCommand = "SELECT * FROM statsPlayer"
        val statement = connection.prepareStatement(sqlCommand)
        val result = statement.executeQuery()
        while (result.next()) {
            StatsSystem.playerStats[UUID.fromString(result.getString("uuid"))] = StatsPlayer(result.getInt("kills"), result.getInt("deaths"), result.getInt("points"), result.getDouble("kdr"), result.getInt("wins"), result.getInt("loses"), result.getInt("games"))

        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', TheHunter.instance.settings.settingsMap["prefix"] as String) + "§aAdded all players from database to playerlist...")

        return true

    }

}

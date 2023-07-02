/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 9/26/22, 11:00 PM All contents of "MySQL.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */

package de.pixels.thehunter.util.database

import de.pixels.thehunter.TheHunter
import de.pixels.thehunter.util.files.BaseFile
import de.pixels.thehunter.util.game.management.StatsPlayer
import de.pixels.thehunter.util.game.management.StatsSystem
import de.pixels.thehunter.util.misc.ConstantStrings
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class DatabaseHandler {

    init {
        if (TheHunter.instance.settings.settingsMap["mysql"] as Boolean) {
            createDatabaseIfNotExists()
        }
    }

    /**
     * The `Companion` class represents a singleton object that provides access to a `Connection` instance.
     */
    companion object {
        /**
         * The `connection` variable represents a connection object to a database.
         * It is declared using the `lateinit` keyword which means it will be initialized at a later point in the program.
         *
         * @property connection The connection object that provides access to a database.
         */
        lateinit var connection: Connection
    }

    /**
     * Creates the database if it doesn't already exist.
     *
     * @return True if the database was created or already exists, false if not.
     */
    private fun createDatabaseIfNotExists(): Boolean {
        if (!(TheHunter.instance.settings.settingsMap["mysql"] as Boolean)) {
            Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes(
                    '&',
                    TheHunter.instance.settings.settingsMap["prefix"] as String
                ) + "§7Using YML-Settings file"
            )

            return false
        }


        //check if the .db file exists
        //get plugins folderpath

        lateinit var databaseFile: File
        if (TheHunter.instance.settings.settingsMap["sqlite-enable"] as Boolean) {
            Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes(
                    '&',
                    TheHunter.instance.settings.settingsMap["prefix"] as String
                ) + "§aUsing SQLite-Settings file"
            )

            databaseFile = try {
                val databaseFile =
                    File(TheHunter.instance.settings.settingsMap[ConstantStrings.SQLITE_PATH] as String)
                if (!databaseFile.exists()) {
                    databaseFile.createNewFile()
                }
                databaseFile
            } catch (e: Exception) {
                val file = File(BaseFile.GAME_FOLDER + "/database.db")
                if (!file.exists()) {
                    file.createNewFile()
                    TheHunter.instance.settings.settingsMap[ConstantStrings.SQLITE_PATH] = file.absolutePath
                    TheHunter.instance.settings.yml[ConstantStrings.SQLITE_PATH, file.absolutePath]
                    TheHunter.instance.settings.yml.save(TheHunter.instance.settings.file)
                    Bukkit.getConsoleSender().sendMessage(
                        ChatColor.translateAlternateColorCodes(
                            '&',
                            TheHunter.instance.settings.settingsMap["prefix"] as String
                        ) + "§aCreating own database-file..."
                    )

                }
                file
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes(
                    '&',
                    TheHunter.instance.settings.settingsMap["prefix"] as String
                ) + "§aUsing MySQL database connection!"
            )

        }

        val host = TheHunter.instance.settings.settingsMap["mysql-host"] as String
        val port = TheHunter.instance.settings.settingsMap["mysql-port"] as Int
        val database = TheHunter.instance.settings.settingsMap["mysql-database"] as String
        val username = TheHunter.instance.settings.settingsMap["mysql-user"] as String
        val password = TheHunter.instance.settings.settingsMap["mysql-password"] as String
        //create a connection to the mysql database
        connection =
            if (TheHunter.instance.settings.settingsMap["sqlite-enable"] as Boolean) DriverManager.getConnection("jdbc:sqlite:${databaseFile.absolutePath}")
            else DriverManager.getConnection("jdbc:mysql://$host:$port/$database?useSSL=false", username, password)
        Bukkit.getConsoleSender().sendMessage(
            ChatColor.translateAlternateColorCodes(
                '&',
                TheHunter.instance.settings.settingsMap["prefix"] as String
            ) + "§aConnected to database!"
        )

        createTableStatsPlayer()
        addAllPlayersFromDataBaseToPLayerList()

        return true
    }

    /**
     * Creates the statsPlayer table in the database if it does not already exist.
     * This table is used to store player statistics such as kills, deaths, points, kdr, wins, loses, and games.
     *
     * @see TheHunter.instance.settings.settingsMap
     */
    private fun createTableStatsPlayer() {
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
        Bukkit.getConsoleSender().sendMessage(
            ChatColor.translateAlternateColorCodes(
                '&',
                TheHunter.instance.settings.settingsMap["prefix"] as String
            ) + "§aCreating table statsPlayer..."
        )

    }

    /**
     * Adds all players from the database to the player list.
     *
     * @return true if the players were successfully added, false otherwise.
     */
    private fun addAllPlayersFromDataBaseToPLayerList(): Boolean {
        if (!(TheHunter.instance.settings.settingsMap["mysql"] as Boolean))
            return false


        //create sqlite connection to databaseFile
        val sqlCommand = "SELECT * FROM statsPlayer"
        val statement = connection.prepareStatement(sqlCommand)
        val result = statement.executeQuery()
        while (result.next()) {
            StatsSystem.playerStats[UUID.fromString(result.getString("uuid"))] = StatsPlayer(
                result.getInt("kills"),
                result.getInt("deaths"),
                result.getInt("points"),
                result.getDouble("kdr"),
                result.getInt("wins"),
                result.getInt("loses"),
                result.getInt("games")
            )

        }
        Bukkit.getConsoleSender().sendMessage(
            ChatColor.translateAlternateColorCodes(
                '&',
                TheHunter.instance.settings.settingsMap["prefix"] as String
            ) + "§aAdded all players from database to playerlist..."
        )

        return true

    }

}

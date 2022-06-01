/*
 * Copyright Notice for theHunterRemaster
 * Copyright (c) at Carina Sophie Schoppe 2022
 * File created on 6/1/22, 4:23 PM by Carina The Latest changes made by Carina on 4/19/22, 9:41 PM All contents of "Autoupdater.kt" are protected by copyright.
 * The copyright law, unless expressly indicated otherwise, is
 * at Carina Sophie Schoppe. All rights reserved
 * Any type of duplication, distribution, rental, sale, award,
 * Public accessibility or other use
 * requires the express written consent of Carina Sophie Schoppe.
 */
package de.carina.thehunter.util.misc

import de.carina.thehunter.TheHunter
import org.bukkit.Bukkit
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

class Autoupdater {
    fun checkUpdate(resourceId: String) {
        if (TheHunter.instance.settings.settingsMap["autoupdater"] as Boolean) {
            try {
                val con: HttpsURLConnection = URL("https://www.spigotmc.org/api/general.php")
                    .openConnection() as HttpsURLConnection
                con.doOutput = true
                con.requestMethod = "POST"
                con.outputStream.write(
                    "key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=$resourceId"
                        .toByteArray(StandardCharsets.UTF_8)
                )
                val version: String = BufferedReader(InputStreamReader(con.inputStream)).readLine()
                if (version != TheHunter.instance.version) {

                    Bukkit.getConsoleSender().sendMessage(TheHunter.instance.messages.messagesMap["autoupdate"]!!)
                } else {
                    Bukkit.getConsoleSender().sendMessage(TheHunter.instance.messages.messagesMap["update-true"]!!)
                }
            } catch (ex: Exception) {
                Bukkit.getConsoleSender().sendMessage(TheHunter.instance.messages.messagesMap["update-error"]!!)
            }
        }
    }
}

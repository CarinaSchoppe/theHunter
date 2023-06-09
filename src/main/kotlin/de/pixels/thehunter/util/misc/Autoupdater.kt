/*
 * Copyright Notice for theHunterRemaster Copyright (c) at Carina Sophie Schoppe 2022 File created on 9/26/22, 11:08 PM by Carina Sophie The Latest changes made by Carina Sophie on 8/30/22, 10:45 PM All contents of "Autoupdater.kt" are protected by copyright. The copyright law, unless expressly indicated otherwise, is at Carina Sophie Schoppe. All rights reserved Any type of duplication, distribution, rental, sale, award, Public accessibility or other use requires the express written consent of Carina Sophie Schoppe.
 */package de.pixels.thehunter.util.misc

import de.pixels.thehunter.TheHunter
import org.bukkit.Bukkit
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.net.ssl.HttpsURLConnection

class Autoupdater {
    fun checkUpdate(resourceId: String) {
        if (TheHunter.instance.settings.settingsMap["updater"] as Boolean) {
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

                    TheHunter.instance.messages.messagesMap["autoupdate"]?.let { Bukkit.getConsoleSender().sendMessage(it) }
                } else {
                    TheHunter.instance.messages.messagesMap["update-true"]?.let { Bukkit.getConsoleSender().sendMessage(it) }
                }
            } catch (ex: Exception) {
                TheHunter.instance.messages.messagesMap["update-error"]?.let { Bukkit.getConsoleSender().sendMessage(it) }
            }
        }
    }
}

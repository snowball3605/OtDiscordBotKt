package org.example.Interface

import net.dv8tion.jda.api.JDA

interface I_Plugin {
    fun onEnable(jda: JDA)
    fun onDisable()
}
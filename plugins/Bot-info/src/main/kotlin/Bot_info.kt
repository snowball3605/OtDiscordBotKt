package org.example

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.internal.interactions.CommandDataImpl
import org.example.Interface.I_Plugin
import org.example.Main.Command
import org.example.util.ExtractResource


class Bot_info : I_Plugin {
    override fun onEnable(jda: JDA) {
        ExtractResource.extract_resource("Bot-info/config.yml")
        ExtractResource.extract_resource("Bot-info/Language/zh-TW.yml")
        ExtractResource.extract_resource("Bot-info/Language/en-GB.yml")
        Class.forName("org.example.util.Get_Config")
        Class.forName("org.example.util.ExtractResource")
        Class.forName("org.example.util.LanguageLoader")
        jda.updateCommands().addCommands(CommandDataImpl("bot_info", "Bot Info")).queue()
        jda.addEventListener(Command())
    }

    override fun onDisable() {
    }

}
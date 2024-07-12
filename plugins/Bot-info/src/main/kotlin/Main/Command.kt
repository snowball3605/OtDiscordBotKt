package org.example.Main

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.example.util.Get_Config
import org.example.util.LanguageLoader

class Command : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if(!event.getName().equals("bot_info")) return
        val s:String =event.userLocale.locale

        if ((Get_Config.path_Yaml("Bot-info/config.yml", "Custom_information")).equals("false")) {
            if (Get_Config.path_here("Bot-info/Language/zh-TW.yml")) {
                val eb: EmbedBuilder = EmbedBuilder()
                    .setTitle("Bot Info")
                    .setDescription(LanguageLoader.getLanguag(s, "Server_count", "Bot-info/Language/") + event.jda.guilds.size + "\n")
                    .appendDescription(LanguageLoader.getLanguag(s, "User_count", "Bot-info/Language/") + event.jda.guilds.size + "\n")

                event.replyEmbeds(eb.build()).queue()
            }
        } else {
            var re_description: String = Get_Config.path_Yaml("Bot-info/config.yml", "Description")
            re_description = re_description.replace("{server_count}",  "${event.jda.guilds.size}").replace("{user_count}", "${event.jda.users.size}")

            var re_title: String = Get_Config.path_Yaml("Bot-info/config.yml", "Title")
            re_title = re_title.replace("{server_count}",  "${event.jda.guilds.size}").replace("{user_count}", "${event.jda.users.size}")

            val eb: EmbedBuilder = EmbedBuilder()
                .setTitle(re_title)
                .setDescription(re_description)

            event.replyEmbeds(eb.build()).queue()
        }
    }
}
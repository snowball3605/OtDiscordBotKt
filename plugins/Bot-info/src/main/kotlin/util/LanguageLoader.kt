package org.example.util

class LanguageLoader {
    companion object{
        fun getLanguag(lang_code: String, KeyWord: String, Path: String): String {
            if (Get_Config.path_here(Path + lang_code + ".yml")) {
                return Get_Config.path_Yaml(Path + lang_code + ".yml", KeyWord)
            } else {
                return Get_Config.path_Yaml(Path + "en-GB" + ".yml", KeyWord)
            }
        }
    }
}
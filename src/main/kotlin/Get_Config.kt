package org.example

import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.zip.GZIPInputStream

class Get_Config {
    companion object{
        fun path_Yaml(Path: String, KeyWord: String): String {
            val yaml = Yaml()
            val file = File(Path)
            try {
                val configMap: Map<String, Any> = yaml.load(file.inputStream()) ?: emptyMap()
                return configMap[KeyWord] as String
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}
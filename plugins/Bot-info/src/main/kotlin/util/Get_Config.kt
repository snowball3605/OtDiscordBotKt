package org.example.util

import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.zip.GZIPInputStream

class Get_Config {
    companion object{
        fun path_Yaml(Path: String, KeyWord: String): String {
            val yaml = Yaml()
            val file = File(Path)
            try {
                val configMap: Map<String, Any> = yaml.load(file.inputStream()) ?: emptyMap()
                return configMap[KeyWord].toString()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        fun path_here(Path: String): Boolean{
            val path: Path = Paths.get(Path)
            if (path.toFile().exists()){
                return true
            } else {
                return false
            }
        }
    }
}
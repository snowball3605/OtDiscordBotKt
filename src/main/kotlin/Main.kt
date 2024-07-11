package org.example

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.example.Interface.I_Plugin
import org.example.loader.Config
import org.slf4j.LoggerFactory
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.InputStream
import java.lang.reflect.Method
import java.net.URL
import java.net.URLClassLoader
import java.util.concurrent.Executors
import java.util.jar.JarFile

fun main() {
    ExtractResource.extract_resource("config.yml")
    Main(Get_Config.path_Yaml("config.yml", "token")).main()
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class Main(private val token: String?) {

    fun setJDA(jda: JDA?) {
        this.jda = jda
    }

        var jda: JDA? = null

        fun main() = try {
            ExtractResource.extract_resource("plugins/README.md")
            val main = Main(Get_Config.path_Yaml("config.yml", "token"))
            val jda = JDABuilder.createDefault(Get_Config.path_Yaml("config.yml", "token")).build()
            main.setJDA(jda)
            main.loadplugin()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        fun loadplugin() {
            val logger = LoggerFactory.getLogger(Main::class.java)
            logger.warn("loaded plugins")
            val done: Int = 0
            val fail: Int = 0
            if (File("plugins").exists()) {
                logger.warn("Plugins directory does not exist! Reloading....")
                ExtractResource.extract_resource("plugins/README.md")
            }
            for (file: File in File("plugins").listFiles()) {
                if (file.getName().endsWith(".jar") && file.getName().lastIndexOf(".") >= 1) {
                    val jarFile: JarFile = JarFile(file)
                    try {
                        val inputSteam: InputStream = jarFile.getInputStream(jarFile.getEntry("in-fo.yml"))
                        val config: Config = Yaml().loadAs(inputSteam, Config::class.java)
                        val urls = arrayOf(file.toURI().toURL())
                        URLClassLoader(urls, Thread.currentThread().contextClassLoader).use { class_Loader ->
                            val pluginsClass: Class<*> = class_Loader.loadClass(config.main)
                            if (I_Plugin::class.java.isAssignableFrom(pluginsClass)) {
                                val instance: Any? = pluginsClass.getDeclaredConstructor().newInstance()
                                val method: Method = pluginsClass.getDeclaredMethod("onEnable", JDA::class.java)
                                method.invoke(instance, jda)
                                logger.warn("Successfully loaded plugin: ${config.name}")
                            }
                        }
                    } catch (e: Exception) {
                        throw RuntimeException(e)
                    }
                }
        }
            logger.warn("Successfully loaded $done plugin(s)")
            logger.warn("Failed loaded $fail plugin(s)")
    }
}
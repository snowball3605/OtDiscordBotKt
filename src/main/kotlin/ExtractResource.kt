package org.example

import java.io.*
import java.nio.file.Files
import java.nio.file.Files.newOutputStream
import java.nio.file.Path
import java.nio.file.Paths

class ExtractResource {
    companion object {
        @Throws(IOException::class)
        fun extract_resource(resourcePath: String) {
            val resourceStream: InputStream? = Main::class.java.classLoader.getResourceAsStream(resourcePath)
            var currectDirectory: Path = Paths.get("").toAbsolutePath()
            var targetPath: Path = currectDirectory.resolve(resourcePath)

            if (!Files.exists(targetPath)) {
                Files.createDirectories(targetPath.parent)

                FileOutputStream(targetPath.toFile()).use { outputStream ->
                    var buffer = ByteArray(1024)
                    var bytesRead: Int
                    if (resourceStream != null) {
                        while (resourceStream.read(buffer).also { bytesRead = it } != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                        }
                    }
                }
            }
            resourceStream?.close()
        }
    }
}

package org.example

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main() {
    val createNewFile = CreateNewFile("./folder/file.txt")
    createNewFile.readFile()
}
class CreateNewFile(private val fileName: String) {

    fun readFile(): String {
        val dir = File(fileName.run {
            val arr = split("/")
            val localFileName = arr[arr.size - 1]
            replace(localFileName, "")
        })
        if (!(dir.exists() || dir.mkdirs()))  {
            throw NoSuchFileException(dir)
        }
        val file = File(fileName)
        if (!file.exists()) {
            if (!file.createNewFile()) throw NoSuchFileException(file)

        }
        val br = BufferedReader(FileReader(fileName))
        var text = ""
        var s: String?
        while (br.readLine().also { s = it } != null) {
            text = text + s + "\n"
        }
        br.close()
        return text
    }
}
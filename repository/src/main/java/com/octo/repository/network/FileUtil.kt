package com.octo.repository.network

import java.io.File

object FileUtil {
    fun readFile(fileName: String) = File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)
}
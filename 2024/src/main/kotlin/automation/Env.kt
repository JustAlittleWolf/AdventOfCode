package me.wolfii.automation

import java.io.FileFilter
import kotlin.io.path.Path

object Env {
    private val variables = HashMap<String, String>()

    init {
        Path("./").toFile().listFiles(FileFilter { it.name.endsWith(".env") && it.isFile() })
            ?.forEach { envFile ->
                envFile.forEachLine { line ->
                    if (line.isEmpty()) return@forEachLine
                    try {
                        val (key, value) = line.split("=").map { it.trim() }
                        if (variables.contains(key) && envFile.nameWithoutExtension == "example") return@forEachLine
                        variables[key] = value
                    } catch (t: Throwable) {
                        Solver.printErr("while reading environment file $envFile line $line", t)
                    }
                }
            }
    }

    fun get(identifier: String): String? {
        return variables[identifier]
    }
}
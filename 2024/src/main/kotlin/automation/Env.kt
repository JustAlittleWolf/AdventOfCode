package me.wolfii.automation

import kotlin.io.path.Path
import kotlin.io.path.forEachLine
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.nameWithoutExtension

object Env {
    private val variables = HashMap<String, String>()

    init {
        Path("./").listDirectoryEntries()
            .filter { it.name.endsWith(".env") && it.isRegularFile() }
            .sortedBy { it.nameWithoutExtension != "example" }
            .forEach { envFile ->
                envFile.forEachLine { line ->
                    if (line.isEmpty()) return@forEachLine
                    try {
                        val (key, value) = line.split("=").map(String::trim)
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
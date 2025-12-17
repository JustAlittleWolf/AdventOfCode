package me.wolfii.automation

import java.io.File
import kotlin.io.path.Path

object Env {
    private val variables = HashMap<String, String>()

    init {
        Path("./").toFile().listFiles { it.name.endsWith(".env") && it.isFile() }
            ?.takeIf { it.isNotEmpty() }
            ?.forEach { envFile ->
                envFile.forEachLine { line ->
                    if (line.isEmpty()) return@forEachLine
                    runCatching {
                        val (key, value) = line.split("=", limit = 2).map { it.trim() }
                        if (variables.contains(key) && envFile.nameWithoutExtension == "example") return@forEachLine
                        variables[key] = value
                    }.onFailure {
                        Solver.printErr("while reading environment file $envFile line $line", it)
                    }
                }
            } ?: run {
            File("./aoc_token.env").writeText("ADVENT_OF_CODE_SESSION_TOKEN = My-Session-Token")
            throw IllegalStateException("Please provide your advent of code token in default aoc_token.env")
        }
    }

    fun get(identifier: String): String? {
        return variables[identifier]
    }
}
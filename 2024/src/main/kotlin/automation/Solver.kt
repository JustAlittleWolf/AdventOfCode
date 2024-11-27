package me.wolfii.automation

import java.io.File
import java.net.ConnectException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import kotlin.reflect.full.primaryConstructor
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object Solver {
    private val sessionToken = Env.get("ADVENT_OF_CODE_SESSION_TOKEN")

    fun solve(solveOptions: SolveOptions) {
        val runs = ArrayList<Run>()

        if (solveOptions.input.requiresTestInput()) {
            runCatching {
                createTestFile(solveOptions)
            }.onFailure { printErr("while creating test file in run ${solveOptions.day}", it) }
            runs.add(Run("${solveOptions.day}-TEST", solveOptions.testFile()))
        }

        if (solveOptions.input.requiresRealInput()) {
            runCatching {
                downloadInput(solveOptions)
            }.onFailure { printErr("while trying to download in run ${solveOptions.day}", it) }
            runs.add(Run(solveOptions.day.toString(), solveOptions.realFile()))
        }

        val solution = runCatching { Class.forName(solveOptions.classPath()).kotlin.primaryConstructor?.call() as Solution }.getOrNull()
        if (solution == null) {
            printErr("Could not find solution under ${solveOptions.classPath()}")
            return
        }

        runs.forEach { r ->
            println("== Running ${r.name} ==")

            val lines = runCatching { r.file.readLines() }
                .onFailure {
                    printErr("while reading inptut file in run ${solveOptions.day}", it)
                    return@forEach
                }
                .getOrDefault(emptyList())
            if (lines.isEmpty()) printErr("INFO: Input was empty")

            if (solveOptions.part.runFirst()) runProblem("Part One", solution::solveFirst, lines, solveOptions)
            if (solveOptions.part.runSecond()) runProblem("Part Two", solution::solveSecond, lines, solveOptions)
            println()
        }
    }

    private fun runProblem(name: String, solution: (List<String>) -> Any?, lines: List<String>, solveOptions: SolveOptions) {
        if (!solveOptions.measure) {
            val result = solution.invoke(lines)
            if (result != Unit) println("$name: $result")
            return
        }
        val startNanos = System.nanoTime()
        val result = solution.invoke(lines)
        val diff = System.nanoTime() - startNanos
        if (result != Unit) println("$name: $result")
        println("$name took ${diff.toDuration(DurationUnit.NANOSECONDS)}")
    }

    private fun downloadInput(solveOptions: SolveOptions) {
        val file = solveOptions.realFile()
        if (file.exists()) return

        if (sessionToken == null) return
        val request = HttpRequest.newBuilder()
            .header("Cookie", "session=$sessionToken")
            .uri(URI("https://adventofcode.com/${solveOptions.day.year}/day/${solveOptions.day.day}/input"))
            .GET()
            .timeout(Duration.ofSeconds(3))
            .build()

        val response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != 200) throw ConnectException("Error while downloading day ${solveOptions.day}: Status code ${response.statusCode()}")

        file.parentFile.mkdirs()
        file.createNewFile()

        file.writeText(response.body())

        println("Downloaded input for day ${solveOptions.day}")
    }

    private fun createTestFile(solveOptions: SolveOptions) {
        val file = solveOptions.testFile()
        if (file.exists()) return
        file.parentFile.mkdirs()
        file.createNewFile()
    }

    fun printErr(info: String, throwable: Throwable? = null) {
        throwable?.let {
            System.err.println("${throwable.javaClass.simpleName}: ${throwable.message}\n\t$info")
        } ?: run {
            System.err.println(info)
        }
    }

    data class Run(val name: String, val file: File)
}
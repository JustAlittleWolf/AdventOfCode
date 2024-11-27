package me.wolfii.automation

import java.nio.file.Path
import kotlin.io.path.Path
import java.io.File

data class SolveOptions(
    val input: Input = Input.REAL,
    val part: Part = Part.BOTH,
    val implementationsPackage: String = Thread.currentThread().stackTrace.last().className.substringBeforeLast("."),
    val inputsPath: Path = Path("./inputs"),
    val day: Day = Day.CURRENT,
    val measure: Boolean = false
) {
    fun testFile(): File = inputsPath.resolve(day.year.toString()).resolve("day${day.day}.test.txt").toFile()
    fun realFile(): File = inputsPath.resolve(day.year.toString()).resolve("day${day.day}.txt").toFile()
    fun classPath(): String = "$implementationsPackage.year${day.year}.day${day.day}.Day${day.day}"
}
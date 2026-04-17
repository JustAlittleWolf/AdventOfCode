package me.wolfii.year2025.day3

import me.wolfii.automation.Solution

class Day3 : Solution {
    override fun solveFirst(lines: List<String>): Long {
        return lines.sumOf { it.joltage(cells = 2) }
    }

    override fun solveSecond(lines: List<String>): Long {
        return lines.sumOf { it.joltage(cells = 12) }
    }

    fun String.joltage(cells: Int): Long {
        var sum: Long = 0L
        var remaining: String = this
        for (i in cells downTo 1) {
            sum *= 10
            val max = remaining.dropLast(i - 1).maxBy(Char::digitToInt)
            remaining = remaining.substringAfter(max)
            sum += max.digitToInt()
        }
        return sum
    }
}
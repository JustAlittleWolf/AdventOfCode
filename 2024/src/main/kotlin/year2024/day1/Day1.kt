package me.wolfii.year2024.day1

import me.wolfii.automation.Solution
import kotlin.math.abs

class Day1 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        val leftList = lines.map { it.firstId() }.sorted()
        val rightList = lines.map { it.secondId() }.sorted()

        return leftList.indices
            .sumOf { i -> abs(leftList[i] - rightList[i]) }
    }

    override fun solveSecond(lines: List<String>): Int {
        val frequency = HashMap<Int, Int>()
        lines.forEach { frequency.compute(it.secondId()) { _, count -> (count ?: 0) + 1 } }
        return lines
            .map { it.firstId() }
            .sumOf { it * frequency.getOrDefault(it, 0) }
    }

    private fun String.firstId() = substringBefore(' ').toInt()
    private fun String.secondId() = substringAfterLast(' ').toInt()
}
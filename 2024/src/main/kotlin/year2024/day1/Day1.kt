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
        val keys = lines.map { it.firstId() }.toSet()
        return lines
            .map { it.secondId() }
            .filter { keys.contains(it) }
            .sum()
    }

    private fun String.firstId() = substringBefore(' ').toInt()
    private fun String.secondId() = substringAfterLast(' ').toInt()
}
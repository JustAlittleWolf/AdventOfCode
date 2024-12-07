package me.wolfii.year2024.day7

import me.wolfii.automation.Solution
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class Day7 : Solution {
    override fun solveFirst(lines: List<String>): Long {
        return lines.map { it.toEquation() }
            .filter { isValid(it.first, it.second) }
            .sumOf { it.first }
    }

    override fun solveSecond(lines: List<String>): Long {
        return lines.map { it.toEquation() }
            .filter { isValid(it.first, it.second, concatenation = true) }
            .sumOf { it.first }
    }

    private fun String.toEquation() = Pair(substringBefore(": ").toLong(), substringAfter(": ").split(" ").map(String::toLong))

    fun isValid(target: Long, values: List<Long>, workingValue: Long = values.first(), index: Int = 1, concatenation: Boolean = false): Boolean {
        if (index == values.size) return workingValue == target
        if (workingValue > target) return false
        return isValid(target, values, workingValue + values[index], index + 1, concatenation)
                || isValid(target, values, workingValue * values[index], index + 1, concatenation)
                || (concatenation && isValid(target, values, concatenate(workingValue, values[index]), index + 1, true))
    }

    private fun concatenate(first: Long, second: Long): Long = first * (10.0.pow(floor(log10(second.toDouble())) + 1)).toLong() + second
}
package me.wolfii.year2024.day7

import me.wolfii.automation.Solution
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class Day7 : Solution {
    override fun solveFirst(lines: List<String>): Long {
        return lines.map { it.toEquation() }
            .filter { isValid(it.first, it.second[0], it.second.withoutFirst()) }
            .sumOf { it.first }
    }

    override fun solveSecond(lines: List<String>): Long {
        return lines.map { it.toEquation() }
            .filter { isValid(it.first, it.second[0], it.second.withoutFirst(), true) }
            .sumOf { it.first }
    }

    private fun String.toEquation() = Pair(substringBefore(": ").toLong(), substringAfter(": ").split(" ").map(String::toLong))

    fun isValid(target: Long, register: Long, remaining: List<Long>, concatenation: Boolean = false): Boolean {
        if (remaining.isEmpty()) return register == target
        if (register > target) return false
        return isValid(target, register + remaining[0], remaining.withoutFirst(), concatenation)
                || isValid(target, register * remaining[0], remaining.withoutFirst(), concatenation)
                || (concatenation && isValid(target, concatenate(register, remaining[0]), remaining.withoutFirst(), true))
    }

    private fun concatenate(first: Long, second: Long): Long = first * (10.0.pow(floor(log10(second.toDouble())) + 1)).toLong() + second

    private fun List<Long>.withoutFirst() = subList(1, this.size)
}
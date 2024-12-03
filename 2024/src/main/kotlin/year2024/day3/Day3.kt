package me.wolfii.year2024.day3

import me.wolfii.automation.Solution

class Day3 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        return lines.joinToString("").sumOfMultiplications()
    }

    override fun solveSecond(lines: List<String>): Int {
        return lines.joinToString("").split("do()").map { it.split("don't()").first() }.sumOf { valid ->
            valid.sumOfMultiplications()
        }
    }

    private fun String.sumOfMultiplications() = "mul\\(\\d+,\\d+\\)".toRegex().findAll(this).sumOf { result ->
        result.value.removePrefix("mul(").removeSuffix(")").split(",").map(String::toInt).reduce { a, b -> a * b }
    }
}
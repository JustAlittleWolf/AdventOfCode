package me.wolfii.year2024.day3

import me.wolfii.automation.Solution

class Day3 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        return lines.joinToString("").sumOfMultiplications()
    }

    override fun solveSecond(lines: List<String>): Int {
        return lines.joinToString("").split("do()").map { it.split("don't()", limit = 2).first() }.sumOf { valid ->
            valid.sumOfMultiplications()
        }
    }

    private fun String.sumOfMultiplications() = """mul\(\d{1,3},\d{1,3}\)""".toRegex().findAll(this).sumOf { result ->
        result.value.removeSurrounding("mul(", ")").split(",").map(String::toInt).let { it[0] * it[1] }
    }
}
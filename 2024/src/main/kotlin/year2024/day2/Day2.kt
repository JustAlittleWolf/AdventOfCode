package me.wolfii.year2024.day2

import me.wolfii.automation.Solution
import kotlin.math.abs

class Day2 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        return lines.count { it.toValues().isValid() }
    }

    override fun solveSecond(lines: List<String>): Int {
        return lines.count {
            it.toValues().let { values ->
                values.isValid() || values.indices.any { i -> values.withoutIndex(i).isValid() }
            }
        }
    }

    private fun List<Int>.isValid(): Boolean {
        val validSteps = windowed(2).count { window -> abs(window[1] - window[0]).let { it >= 1 && it <= 3 } }
        if (validSteps != size - 1) return false
        val increasingSteps = windowed(2).count { window -> window[1] > window[0] }
        return increasingSteps == size - 1 || increasingSteps == 0
    }

    private fun List<Int>.withoutIndex(index: Int) = filterIndexed { i, _ -> i != index }
    private fun String.toValues() = split(" ").map(String::toInt)
}
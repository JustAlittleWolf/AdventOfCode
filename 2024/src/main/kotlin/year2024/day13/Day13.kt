package me.wolfii.year2024.day13

import me.wolfii.automation.Solution
import me.wolfii.common.Vec2L
import me.wolfii.common.leastCommonMultipleL

class Day13 : Solution {
    override fun solveFirst(lines: List<String>): Long {
        return lines.windowed(3, 4).sumOf { window ->
            val (buttonA, buttonB, prize) = window.map { it.toVec2L() }
            leastTokens(buttonA, buttonB, prize)
        }
    }

    override fun solveSecond(lines: List<String>): Long {
        return lines.windowed(3, 4).sumOf { window ->
            val (buttonA, buttonB, prize) = window.map { it.toVec2L() }
            leastTokens(buttonA, buttonB, prize + Vec2L(10000000000000, 10000000000000))
        }
    }

    private fun String.toVec2L() = this.substringAfterLast(": ").split(", ", limit = 2).map { it.substringAfter("+").substringAfter("=").toLong() }.let { Vec2L(it[0], it[1]) }

    /**
     * The equation being solved is:
     *
     * I:  target.x = aButton.x * A + bButton.x * B
     * II: target.y = aButton.y * A + bButton.y * B
     */
    private fun leastTokens(aButton: Vec2L, bButton: Vec2L, target: Vec2L): Long {
        val lcm = leastCommonMultipleL(aButton.x, aButton.y)
        val eq1Multiplier = lcm / aButton.x
        val eq1Target = target.x * eq1Multiplier
        val eq1A = aButton.x * eq1Multiplier
        val eq1B = bButton.x * eq1Multiplier

        val eq2Multiplier = lcm / aButton.y

        val solutionTarget = eq1Target - target.y * eq2Multiplier
        val solutionA = eq1A - aButton.y * eq2Multiplier
        val solutionB = eq1B - bButton.y * eq2Multiplier

        if (solutionA != 0L) throw IllegalStateException()

        val bPresses = solutionTarget / solutionB
        val aPresses = (target.x - bButton.x * bPresses) / aButton.x

        return if (aButton * aPresses + bButton * bPresses == target) aPresses * 3L + bPresses
        else 0L
    }
}

package me.wolfii.year2025.day1

import me.wolfii.automation.Solution
import kotlin.math.absoluteValue

class Day1 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        var zeroVisitedCount = 0
        lines.forEachRotation { newPosition, _, _ ->
            if (newPosition == 0) zeroVisitedCount++
        }
        return zeroVisitedCount
    }

    override fun solveSecond(lines: List<String>): Int {
        var zeroPassedCount = 0
        lines.forEachRotation { newPosition, oldPosition, newPositionOverflown ->
            if (newPositionOverflown !in 0..99) {
                if (newPositionOverflown > 0) zeroPassedCount += newPositionOverflown / 100
                else {
                    zeroPassedCount += 1 + (newPositionOverflown.absoluteValue / 100)
                    if (oldPosition == 0) zeroPassedCount--
                }
            } else if (newPosition == 0) zeroPassedCount++
        }
        return zeroPassedCount
    }

    private fun List<String>.forEachRotation(consumer: (newPosition: Int, oldPosition: Int, newPositionOverflown: Int) -> Unit) {
        this.fold(50) { position, rotation ->
            val sign = if (rotation[0] == 'R') 1 else -1
            val magnitude = rotation.drop(1).toInt()
            val newPositionOverflown = position + magnitude * sign
            return@fold Math.floorMod(newPositionOverflown, 100).also { newPosition ->
                consumer(newPosition, position, newPositionOverflown)
            }
        }
    }
}
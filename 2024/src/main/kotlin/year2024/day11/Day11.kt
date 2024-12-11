package me.wolfii.year2024.day11

import me.wolfii.automation.Solution
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

class Day11 : Solution {
    private val cache: MutableMap<Pair<Int, Long>, Long> = HashMap()

    override fun solveFirst(lines: List<String>): Long {
        cache.clear()
        return lines.first().split(" ").map { it.toLong() }.sumOf { blink(25, it) }
    }

    override fun solveSecond(lines: List<String>): Long {
        cache.clear()
        return lines.first().split(" ").map { it.toLong() }.sumOf { blink(75, it) }
    }

    private fun blink(count: Int, stone: Long): Long {
        val key = Pair(count, stone)
        return cache[key] ?: run {
            val digits = floor(log10(stone.toDouble()) + 1).toLong()
            val stones = when {
                count <= 0 -> 1L
                stone == 0L -> blink(count - 1, 1)
                digits % 2 == 0L -> {
                    val cutOff = (10.0.pow((digits / 2).toDouble())).toLong()
                    blink(count - 1, stone / cutOff) + blink(count - 1, stone % cutOff)
                }

                else -> blink(count - 1, stone * 2024)
            }
            cache[key] = stones
            stones
        }
    }
}
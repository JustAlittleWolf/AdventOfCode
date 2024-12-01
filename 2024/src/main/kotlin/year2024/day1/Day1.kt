package me.wolfii.year2024.day1

import me.wolfii.automation.Solution
import java.util.*
import kotlin.math.abs

class Day1 : Solution {
    override fun solveFirst(lines: List<String>): Any? {
        val leftList = PriorityQueue<Int>()
        val rightList = PriorityQueue<Int>()

        lines.forEach { line ->
            leftList.add(line.firstId())
            rightList.add(line.secondId())
        }

        var sum = 0
        while (leftList.isNotEmpty()) {
            sum += abs(leftList.poll() - rightList.poll())
        }
        return sum
    }

    override fun solveSecond(lines: List<String>): Any? {
        val keys = HashSet<Int>()
        lines.forEach { line -> keys.add(line.firstId()) }
        return lines.sumOf { line ->
            val value = line.secondId()
            if (keys.contains(value)) return@sumOf value else 0
        }
    }

    private fun String.firstId() = substringBefore(' ').toInt()
    private fun String.secondId() = substringAfterLast(' ').toInt()
}
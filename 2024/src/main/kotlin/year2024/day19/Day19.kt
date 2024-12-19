package me.wolfii.year2024.day19

import me.wolfii.automation.Solution

class Day19 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        val towels = lines.towels()
        return lines.patterns().count { it.countPossible(towels) != 0L }
    }

    override fun solveSecond(lines: List<String>): Long {
        val towels = lines.towels()
        return lines.patterns().sumOf { it.countPossible(towels) }
    }

    private fun List<String>.towels() = this.first().split(", ").map { it.toCharArray() }
    private fun List<String>.patterns(): List<CharArray> = this.takeLastWhile { it.isNotBlank() }.map { it.toCharArray() }

    private fun CharArray.countPossible(towels: List<CharArray>, offset: Int = 0, cache: Array<Long?> = Array(this.size) { null }): Long {
        if (this.size == offset) return 1L
        return cache[offset]
            ?: towels.sumOf { towel ->
                if (!this.startsWith(towel, offset)) return@sumOf 0L
                return@sumOf this.countPossible(towels, offset + towel.size, cache)
            }.also { cache[offset] = it }
    }

    private fun CharArray.startsWith(towel: CharArray, offset: Int): Boolean {
        if (offset + towel.size > this.size) return false
        for (i in 0..<towel.size) {
            if (this[offset + i] != towel[i]) return false
        }
        return true
    }
}

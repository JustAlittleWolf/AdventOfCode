package me.wolfii.year2024.day10

import me.wolfii.automation.Solution
import me.wolfii.common.*

class Day10 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        val field = lines.toIntField()
        return field.hikeStartPositions()
            .sumOf { start ->
                start.explore(field)
                    .filter { (pos, _) -> field[pos] == 9 }
                    .values.sum()
            }
    }

    override fun solveSecond(lines: List<String>): Int {
        val field = lines.toIntField()
        return field.hikeStartPositions()
            .sumOf { start ->
                start.explore(field)
                    .keys.count { field[it] == 9 }
            }
    }

    private fun List<String>.toIntField() = this.toCharField().map { it.digitToIntOrNull() ?: -1 }

    private fun Field<Int>.hikeStartPositions() = this.indices.filter { this[it] == 0 }

    private fun Vec2I.explore(field: Field<Int>): Map<Vec2I, Int> {
        val visitedCount = HashMap<Vec2I, Int>()
        val visitNext = ArrayDeque<Vec2I>()
        visitNext.add(this)
        while (visitNext.isNotEmpty()) {
            val current = visitNext.removeFirst()
            visitedCount.compute(current) { _, v -> (v ?: 0) + 1 }
            for (neighbourOffset in Direction.entries) {
                val neighbour = field[current + neighbourOffset.vec] ?: continue
                if (neighbour != field.getValue(current) + 1) continue
                visitNext.add(current + neighbourOffset.vec)
            }
        }
        return visitedCount
    }
}
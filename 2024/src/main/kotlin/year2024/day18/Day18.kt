package me.wolfii.year2024.day18

import me.wolfii.automation.Solution
import me.wolfii.common.*
import kotlin.math.min

class Day18 : Solution {
    override fun solveFirst(lines: List<String>): Any {
        return getMinPathAfterNBytesHaveFallen(1024, lines.bytes()) ?: "No Path found"
    }

    override fun solveSecond(lines: List<String>): Any {
        val bytes = lines.bytes()
        return (1 until lines.size)
            .firstOrNull { n -> getMinPathAfterNBytesHaveFallen(n, bytes) == null }
            ?.let { n -> "${bytes[n - 1].x},${bytes[n - 1].y}" }
            ?: "Path is never cut off"
    }

    private fun List<String>.bytes() = this.map { it.split(",").let { Vec2I(it.first().toInt(), it.last().toInt()) } }
    private fun <T> List<Vec2I>.field(initial: () -> T): MutableField<T> = mutableFieldWithInitial(Vec2I(this.maxOf { it.x } + 1, this.maxOf { it.y } + 1)) { initial() }

    private fun getMinPathAfterNBytesHaveFallen(n: Int, bytes: List<Vec2I>): Int? {
        val obstructions = bytes.field { false }
        bytes.subList(0, min(n, bytes.size)).forEach { byte -> obstructions[byte] = true }
        return getMinPath(Vec2I(0, 0), Vec2I(obstructions.width - 1, obstructions.height - 1), obstructions)
    }

    private fun getMinPath(start: Vec2I, end: Vec2I, obstructions: Field<Boolean>): Int? {
        val visited = HashSet<Vec2I>()
        val distanceField = mutableFieldWithInitial(Vec2I(obstructions.width, obstructions.height)) { Int.MAX_VALUE }
        distanceField[start] = 0
        val toExplore = ArrayDeque<Vec2I>(listOf(start))
        while (toExplore.isNotEmpty()) {
            val pos = toExplore.removeFirst()
            val distance = distanceField.getValue(pos)
            if (pos == end) return distance
            if (visited.contains(pos)) continue
            visited.add(pos)
            for (direction in Direction.entries) {
                val neighbour = pos + direction.vec
                if (neighbour in visited) continue
                if (obstructions[neighbour] != false) continue
                if (distanceField.getValue(neighbour) <= distance + 1) continue
                distanceField[neighbour] = distance + 1
                toExplore.add(neighbour)
            }
        }
        return null
    }
}

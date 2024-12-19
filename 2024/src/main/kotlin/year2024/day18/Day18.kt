package me.wolfii.year2024.day18

import me.wolfii.automation.Solution
import me.wolfii.common.*
import java.util.PriorityQueue
import kotlin.math.min

class Day18 : Solution {
    override fun solveFirst(lines: List<String>): Any {
        val bytes = lines.bytes().take(1024).toHashSet()
        val obstructions = mutableFieldWithInitial(Vec2I(bytes.maxOf { it.x } + 1, bytes.maxOf { it.y } + 1)) { index -> index in bytes }
        return shortestPathLength(Vec2I(0, 0), Vec2I(obstructions.width - 1, obstructions.height - 1), obstructions) ?: "No Path found"
    }

    override fun solveSecond(lines: List<String>): Any {
        val bytes = lines.bytes()
        val obstructedIn = fieldWithInitial(Vec2I(bytes.maxOf { it.x } + 1, bytes.maxOf { it.y } + 1)) { pos ->
            val index = bytes.indexOf(pos)
            if (index == -1) Int.MAX_VALUE else index
        }
        val blockingByte = bytes[lastReachableTimePoint(Vec2I(0, 0), Vec2I(obstructedIn.width - 1, obstructedIn.height - 1), obstructedIn)]
        return "${blockingByte.x},${blockingByte.y}"
    }

    private fun List<String>.bytes() = this.map { it.split(",").let { Vec2I(it.first().toInt(), it.last().toInt()) } }

    private fun shortestPathLength(start: Vec2I, end: Vec2I, obstructions: Field<Boolean>): Int? {
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

    private fun lastReachableTimePoint(start: Vec2I, end: Vec2I, obstructedIn: Field<Int>): Int {
        val reachableInTimeField = mutableFieldWithInitial(Vec2I(obstructedIn.width, obstructedIn.height)) { 0 }
        reachableInTimeField[start] = obstructedIn.getValue(start)
        val toExplore = PriorityQueue<Vec2I> {a, b -> reachableInTimeField.getValue(b) - reachableInTimeField.getValue(a)}
        toExplore.add(start)
        while (toExplore.isNotEmpty()) {
            val pos = toExplore.remove()
            val posReachableIn = reachableInTimeField.getValue(pos)
            for (direction in Direction.entries) {
                val neighbour = pos + direction.vec
                val byteFallsOnNeighbourIn = obstructedIn[neighbour] ?: continue
                val neighbourReachableIn = min(byteFallsOnNeighbourIn, posReachableIn)
                if (reachableInTimeField.getValue(neighbour) >= neighbourReachableIn) continue
                reachableInTimeField[neighbour] = neighbourReachableIn
                toExplore.add(neighbour)
            }
        }
        return reachableInTimeField.getValue(end)
    }
}

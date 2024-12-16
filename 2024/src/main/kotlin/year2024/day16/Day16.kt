package me.wolfii.year2024.day16

import me.wolfii.automation.Solution
import me.wolfii.common.Direction
import me.wolfii.common.MutableField
import me.wolfii.common.Field
import me.wolfii.common.Vec2I
import me.wolfii.common.map
import me.wolfii.common.toMutableCharField
import me.wolfii.common.toMutableField
import java.util.EnumMap

class Day16 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        val map = lines.toMutableCharField()
        val end = map.locateEnd()
        return map
            .explore(map.locateStart(), end)
            .minScoreAt(end)
    }

    override fun solveSecond(lines: List<String>): Int {
        val map = lines.toMutableCharField()
        val end = map.locateEnd()
        return map
            .explore(map.locateStart(), end)
            .bestTiles(end).size
    }

    private fun MutableField<Char>.locateStart(): Vec2I = this.indices.first { this[it] == 'S' }.also { this[it] = '.' }
    private fun MutableField<Char>.locateEnd(): Vec2I = this.indices.first { this[it] == 'E' }.also { this[it] = '.' }

    private fun Field<Char>.explore(start: Vec2I, target: Vec2I): EnumMap<Direction, MutableField<Distance>> {
        val distances = EnumMap<Direction, MutableField<Distance>>(Direction::class.java)
        Direction.entries.forEach { direction ->
            distances.put(direction, this.map { Distance(Int.MAX_VALUE, Int.MAX_VALUE) }.toMutableField())
        }
        val startDirection = Direction.EAST
        distances.getValue(startDirection)[start] = Distance(0, 0)
        val toExplore = ArrayDeque<Location>(listOf(Location(start, startDirection)))
        var maxValidScore = Int.MAX_VALUE
        while (toExplore.isNotEmpty()) {
            val location = toExplore.removeFirst()
            val distance = distances.getValue(location.direction).getValue(location.pos)
            if (location.pos == target) {
                maxValidScore = maxValidScore.coerceAtMost(distance.score)
                continue
            }
            for (nextDirection in listOf(location.direction.turnLeft(), location.direction.turnRight())) {
                val nextLocation = Location(location.pos, nextDirection)
                val nextDistance = Distance(distance.steps, distance.turns + 1)
                if (distances.computeImprovement(nextLocation, nextDistance, maxValidScore)) toExplore.add(nextLocation)
            }
            val nextLocation = Location(location.pos + location.direction.vec, location.direction)
            if (this[nextLocation.pos] != '.') continue
            val nextDistance = Distance(distance.steps + 1, distance.turns)
            if (distances.computeImprovement(nextLocation, nextDistance, maxValidScore)) toExplore.add(nextLocation)
        }
        return distances
    }

    /**
     * @return Whether an improvement was made
     */
    private fun EnumMap<Direction, MutableField<Distance>>.computeImprovement(location: Location, distance: Distance, maxValidScore: Int = Int.MAX_VALUE): Boolean {
        if (distance.score > maxValidScore) return false
        if (this.getValue(location.direction).getValue(location.pos).score <= distance.score) return false
        this.getValue(location.direction)[location.pos] = distance
        return true
    }

    private fun EnumMap<Direction, MutableField<Distance>>.minScoreAt(pos: Vec2I) = this.minOf { it.value.getValue(pos).score }

    private fun EnumMap<Direction, MutableField<Distance>>.bestTiles(end: Vec2I): Set<Vec2I> {
        val bestTiles = HashSet<Vec2I>()
        var toExplore = ArrayDeque<Location>(
            Direction.entries
                .filter { this.getValue(it).getValue(end).score == this.minScoreAt(end) }
                .map { Location(end, it) }
        )
        while (toExplore.isNotEmpty()) {
            val location = toExplore.removeFirst()
            bestTiles.add(location.pos)
            val distance = this.getValue(location.direction).getValue(location.pos)
            for (direction in Direction.entries) {
                if (this.getValue(direction).getValue(location.pos).score >= distance.score) continue
                toExplore.add(Location(location.pos, direction))
            }
            if (this.getValue(location.direction).getValue(location.pos - location.direction.vec).score >= distance.score) continue
            toExplore.add(Location(location.pos - location.direction.vec, location.direction))
        }
        return bestTiles
    }

    private data class Distance(val steps: Int, val turns: Int, val score: Int = steps + turns * 1000)
    private data class Location(val pos: Vec2I, val direction: Direction)
}

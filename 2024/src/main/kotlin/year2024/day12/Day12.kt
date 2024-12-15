package me.wolfii.year2024.day12

import me.wolfii.automation.Solution
import me.wolfii.common.Direction
import me.wolfii.common.Field
import me.wolfii.common.Vec2I
import me.wolfii.common.toCharField

class Day12 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        return lines.toCharField().regions().sumOf { it.area() * it.perimeter() }
    }

    override fun solveSecond(lines: List<String>): Int {
        return lines.toCharField().regions().sumOf { it.area() * it.sides() }
    }

    private fun Field<Char>.regions(): List<Region> {
        val taken = HashSet<Vec2I>()
        return this.indices.mapNotNull { start ->
            if (taken.contains(start)) return@mapNotNull null
            taken.add(start)
            val plant = this.getValue(start)
            val currentRegion = HashSet<Vec2I>(listOf(start))
            val toExplore = ArrayDeque<Vec2I>(listOf(start))
            while (toExplore.isNotEmpty()) {
                val current = toExplore.removeFirst()
                for (direction in Direction.entries) {
                    val neighbour = current + direction.vec
                    if (this[neighbour] != plant) continue
                    if (currentRegion.contains(neighbour)) continue
                    taken.add(neighbour)
                    currentRegion.add(neighbour)
                    toExplore.add(neighbour)
                }
            }
            return@mapNotNull Region(currentRegion)
        }
    }

    private class Region(private val tiles: Set<Vec2I>) {
        fun area() = tiles.size
        fun perimeter() = tiles.sumOf { tile ->
            Direction.entries.count { direction ->
                !tiles.contains(tile + direction.vec)
            }
        }

        fun sides(): Int {
            val allSides = tiles.flatMap { tile ->
                Direction.entries.filter { direction -> !tiles.contains(tile + direction.vec) }
                    .map { direction -> Side(tile, direction) }
            }
            val candidates = HashSet(allSides)
            var uniqueSideCount = 0
            while (candidates.isNotEmpty()) {
                val currentSide = candidates.random()
                candidates.remove(currentSide)
                uniqueSideCount++
                listOf(currentSide.direction.turnRight().vec, currentSide.direction.turnLeft().vec).forEach { normal ->
                    var offset = 0
                    var unusedSide = currentSide
                    do {
                        offset++
                        unusedSide = Side(currentSide.pos + normal * offset, currentSide.direction)
                        candidates.remove(unusedSide)
                    } while (allSides.contains(unusedSide))
                }
            }
            return uniqueSideCount
        }
    }

    private data class Side(val pos: Vec2I, val direction: Direction)
}
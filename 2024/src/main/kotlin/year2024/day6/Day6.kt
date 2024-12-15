package me.wolfii.year2024.day6

import me.wolfii.automation.Solution
import me.wolfii.common.*
import java.util.*

class Day6 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        return lines.findGuard().explore(lines.toCharField()).visited().size
    }

    override fun solveSecond(lines: List<String>): Int {
        val field = lines.toMutableCharField()
        val guard = lines.findGuard()
        return guard.explore(field).visited()
            .count { pos ->
                if (pos == guard) return@count false
                field[pos] = '#'
                val result = guard.explore(field).isLoop
                field[pos] = '.'
                return@count result
            }
    }

    private fun List<String>.findGuard() = this.indexOfFirst { it.contains('^') }.let { y -> Vec2I(this[y].indexOf('^'), y) }

    private fun Vec2I.explore(field: Field<Char>): Trip {
        var position = this
        var direction = Direction.NORTH
        val visited = EnumMap<Direction, MutableSet<Vec2I>>(Direction.entries.associateWith { HashSet() })

        while (position inside field) {
            if (visited.getValue(direction).contains(position)) return Trip(visited, true)
            visited.getValue(direction).add(position)

            while (field[position + direction.vec] == '#') {
                direction = direction.turnRight()
            }
            position += direction.vec
        }
        return Trip(visited, false)
    }

    private class Trip(private val visited: Map<*, Set<Vec2I>>, val isLoop: Boolean) {
        fun visited() = visited.flatMap { it.value }.toSet()
    }
}

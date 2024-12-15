package me.wolfii.year2024.day15

import me.wolfii.automation.Solution
import me.wolfii.common.*

class Day15 : Solution {
    override fun solveFirst(lines: List<String>): Long {
        val warehouse = lines.warehouse()
        var robot = warehouse.locateRobot()
        for (move in lines.moves()) {
            robot = moveRobot(robot, warehouse, move)
        }
        return warehouse.sumOfGpsCoordinates()
    }

    override fun solveSecond(lines: List<String>): Long {
        val warehouse = lines.widen().warehouse()
        var robot = warehouse.locateRobot()
        for (move in lines.moves()) {
            robot = moveRobot(robot, warehouse, move)
        }
        return warehouse.sumOfGpsCoordinates()
    }

    private fun MutableField<Char>.locateRobot() = this.indices.first { this[it] == '@' }.also { this[it] = '.' }
    private fun List<String>.warehouse() = this.takeWhile { !it.isBlank() }.toMutableCharField()
    private fun List<String>.moves() = this.takeLastWhile { !it.isBlank() }.joinToString("").map {
        when (it) {
            '<' -> Direction.WEST
            '^' -> Direction.NORTH
            '>' -> Direction.EAST
            'v' -> Direction.SOUTH
            else -> throw IllegalArgumentException()
        }
    }

    private fun List<String>.widen() = this.map { line ->
        line.fold(StringBuilder()) { sb, ch ->
            when (ch) {
                '@' -> sb.append("@.")
                'O' -> sb.append("[]")
                else -> sb.append(ch).append(ch)
            }
        }.toString()
    }

    private fun moveRobot(robot: Vec2I, map: MutableField<Char>, direction: Direction): Vec2I {
        val toExplore = ArrayDeque<Vec2I>(listOf(robot + direction.vec))
        val toMove = LinkedHashSet<Vec2I>()
        while (toExplore.isNotEmpty()) {
            val pos = toExplore.removeFirst()
            if (toMove.contains(pos)) continue
            when (map[pos]) {
                '[', ']', 'O' -> {
                    toExplore.add(pos + direction.vec)
                    toMove.add(pos)
                    if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                        when (map[pos]) {
                            '[' -> toExplore.add(pos + Vec2I(1, 0))
                            ']' -> toExplore.add(pos + Vec2I(-1, 0))
                        }
                    }
                }

                '#' -> return robot
                else -> continue
            }
        }
        for (pos in toMove.reversed()) {
            map[pos + direction.vec] = map.getValue(pos)
            map[pos] = '.'
        }
        return robot + direction.vec
    }

    private fun Field<Char>.sumOfGpsCoordinates(): Long = this.sumOfIndexed { pos, ch -> if (ch == 'O' || ch == '[') 100L * pos.y + pos.x else 0L }
}
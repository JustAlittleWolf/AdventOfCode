package me.wolfii.year2024.day8

import me.wolfii.automation.Solution
import me.wolfii.common.*

class Day8 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        return lines.toCharField().countAntinodes { pos, firstAntenna, secondAntenna ->
            (firstAntenna + (firstAntenna - secondAntenna)) == pos
        }
    }

    override fun solveSecond(lines: List<String>): Int {
        return lines.toCharField().countAntinodes { pos, firstAntenna, secondAntenna ->
            if (pos == firstAntenna) return@countAntinodes true
            val diffToFirst = pos - firstAntenna
            val diffToSecond = pos - secondAntenna
            if (diffToFirst.y == 0) return@countAntinodes diffToSecond.y == 0
            if (diffToSecond.y == 0) return@countAntinodes false
            val xSlope = diffToFirst.x.toDouble() / diffToFirst.y
            val ySlope = diffToSecond.x.toDouble() / diffToSecond.y
            return@countAntinodes xSlope == ySlope
        }
    }

    private inline fun Field<Char>.countAntinodes(predicate: (Vec2I, Antenna, Antenna) -> Boolean): Int {
        val antennaPairs = this.antennaGroups().values.flatMap { it.cartesianUnique() }
        return this.indices.count { pos ->
            antennaPairs.any { (firstAntenna, secondAntenna) -> predicate(pos, firstAntenna, secondAntenna) }
        }
    }

    private fun Field<Char>.antennaGroups(): Map<Char, List<Antenna>> = HashMap<Char, MutableList<Antenna>>().also { map ->
        this.forEachIndexed { pos, ch -> if (ch != '.') map.getOrPut(ch) { ArrayList<Antenna>() }.add(pos) }
    }
}

private typealias Antenna = Vec2I
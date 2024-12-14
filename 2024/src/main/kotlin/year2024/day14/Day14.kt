package me.wolfii.year2024.day14

import me.wolfii.automation.Solution
import me.wolfii.common.Vec2I
import kotlin.math.abs
import kotlin.math.max

class Day14 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        val robots = lines.map { it.toRobot() }
        val bounds = robots.bounds()
        val steppedRobots = robots.step(100, bounds)
        val centerX = (bounds.x - 1) / 2
        val centerY = (bounds.y - 1) / 2
        return steppedRobots.countInRange(0..<centerX, 0..<centerY) *
                steppedRobots.countInRange((centerX + 1)..bounds.x, 0..<centerY) *
                steppedRobots.countInRange(0..<centerX, (centerY + 1)..<bounds.y) *
                steppedRobots.countInRange((centerX + 1)..bounds.x, (centerY + 1)..<bounds.y)
    }

    override fun solveSecond(lines: List<String>): Any {
        val robots = lines.map { it.toRobot() }
        val bounds = robots.bounds()
        val maxSteps = 10_000
        val christmasTreeThreshold = robots.size / 3
        val distanceThreshold = max(bounds.x, bounds.y) / 5
        for (step in 0..<maxSteps) {
            val steppedRobots = robots.step(step, bounds)
            val massCenter = Vec2I(
                steppedRobots.sumOf { it.x } / robots.size,
                steppedRobots.sumOf { it.y } / robots.size
            )
            val closeRobots = steppedRobots.count { abs(it.x - massCenter.x) + abs(it.y - massCenter.y) < distanceThreshold }
            if (closeRobots > christmasTreeThreshold) return step
        }
        return "No Christmas tree found"
    }

    private fun List<Robot>.bounds() = Vec2I(this.maxOf { it.position.x } + 1, this.maxOf { it.position.y } + 1)

    private fun List<Vec2I>.countInRange(xRange: IntRange, yRange: IntRange) = this.count { it.x in xRange && it.y in yRange }

    private fun String.toRobot() = Robot(
        substringBefore(' ').substringAfter("p=").split(",").let { Vec2I(it[0].toInt(), it[1].toInt()) },
        substringAfter("v=").split(",").let { Vec2I(it[0].toInt(), it[1].toInt()) }
    )

    private fun List<Robot>.step(steps: Int, bounds: Vec2I) = this.map { robot ->
        val newPositionRaw = robot.position + robot.velocity * steps
        Vec2I(Math.floorMod(newPositionRaw.x, bounds.x), Math.floorMod(newPositionRaw.y, bounds.y))
    }

    data class Robot(val position: Vec2I, val velocity: Vec2I)
}
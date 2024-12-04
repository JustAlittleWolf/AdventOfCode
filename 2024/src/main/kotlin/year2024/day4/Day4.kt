package me.wolfii.year2024.day4

import me.wolfii.automation.Solution
import me.wolfii.common.Vec2I

class Day4 : Solution {
    private val diagonalsPositiveX = listOf(Vec2I(1, 1), Vec2I(1, -1))
    private val directions = listOf(Vec2I(1, 0), Vec2I(-1, 0), Vec2I(0, 1), Vec2I(0, -1), Vec2I(1, 1), Vec2I(1, -1), Vec2I(-1, 1), Vec2I(-1, -1))
    private val lettersXMAS = "XMAS".toCharArray()
    private val indexedLettersXmas = lettersXMAS.zip(lettersXMAS.indices)
    private val lettersMAS = "MAS".toCharArray()

    override fun solveFirst(lines: List<String>): Any? {
        return lines.toField().let { field ->
            field.positions()
                .filter { pos -> field.at(pos) == lettersXMAS.first() }
                .sumOf { pos -> field.countXmasFrom(pos) }
        }
    }

    override fun solveSecond(lines: List<String>): Any? {
        return lines.toField().let { field ->
            field.positions()
                .filter { pos -> field.at(pos) == lettersMAS[1] }
                .count { pos -> field.isMasXAt(pos) }
        }
    }

    private fun List<String>.toField() = map { it.toCharArray().filter { !it.isWhitespace() } }
    private fun List<List<Char>>.positions() = indices.flatMap { y -> this[y].indices.map { x -> Vec2I(x, y) } }
    private fun List<List<Char>>.at(pos: Vec2I) = this.getOrNull(pos.y)?.getOrNull(pos.x)

    private fun List<List<Char>>.countXmasFrom(pos: Vec2I) = directions.count { direction ->
        indexedLettersXmas.all { (ch, index) -> this.at(pos + direction * index) == ch }
    }

    private fun List<List<Char>>.isMasXAt(pos: Vec2I) = diagonalsPositiveX.all { direction ->
        val diagonalLetters = lettersMAS.toMutableSet()
        diagonalLetters.remove(lettersMAS[1])
        diagonalLetters.remove(this.at(pos + direction))
        diagonalLetters.remove(this.at(pos - direction))
        return@all diagonalLetters.isEmpty()
    }
}
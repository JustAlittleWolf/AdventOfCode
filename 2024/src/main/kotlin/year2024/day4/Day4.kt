package me.wolfii.year2024.day4

import me.wolfii.automation.Solution
import me.wolfii.common.Field
import me.wolfii.common.Vec2I
import me.wolfii.common.toCharField

class Day4 : Solution {
    private val diagonalsPositiveX = listOf(Vec2I(1, 1), Vec2I(1, -1))
    private val directions = listOf(Vec2I(1, 0), Vec2I(-1, 0), Vec2I(0, 1), Vec2I(0, -1), Vec2I(1, 1), Vec2I(1, -1), Vec2I(-1, 1), Vec2I(-1, -1))
    private val lettersXMAS = "XMAS".toCharArray()
    private val indexedLettersXmas = lettersXMAS.zip(lettersXMAS.indices)
    private val lettersMAS = "MAS".toCharArray()

    override fun solveFirst(lines: List<String>): Any? {
        return lines.toCharField().let { field ->
            field.indices
                .filter { pos -> field[pos] == lettersXMAS.first() }
                .sumOf { pos -> field.countXmasFrom(pos) }
        }
    }

    override fun solveSecond(lines: List<String>): Any? {
        return lines.toCharField().let { field ->
            field.indices
                .filter { pos -> field[pos] == lettersMAS[1] }
                .count { pos -> field.isMasXAt(pos) }
        }
    }

    private fun Field<Char>.countXmasFrom(pos: Vec2I) = directions.count { direction ->
        indexedLettersXmas.all { (ch, index) -> this[pos + direction * index] == ch }
    }

    private fun Field<Char>.isMasXAt(pos: Vec2I) = diagonalsPositiveX.all { direction ->
        val diagonalLetters = lettersMAS.toMutableSet()
        diagonalLetters.remove(lettersMAS[1])
        diagonalLetters.remove(this[pos + direction])
        diagonalLetters.remove(this[pos - direction])
        return@all diagonalLetters.isEmpty()
    }
}
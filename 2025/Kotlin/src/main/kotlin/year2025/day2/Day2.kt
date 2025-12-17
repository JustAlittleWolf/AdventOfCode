package me.wolfii.year2025.day2

import me.wolfii.automation.Solution

class Day2 : Solution {
    override fun solveFirst(lines: List<String>): Long {
        return lines.ranges().flatMap(Range::invalidIds).distinct().filter {
            val stringified = it.toString()
            if (stringified.length % 2 != 0) return@filter false
            return@filter stringified.take(stringified.length / 2) == stringified.drop(stringified.length / 2)
        }.sum()
    }

    override fun solveSecond(lines: List<String>): Long {
        return lines.ranges().flatMap(Range::invalidIds).distinct().sum()
    }

    private fun List<String>.ranges() = this
        .first()
        .split(',')
        .map {
            val (first, last) = it.split('-').map(String::toLong)
            return@map Range(first, last)
        }

    private data class Range(val first: Long, val last: Long) {
        fun invalidIds(): List<Long> =
            buildList {
                val number = last.toString().map(Char::digitToInt)
                for (i in 0..<number.size) {
                    addAll(invalidIdsRecursive(number = number.drop(i).toIntArray(), digit = 0))
                }
            }

        private fun invalidIdsRecursive(number: IntArray, digit: Int): List<Long> =
            buildList {
                if (digit + 1 >= number.size) return@buildList
                for (i in 0..9) {
                    if (digit == 0 && i == 0) continue
                    number[digit] = i
                    if (number.size % (digit + 1) == 0) {
                        for (n in (digit + 1)..<number.size) {
                            number[n] = number[n % (digit + 1)]
                        }
                        if (number.digitsToLong() in first..last) {
                            add(number.digitsToLong())
                        }
                    }
                    if (digit == number.size - 1) continue
                    number.fill(0, digit + 1, number.size)
                    if (number.digitsToLong() > last) continue
                    number.fill(9, digit + 1, number.size)
                    if (number.digitsToLong() < first) continue
                    addAll(invalidIdsRecursive(number, digit + 1))
                }
            }

        private fun IntArray.digitsToLong(base: Int = 10): Long {
            var sum = 0L
            for (digit in this) {
                sum = sum * base + digit
            }
            return sum
        }
    }
}
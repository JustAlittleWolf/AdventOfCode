package me.wolfii.year2024.day9

import me.wolfii.automation.Solution

class Day9 : Solution {
    companion object {
        private const val EMPTY_ID = -1
    }

    override fun solveFirst(lines: List<String>): Long {
        return lines.first().segments().also { it.compact() }.checksum()
    }

    override fun solveSecond(lines: List<String>): Long {
        return lines.first().segments().also { it.compactWithoutFragmentation() }.checksum()
    }

    // Currently very slow
    private fun MutableList<Segment>.compactWithoutFragmentation() {
        // IDEA: store the head for each of the 9 segment sizes?
        var head = 0
        var tail = this.size - 1
        while (head < tail) {
            while (head < tail && this[head].id != EMPTY_ID) head++
            while (head < tail && this[tail].id == EMPTY_ID) tail--
            if(head >= tail) return
            for (i in head..<tail) {
                if (this[i].id != EMPTY_ID) continue
                if (this[i].size < this[tail].size) continue
                val remainingSpace = this[i].size - this[tail].size
                this[i] = this[tail]
                this[tail] = Segment(this[i].size)
                if (remainingSpace > 0) this.add(i + 1, Segment(remainingSpace))
                break
            }
            tail--
        }
    }

    private fun MutableList<Segment>.compact() {
        var head = 0
        var tail = this.size - 1
        while (head < tail) {
            while (head < tail && this[head].id != EMPTY_ID) head++
            while (head < tail && this[tail].id == EMPTY_ID) tail--
            if (head >= tail) return
            val extraBlocks = this[head].size - this[tail].size
            when {
                extraBlocks < 0 -> {
                    this[head] = Segment(this[head].size, this[tail].id)
                    this[tail] = Segment(-extraBlocks, this[tail].id)
                }

                extraBlocks == 0 -> this[head] = this[tail].also { this[tail] = this[head] }

                else -> {
                    this[head] = this[tail]
                    this[tail] = Segment(this[tail].size)
                    this.add(head + 1, Segment(extraBlocks))
                }
            }
        }
    }

    private fun String.segments(): MutableList<Segment> =
        this.mapIndexedTo(ArrayDeque()) { index, char ->
            if (index % 2 == 0) Segment(char.digitToInt(), index / 2)
            else Segment(char.digitToInt())
        }

    private fun List<Segment>.checksum(): Long {
        var index = 0
        var sum = 0L
        this.forEach { segment ->
            if (segment.id != EMPTY_ID) {
                for (i in 0..<segment.size) {
                    sum += segment.id * (index + i)
                }
            }
            index += segment.size
        }
        return sum
    }

    private class Segment(val size: Int, val id: Int = EMPTY_ID)
}
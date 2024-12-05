package me.wolfii.year2024.day5

import me.wolfii.automation.Solution

class Day5 : Solution {
    override fun solveFirst(lines: List<String>): Int {
        val rules = lines.rules()
        return lines.pages()
            .filter { it.satisfies(rules) }
            .sumOf { it[it.size / 2] }
    }

    override fun solveSecond(lines: List<String>): Int {
        val rules = lines.rules()
        return lines.pages()
            .filterNot { it.satisfies(rules) }
            .map { it.order(rules) }
            .sumOf { it[it.size / 2] }
    }

    private fun List<String>.rules() = HashMap<Int, MutableSet<Int>>().also { rules ->
        this.takeWhile { it.isNotEmpty() }
            .map { it.substringBefore("|").toInt() to it.substringAfter("|").toInt() }
            .forEach { pair ->
                rules.getOrPut(pair.second) { HashSet() }.add(pair.first)
            }
    }

    private fun List<String>.pages() = this.takeLastWhile { it.isNotEmpty() }.map { it.split(",").map(String::toInt) }

    private fun List<Int>.satisfies(rules: Map<Int, Set<Int>>) = this.indices.all { index ->
        val before = rules[this[index]] ?: return@all true
        this.subList(index + 1, this.size).none { before.contains(it) }
    }

    private fun List<Int>.order(rules: Map<Int, Set<Int>>) = ArrayList<Int>(this).also { ordered ->
        ordered.indices.reversed().forEach { index ->
            val before = rules[ordered[index]] ?: return@forEach
            var i = index
            while (ordered.subList(i + 1, ordered.size).any { before.contains(it) }) {
                ordered[i + 1] = ordered[i].also { ordered[i] = ordered[i + 1] }
                i += 1
            }
        }
    }
}
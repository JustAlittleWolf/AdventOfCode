package me.wolfii.year2024.day17

import me.wolfii.automation.Solution
import kotlin.math.pow

class Day17 : Solution {
    override fun solveFirst(lines: List<String>): String {
        val registers = lines.registers()
        return runProgram(
            registers.getValue('A'),
            registers.getValue('B'),
            registers.getValue('C'),
            lines.program()
        ).joinToString(",")
    }

    override fun solveSecond(lines: List<String>): Long? {
        val registers = lines.registers()
        val program = lines.program()
        return findARegisterForCloneProgram(
            program = program,
            bRegisterInitial = registers.getValue('B'),
            cRegisterInitial = registers.getValue('C')
        )
    }

    private fun List<String>.registers(): MutableMap<Char, Long> = this.takeWhile { it.isNotEmpty() }.associate { line ->
        line.substringBefore(": ").last() to line.substringAfter(": ").toLong()
    }.toMutableMap()

    private fun List<String>.program(): List<Int> = this.last().removePrefix("Program: ").split(',').map { it.toInt() }

    private fun runProgram(aRegisterInitial: Long, bRegisterInitial: Long, cRegisterInitial: Long, program: List<Int>): List<Int> {
        var instructionPointer = 0
        val output = ArrayList<Int>()
        var aRegister = aRegisterInitial
        var bRegister = bRegisterInitial
        var cRegister = cRegisterInitial
        while (instructionPointer < program.size) {
            val instruction = program[instructionPointer]
            val operand = program[instructionPointer + 1]

            when (instruction) {
                0 -> aRegister = (aRegister / 2.0.pow(operand.toComboOperand(aRegister, bRegister, cRegister).toDouble())).toLong()
                1 -> bRegister = bRegister xor operand.toLong()
                2 -> bRegister = operand.toComboOperand(aRegister, bRegister, cRegister) % 8
                3 -> if (aRegister != 0L) instructionPointer = operand - 2
                4 -> bRegister = bRegister xor cRegister
                5 -> output.add((operand.toComboOperand(aRegister, bRegister, cRegister) % 8).toInt())
                6 -> bRegister = (aRegister / 2.0.pow(operand.toComboOperand(aRegister, bRegister, cRegister).toDouble())).toLong()
                7 -> cRegister = (aRegister / 2.0.pow(operand.toComboOperand(aRegister, bRegister, cRegister).toDouble())).toLong()
                else -> throw IllegalArgumentException()
            }

            instructionPointer += 2
        }
        return output
    }

    private fun Int.toComboOperand(aRegister: Long, bRegister: Long, cRegister: Long): Long {
        return when (this) {
            in 0..3 -> this.toLong()
            4 -> aRegister
            5 -> bRegister
            6 -> cRegister
            else -> throw IllegalArgumentException()
        }
    }

    private fun findARegisterForCloneProgram(
        program: List<Int>,
        aRegisterCurrent: Long = 0,
        bRegisterInitial: Long,
        cRegisterInitial: Long,
        matchIndex: Int = program.size - 1
    ): Long? {
        if (matchIndex < 0) return aRegisterCurrent
        for (aRegisterValue in (aRegisterCurrent * 8)..<(aRegisterCurrent * 8 + 8)) {
            val output = runProgram(aRegisterValue, bRegisterInitial, cRegisterInitial, program)
            if (output.first() != program[matchIndex]) continue
            val result = findARegisterForCloneProgram(program, aRegisterValue, bRegisterInitial, cRegisterInitial, matchIndex - 1)
            if (result != null) return result
        }
        return null
    }
}
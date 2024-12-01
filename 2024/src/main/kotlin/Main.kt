package me.wolfii

import me.wolfii.automation.Input
import me.wolfii.automation.Part
import me.wolfii.automation.SolveOptions
import me.wolfii.automation.Solver

fun main() {
    Solver.solve(
        SolveOptions(
            part = Part.BOTH,
            input = Input.BOTH
        )
    )
}


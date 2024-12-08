package me.wolfii.common


fun <T> Collection<T>.cartesian(): Sequence<Pair<T, T>> = sequence {
    for (first in this@cartesian) {
        for (second in this@cartesian) {
            yield(Pair(first, second))
        }
    }
}

fun <T> Collection<T>.cartesianUnique(): Sequence<Pair<T, T>> = sequence {
    for (first in this@cartesianUnique) {
        for (second in this@cartesianUnique) {
            if (first == second) continue
            yield(Pair(first, second))
        }
    }
}
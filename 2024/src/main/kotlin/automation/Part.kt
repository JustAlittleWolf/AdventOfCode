package me.wolfii.automation

enum class Part {
    ONE,
    TWO,
    BOTH;

    fun runFirst() = this == ONE || this == BOTH
    fun runSecond() = this == TWO || this == BOTH
}
package me.wolfii.automation

enum class Input {
    TEST,
    REAL,
    BOTH;

    fun requiresTestInput(): Boolean = this == TEST || this == BOTH
    fun requiresRealInput(): Boolean = this == REAL || this == BOTH
}
package me.wolfii.common

fun leastCommonMultiple(a: Int, b: Int): Long = leastCommonMultipleL(a.toLong(), b.toLong())
fun leastCommonMultipleL(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun greatestCommonDenominator(a: Int, b: Int): Int = greatestCommonDenominatorL(a.toLong(), b.toLong()).toInt()
fun greatestCommonDenominatorL(a: Long, b: Long): Long {
    var num1 = a
    var num2 = b
    while (num2 != 0L) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }
    return num1
}
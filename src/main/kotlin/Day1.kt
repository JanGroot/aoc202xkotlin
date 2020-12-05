fun main() {
    val numbers = {}.javaClass.getResource("input1.txt").readText().lines().map { it.toInt() }
    println(productOfTwoEntriesSummingTo2020(numbers))
    println(productOfThreeEntriesSummingTo2020(numbers))
}

fun productOfTwoEntriesSummingTo2020(input: List<Int>): Int {
    for (i in input) for (j in input) if (i + j == 2020) return i * j
    error("not found")
}

fun productOfThreeEntriesSummingTo2020(input: List<Int>): Int {
    for (i in input) for (j in input) for (k in input) if (i + j + k == 2020) return i * j * k
    error("not found")
}
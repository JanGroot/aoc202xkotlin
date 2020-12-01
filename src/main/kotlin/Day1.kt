fun read() = {}.javaClass.getResource("input1.txt").readText().lines().map {
    it.toInt()
}

fun main() {
    val input = read()
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<Int>): Int {
    for (i in input) for (j in input) if (i + j == 2020) return i * j
    error("not found")
}

fun part2(input: List<Int>): Int {
    for (i in input) for (j in input) for (k in input) if (i + j + k == 2020) return i * j * k
    error("not found")
}
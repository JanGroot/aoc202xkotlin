package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-1.txt")!!.readText().split("\n\n");
    println(dayOnePartOne(input))
    println(dayOnePartTwo(input))
}

fun dayOnePartOne(input: List<String>): Int {
    return input
        .map { s -> s.lines().filter { it.isNotEmpty() } }
        .maxOf { l -> l.sumOf { s -> s.toInt() } }
}

fun dayOnePartTwo(input: List<String>): Int {
    return input
        .asSequence()
        .map { s -> s.lines().filter { it.isNotEmpty() } }
        .map { l -> l.sumOf { s -> s.toInt() } }
        .sortedDescending()
        .take(3)
        .sum()
}
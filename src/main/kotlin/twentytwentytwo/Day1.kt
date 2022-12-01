package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-1.txt")!!.readText().split("\n\n");
    println(dayOnePartOne(input))
    println(dayOnePartTwo(input))
}

fun dayOnePartOne(input: List<String>): Int {
    return input.map { s -> s.lines().filter { x -> x != "" } }.map { l -> l.map { s -> s.toInt() }.sum() }.max();
}

fun dayOnePartTwo(input: List<String>): Int {
    return input.asSequence().map { s -> s.lines().filter { x -> x != "" } }.map { l -> l.map { s -> s.toInt() }.sum() }.sortedDescending().take(3).sum()
}
package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-8.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day8(input)
    println(day.part1())
    println(day.part2())
}

class Day8(private val input: List<String>) {
    fun part1(): Int {
        error("not found")
    }

    fun part2(): Int {
        error("not found")
    }
}


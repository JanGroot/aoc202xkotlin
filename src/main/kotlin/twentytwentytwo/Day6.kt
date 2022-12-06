package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-6.txt")!!.readText();
    val day = Day6(input)
    println(day.part1())
    println(day.part2())
}

class Day6(private val input: String) {
    fun part1(): Int {
        return input.asSequence().windowed(4)
            .indexOfFirst { it.toSet().size == 4 } + 4
    }

    fun part2(): Int {
        return input.asSequence().windowed(14)
            .indexOfFirst { it.toSet().size == 14 } + 14
    }
}


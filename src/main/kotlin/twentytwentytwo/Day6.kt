package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-6.txt")!!.readText();
    val day = Day6(input)
    println(day.part1())
    println(day.part2())
}

class Day6(private val input: String) {
    fun part1(): Int {
        input.split("")
        val found = input.split("").asSequence().filter { it.isNotEmpty() }.windowed(4).filter { it.toSet().size == 4 }.first().joinToString("")
        return input.indexOf(found) + 4
    }

    fun part2(): Int {
        input.split("")
        val found = input.split("").asSequence().filter { it.isNotEmpty() }.windowed(14).filter { it.toSet().size == 14 }.first().joinToString("")
        return input.indexOf(found) + 14
    }
}


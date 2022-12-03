package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-X.txt")!!.readText().split("\n").filter { it.isNotEmpty() };
    val day = DayX(input)
    println(day.part1())
    println(day.part2())
}

class DayX(private val input: List<String>) {
    fun part1(): Int {
        error("not found")
    }

    fun part2(): Int {
        error("not found")
    }
}


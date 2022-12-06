package twentytwentytwo


fun main() {
    val day3 = Day3({}.javaClass.getResource("input-3.txt")!!.readText().linesFiltered { it.isNotEmpty() })
    println(day3.part1())
    println(day3.part2())
}

class Day3(private val input: List<String>) {
    private val priorities = (('a'..'z') + ('A'..'Z')).withIndex().associate { it.value to it.index + 1 }
    fun part1(): Int {
        return input.sumOf {
            val (first, second) = it.windowed(it.length / 2, it.length / 2)
            priorities[first.toSet().intersect(second.toSet()).first()]!!
        }
    }

    fun part2(): Int {
        return input.windowed(3, 3).sumOf { (first, second, third) ->
            priorities[first.toSet().intersect(second.toSet()).intersect(third.toSet()).first()]!!
        }
    }
}
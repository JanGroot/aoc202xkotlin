package twentytwentytwo


fun main() {
    val input = {}.javaClass.getResource("input-22.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val tinput = {}.javaClass.getResource("input-22-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val test = Day21(tinput)
    val day = Day21(input)
    println(test.part1())
    println(test.part2())
    println(day.part1())
    println(day.part2())
}


class Day22(input: List<String>) {

    fun part1() {
        TODO()
    }

    fun part2() {
        TODO()
    }
}


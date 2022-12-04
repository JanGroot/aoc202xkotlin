package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-4.txt")!!.readText().split("\n").filter { it.isNotEmpty() }.map { s ->
        s.split(",").map { p ->
            p.split("-").map { it.toInt() }
        }.map { it.first()..it.last() }
    }
    val day = Day4(input)
    println(day.part1())
    println(day.part2())
}

class Day4(private val input: List<List<IntRange>>) {
    fun part1(): Int {
        return input
            .count {
                it.first().all { n -> it.last().contains(n) } || it.last().all { n -> it.first().contains(n) }
            }
    }

    fun part2(): Int {
        return input
            .count {
                it.first().any { n -> it.last().contains(n) } || it.last().any { n -> it.first().contains(n) }
            }
    }
}


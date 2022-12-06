package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-4.txt")!!.readText().linesFiltered{ it.isNotEmpty() }.map { s ->
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
            .count { (first, second) ->
                first.all { n -> second.contains(n) } || second.all { n -> first.contains(n) }
            }
    }

    fun part2(): Int {
        return input
            .count { (first, second) ->
                first.any { n -> second.contains(n) }
            }
    }
}


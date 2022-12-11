package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-10.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day10(input)
    println(day.part1())
    println(day.part2())
}

class Day10(private val input: List<String>) {
    fun part1(): Int {
        var cycle = 0
        var value = 1
        var result = mutableListOf<Int>()
        input.forEach {
            when (it) {
                "noop" -> {
                    if (++cycle in intArrayOf(20, 60, 100, 140, 180, 220)) {
                        result.add(value * cycle)
                    }
                }

                else -> {
                    if (++cycle in intArrayOf(20, 60, 100, 140, 180, 220)) {
                        result.add(value * cycle)
                    }
                    if (++cycle in intArrayOf(20, 60, 100, 140, 180, 220)) {
                        result.add(value * cycle)
                    }
                    value += it.split(" ")[1].toInt()
                }

            }
        }
        return result.sum()
    }

    fun part2(): Int {
        var cycle = 0
        var value = 1
        var row = ""
        input.forEach {
            when (it) {
                "noop" -> {
                    row += if (cycle % 40 in value-1 ..value + 1 ) "#" else "."
                    cycle++
                }
                else -> {
                    row += if (cycle % 40 in value-1..value + 1) "#" else "."
                    cycle++
                    row += if (cycle % 40 in value-1..value + 1) "#" else "."
                    cycle++
                    value += it.split(" ")[1].toInt()
                }
            }
        }
        row.chunked(40).forEach { println(it) }

        return 0
    }
}


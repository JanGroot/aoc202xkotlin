package twentytwentytwo


fun main() {
    val tinput = {}.javaClass.getResource("input-24-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val input = {}.javaClass.getResource("input-24.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val test = Day24(tinput)
    val day = Day24(input)
    println(test.part1())
    println(test.part2())
    println(day.part1())
    println(day.part2())
}

class Day24(private val input: List<String>) {
    var blizzards: Map<Pair<Int, Int>, List<Char>> = input.mapIndexed { y, row ->
        row.mapIndexed { x, s -> (x to y) to s }.filter { it.second != '.' }
    }.flatten().groupBy({ it.first }, { it.second })
    val rows = input.size
    val cols = input.first().length
    val start = input.first().indexOf('.') to 0
    val end = input.last().indexOf('.') to input.size
    fun part1(): Int {
        return stormWalker(start, end)
    }

    fun part2(): Int {
        blizzards = input.mapIndexed { y, row ->
            row.mapIndexed { x, s -> (x to y) to s }.filter { it.second != '.' }
        }.flatten().groupBy({ it.first }, { it.second })
        var part1 = stormWalker(start, end)
        var part2 = stormWalker(end, start)
        var part3 = stormWalker(start, end)
        return part1 + part2 + part3
    }

    private fun stormWalker(start: Pair<Int, Int>, destination: Pair<Int, Int>): Int {
        var options = setOf(start) // start
        var end = destination
        repeat(10000) { it ->
            blizzards = blizzards.map { (k, v) ->
                v.map { c -> k.next(c) }
            }.flatten().groupBy({ it.first }, { it.second })
            options = options.flatMap { it.options().filter { o -> o !in blizzards.keys } }.toSet()
            if (options.contains(end)) {
                return it
            }
        }
        error("ooops")
    }

    fun Pair<Int, Int>.next(c: Char): Pair<Pair<Int, Int>, Char> =
        when (c) {
            '#' -> this to c
            '<' -> ((if (first == 1) cols - 2 else first - 1) to second) to c
            '>' -> ((if (first == cols - 2) 1 else first + 1) to second) to c
            'v' -> (first to (if (second == rows - 2) 1 else second + 1)) to c
            '^' -> (first to (if (second == 1) rows - 2 else second - 1)) to c
            else -> error("oops")
        }

    fun Pair<Int, Int>.options(): Set<Pair<Int, Int>> {
        return setOf(
            first to second,
            first to second - 1,
            first to second + 1,
            first + 1 to second,
            first - 1 to second,
        ).filter { it.first >= 0 && it.second >= 0 }.toSet()
    }
}


package twentytwentytwo

import kotlin.math.abs

typealias Elve = Pair<Int, Int>

fun main() {
    val input = {}.javaClass.getResource("input-23.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val tinput = {}.javaClass.getResource("input-23-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day23(input)
    val test = Day23(tinput)
    println(test.part1())
    println(day.part1())
    println(day.part2())
}

class Day23(private val input: List<String>) {
    var elves: MutableSet<Elve> = mutableSetOf()
    var direction: String = "N"

    fun part1(): Int {
        elves = input.mapIndexed { y, row ->
            row.mapIndexed { x, s -> (x to y) to s }
        }.flatten().filter { p -> p.second == '#' }.map { it.first }
            .toMutableSet()
        repeat(10) {
            val moves = elves.filter { elve ->
                elve.all().any { elves.contains(it) }
            }.map { elve ->
                elve.getNext() to elve
            }.groupBy { it.first }

            moves.filter { it.value.size == 1 }.forEach {
                elves.remove(it.value.first().second)
                elves.add(it.key)
            }
            direction = nextDirection(direction)
        }
        println(elves.size)
        return elves.emptySpaces()
    }


    fun part2(): Int {
        direction = "N"
        elves = input.mapIndexed { y, row ->
            row.mapIndexed { x, s -> (x to y) to s }
        }.flatten().filter { p -> p.second == '#' }.map { it.first }
            .toMutableSet()
        repeat(10000)  {
            val moves = elves.filter { elve ->
                elve.all().any { elves.contains(it) }
            }.map { elve ->
                elve.getNext() to elve
            }.groupBy { it.first }

            if (moves.isEmpty()) return it + 1
            moves.filter { it.value.size == 1 }.forEach {
                elves.remove(it.value.first().second)
                elves.add(it.key)
            }
            direction = nextDirection(direction)
        }
        error("oops")
    }

    fun Elve.getNext(): Elve {
        var next = direction
        repeat(4) {
            if (neighbours(next).none { n -> elves.contains(n) }) {
                return move(next)
            }
            next = nextDirection(next)
        }
        return this
    }

    fun Elve.neighbours(direction: String): List<Elve> {
        return when (direction) {
            "N" -> {
                listOf(
                    first to second - 1,
                    first - 1 to second - 1,
                    first + 1 to second - 1,
                )
            }

            "S" -> {
                listOf(
                    first to second + 1,
                    first - 1 to second + 1,
                    first + 1 to second + 1,
                )
            }

            "W" -> {
                listOf(
                    first - 1 to second,
                    first - 1 to second + 1,
                    first - 1 to second - 1,
                )
            }

            "E" -> {
                listOf(
                    first + 1 to second,
                    first + 1 to second + 1,
                    first + 1 to second - 1,
                )
            }

            else -> {
                error("oops")
            }
        }
    }

    fun Elve.all() =
        listOf(
            first to second - 1,
            first to second + 1,
            first + 1 to second,
            first - 1 to second,
            first - 1 to second + 1,
            first - 1 to second - 1,
            first + 1 to second - 1,
            first + 1 to second + 1,
        )

    fun Elve.move(dir: String): Elve {
        return when (dir) {
            "N" -> (first to second - 1)
            "S" -> (first to second + 1)
            "W" -> (first - 1 to second)
            "E" -> (first + 1 to second)
            else -> {
                this
            }
        }
    }
    private fun Set<Elve>.emptySpaces(): Int =
        (abs(maxOf { it.first } - minOf { it.first }) + 1) * (abs(maxOf { it.second } - minOf { it.second }) + 1) - size

    companion object {
        fun nextDirection(dir: String) = when (dir) {
            "N" -> "S"
            "S" -> "W"
            "W" -> "E"
            "E" -> "N"
            else -> {
                error("oops")
            }
        }
    }
}




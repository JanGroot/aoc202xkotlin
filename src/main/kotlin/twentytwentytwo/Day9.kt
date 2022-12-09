package twentytwentytwo

import twentytwentytwo.Structures.Point2d

typealias Rope = MutableList<Point2d>

fun main() {
    val input = {}.javaClass.getResource("input-9.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day9(input)
    println(day.part1())
    println(day.part2())
}

class Day9(private val input: List<String>) {
    private val start = Point2d(0, 0)

    fun part1() = mutableListOf(start, start).posVisited(1)
    fun part2() = (1..10).map { start }.toMutableList().posVisited(9)

    private fun Rope.posVisited(position: Int): Int {
        val visited = mutableSetOf<Point2d>()
        input.forEach {
            val (dir, steps) = it.split(" ")
            repeat(steps.toInt()) {
                move(dir)
                visited.add(this[position])
            }
        }
        return visited.size
    }

    private fun Rope.move(dir: String) {
        forEachIndexed { index, point ->
            when (index) {
                0 -> this[index] = point.move(dir)
                else -> this[index] = point.follow(this[index - 1])
            }
        }
    }
}


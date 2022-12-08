package twentytwentytwo

import twentytwentytwo.Structures.Point2d
import kotlin.math.sqrt

typealias Tree = Pair<Point2d, Int>

fun main() {
    val input = {}.javaClass.getResource("input-8.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day8(input)
    val columns = (0 until input[0].length).map { index -> input.map { it[index].digitToInt() } }.also { println(it) }
    val rows = (input.indices).map { index -> input[index].map { it.digitToInt() } }.also { println(it) }
    val size = sqrt(input.joinToString(separator = "") { it }.length.toDouble()).toInt()
    println(size)
    val trees = input.joinToString(separator = "") { it }.mapIndexed { index, c ->
        val x = index / size
        val y = index % size
        Tree(Point2d(x, y), c.digitToInt())
    }
    trees.forEach { println("${it.first.x},${it.first.y}   ${it.second}") }
    println(size)
    println(day.part1())
    println(day.part2())
}

class Day8(input: List<String>) {
    private val trees = input.mapIndexed { y, row ->
        row.mapIndexed { x, s -> Tree(Point2d(x, y), s.digitToInt()) }
    }.flatten()

    init {
        trees.forEach { (point) ->
            rows.computeIfAbsent(point.y) { trees.filter { it.first.sameRow(point) } }
            columns.computeIfAbsent(point.x) { trees.filter { it.first.sameColumn(point) } }
        }
    }

    fun part1() = trees.filter { it.visible() }.size

    fun part2() = trees.maxOf { it.sees() }


    companion object {
        private val rows = mutableMapOf<Int, List<Tree>>()
        private val columns = mutableMapOf<Int, List<Tree>>()

        fun Tree.sees(): Int {
            val (left, right, above, under) = getLines()
            return countVisibleTrees(left.reversed()) * countVisibleTrees(right) * countVisibleTrees(under) * countVisibleTrees(
                above.reversed()
            )
        }

        fun Tree.visible() = edge() || getLines().any { isBiggest(it) }

        // check if we reached the end otherwise add 1. (should have a takeWhileInclusive or something)
        private fun Tree.countVisibleTrees(trees: List<Tree>) = trees.takeWhile { it.second < second }.count()
            .let { count -> if (count == trees.size) count else count + 1 }

        private fun Tree.getLines() = rows[first.y]!!.split(this.first.x) + columns[first.x]!!.split(this.first.y)

        private fun List<Tree>.split(number: Int) = listOf(take(number), takeLast(size - number - 1))

        private fun Tree.isBiggest(trees: List<Tree>) = trees.all { it.second < second }

        private fun Tree.edge() = first.x == 0 || first.x == columns.size || first.y == 0 || first.y == rows.size
    }
}


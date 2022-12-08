package twentytwentytwo

import twentytwentytwo.Structures.Point2d

typealias Tree = Pair<Point2d, Int>

fun main() {
    val input = {}.javaClass.getResource("input-8.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day8(input)
    println(day.part1())
    println(day.part2())
}

class Day8(input: List<String>) {
    private val trees = input.mapIndexed { y, row ->
        row.mapIndexed { x, s -> Pair(Point2d(x, y), s.code) }
    }.flatten()

    init {
        trees.forEach { (point) ->
            rows.computeIfAbsent(point.y) { trees.filter { it.first.sameRow(point) } }
            columns.computeIfAbsent(point.x) { trees.filter { it.first.sameColumn(point) } }
        }
    }

    fun part1() = trees.filter { it.visible() }.toSet().size

    fun part2() = trees.maxOf { it.sees() }


    companion object {
        private val rows = mutableMapOf<Int, List<Tree>>()
        private val columns = mutableMapOf<Int, List<Tree>>()

        private fun List<Tree>.split(number: Int) = listOf(take(number), takeLast(size - number - 1))

        fun Tree.sees(): Int {
            val (left, right) = rows[first.y]!!.split(this.first.x)
            val (above, under) = columns[first.x]!!.split(this.first.y)
            return countVisibleTrees(left.reversed()) * countVisibleTrees(right) * countVisibleTrees(under) * countVisibleTrees(
                above.reversed()
            )
        }

        private fun Tree.countVisibleTrees(trees: List<Tree>): Int =
        // check if we reached the end otherwise add 1. (should have a takeWhileInclusive or something)
            // could also be max of both..
            trees.takeWhile { it.second < second }.count()
                .let { count -> if (count == trees.size) count else count + 1 }


        fun Tree.visible(): Boolean {
            val lines = rows[first.y]!!.split(this.first.x) + columns[first.x]!!.split(this.first.y)
            return edge() || lines.any { isBiggest(it) }
        }

        private fun Tree.isBiggest(trees: List<Tree>) = trees.all { it.second < second }

        private fun Tree.edge() = first.x == 0 || first.x == columns.size || first.y == 0 || first.y == rows.size
    }
}


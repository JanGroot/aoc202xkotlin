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

    fun part1(): Int {
        val maxX = trees.maxOf { it.first.x }
        val maxY = trees.maxOf { it.first.y }
        return trees.filter { it.edge(maxX, maxY) || it.visible() }.toSet().size
    }

    fun part2(): Int = trees.maxOf { it.sees() }


    companion object {
        private val rows = mutableMapOf<Int, List<Tree>>()

        private val columns = mutableMapOf<Int, List<Tree>>()
        private fun List<Tree>.split(number: Int): Pair<List<Tree>, List<Tree>> {
            return Pair(take(number), takeLast(size - number - 1))
        }

        fun Tree.sees(): Int {
            val row = rows[first.y]
            val column = columns[first.x]
            val (left, right) = row!!.split(this.first.x)
            val (above, under) = column!!.split(this.first.y)
            return countVisibleTrees(left.reversed()) * countVisibleTrees(right) * countVisibleTrees(under) * countVisibleTrees(above.reversed())
        }

        private fun Tree.countVisibleTrees(trees: List<Tree>): Int =
            // check if we reached the end otherwise add 1. (should have a takeWhileInclusive or something)
            // could also be max of both..
            trees.takeWhile { it.second < second }
                .count().let { count -> if (count == trees.size) count else count + 1 }


        fun Tree.visible(): Boolean {
            val row = rows[first.y]
            val column = columns[first.x]
            val (left, right) = row!!.split(this.first.x)
            val (above, under) = column!!.split(this.first.y)
            return left.all { it.second < second } || right.all { it.second < second } || above.all { it.second < second } || under.all { it.second < second }
        }

        fun Tree.edge(maxX: Int, maxY: Int): Boolean {
            return first.x == 0 || first.x == maxX || first.y == 0 || first.y == maxY
        }
    }
}


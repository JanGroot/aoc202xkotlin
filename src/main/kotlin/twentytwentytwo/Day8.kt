package twentytwentytwo

import twentytwentytwo.Structures.*

typealias Tree = Pair<Point2d, Int>
fun main() {
    val input = {}.javaClass.getResource("input-8.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day8(input)
    println(day.part1())
    println(day.part2())
}

class Day8(private val input: List<String>) {

    fun part1(): Int {
        val grid = input.mapIndexed { indexY, s ->
            s.split("").filter { it.isNotEmpty() }.mapIndexed { indexX, s -> Pair(Point2d(indexX, indexY), s.toInt()) }
        }.flatten();
        val maxX = grid.maxOf { it.first.x }
        val maxY = grid.maxOf { it.first.y }
        return grid.filter { it.edge(maxX, maxY) || it.visible(grid) }.toSet().size
    }

    fun part2(): Int {
        val grid = input.mapIndexed { indexY, s ->
            s.split("").filter { it.isNotEmpty() }
                .mapIndexed { indexX, s -> Tree(Point2d(indexX, indexY), s.toInt()) }
        }.flatten();
        return grid.maxOf { it.sees(grid) }
    }

    private fun List<Tree>.split(number: Int): Pair<List<Tree>, List<Tree>> {
        return Pair(take(number), takeLast(size - number -1))
    }


    fun Tree.sees(grid: List<Tree>): Int {
        val row = grid.filter { this.first.sameRow(it.first) }
        val column = grid.filter { this.first.sameColumn(it.first) }
        val (left, right) = row.split(this.first.x)
        val (above, under) = column.split(this.first.y)
        return left.reversed().takeWhile { it.second < second }.count()
            .let { count -> if (count == left.size) count else count + 1 } * right.takeWhile { it.second < second }
            .count().let { count -> if (count == right.size) count else count + 1 } * above.reversed()
            .takeWhile { it.second < second }.count()
            .let { count -> if (count == above.size) count else count + 1 } * under.takeWhile { it.second < second }
            .count().let { count -> if (count == under.size) count else count + 1 }
    }

    fun Pair<Point2d, Int>.visible(grid: List<Tree>) : Boolean{
        val row = grid.filter { this.first.sameRow(it.first) }
        val column = grid.filter { this.first.sameColumn(it.first) }
        val (left, right) = row.split(this.first.x)
        val (above, under) = column.split(this.first.y)
        return left.all {it.second < second}  || right.all{it.second < second} || above.all{it.second < second} || under.all { it.second < second }
    }

    fun Tree.edge(maxX: Int, maxY: Int) : Boolean{
        return first.x == 0 || first.x == maxX || first.y == 0 || first.y == maxY
    }

    companion object {
        val rows = mutableMapOf<Int, List<Tree>>()
    }
}


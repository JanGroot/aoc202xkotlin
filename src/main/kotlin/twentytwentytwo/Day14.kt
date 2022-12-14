package twentytwentytwo

import twentytwentytwo.Structures.Point2d

fun main() {
    val input = {}.javaClass.getResource("input-14.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day14(input)
    println(day.part1())
   println(day.part2())
}


class Day14(val input: List<String>) {


    private val origin: Point2d = Point2d(500, 0)

    fun part1(): Int {
        val grid = createGrid(getRocks(input))
        drop(origin, grid)
        return grid.sumOf { it.count { c -> c == 'o' } }
    }

    fun part2(): Int {
        val rocks = getRocks(input)
        val y = rocks.maxBy { it.y }.y + 2
        val extended = rocks + (Point2d(500 - y , y).lineTo(Point2d(500 + y, y)))
        val grid = createGrid(extended)
        drop(origin, grid)
        return grid.sumOf { it.count { c -> c == 'o' } }
    }

    private fun drop(source: Point2d, grid: Array<CharArray>) {
        var point = source
        while (point.down in grid && grid[point] != 'o') {
            when {
                grid[point.down] !in rockOrSand -> {
                    point = point.down
                }

                grid[point.downLeft] !in rockOrSand -> {
                    point = point.downLeft
                }

                grid[point.downRight] !in rockOrSand -> {
                    point = point.downRight
                }
                else -> {
                    grid[point] = 'o'
                    point = source
                }
            }
        }
    }


    private fun createGrid(rocks: List<Point2d>): Array<CharArray> {
        val maxX = rocks.maxBy { it.x }.x
        val maxY = rocks.maxBy { it.y }.y

        val grid: Array<CharArray> = (0..maxY).map {
            CharArray(maxX + 2).apply { fill('.') }
        }.toTypedArray()

        rocks.forEach { rock ->
            grid[rock] = '#'
        }
        return grid
    }

    private fun getRocks(input: List<String>): List<Point2d> {
        return input.flatMap { row ->
            val points = row.split(" -> ").map { p -> p.split(",").map { it.toInt() } }.map { (x, y) -> Point2d(x, y) }
            points.windowed(2, 1).map { (from, to) -> from lineTo to }.flatten()
        }
    }

    companion object {
        private val rockOrSand = setOf('#', 'o')
    }

    private operator fun Array<CharArray>.get(point: Point2d): Char =
        this[point.y][point.x]

    private operator fun Array<CharArray>.set(point: Point2d, to: Char) {
        this[point.y][point.x] = to
    }

    private operator fun Array<CharArray>.contains(point: Point2d): Boolean =
        point.x >= 0 && point.x < this[0].size && point.y >= 0 && point.y < this.size

}


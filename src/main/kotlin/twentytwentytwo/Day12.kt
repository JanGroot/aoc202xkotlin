package twentytwentytwo

import twentytwentytwo.Structures.Point2d

typealias Land = Point2d

fun main() {
    val input = {}.javaClass.getResource("input-12.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day12(input)
    println(day.part1())
    println(day.part2())
    //
}

class Day12(private val input: List<String>) {
    private val land = input.mapIndexed { y, row ->
        row.mapIndexed { x, s -> Point2d(x, y,s ) }
    }.flatten()

    fun part1(): Int {
        val start = land.filter { it.value == 'S' }.map{ Point2d(it.x, it.y, 'a')}.first()
        return land.bfs(start)
    }

    fun part2() = land.filter { it.value == 'a' }.minOfOrNull { land.bfs(it) }

    fun List<Land>.bfs(start: Point2d): Int {
        val queue = Structures.ArrayListQueue<Pair<Point2d, Int>>()
        queue.enqueue(Pair(start, 1))
        val visited = hashSetOf<Point2d>()
        visited.add(start)

        while ( !queue.isEmpty) {
            val (node, index) = queue.dequeue()!!
            fun search(point: Point2d) = land.firstOrNull { it.x == point.x && it.y == point.y }

            node.neighbors().mapNotNull { search(it) }.forEach { neighbour ->

                val nv = if (neighbour.value == 'E') 'z' else neighbour.value
                if (node.value == 'S' || (nv <= node.value + 1) && visited.add(neighbour)) {
                    if (neighbour.value == 'E') return index
                    queue.enqueue(Pair(neighbour, index + 1))
                }
            }
        }
        return Int.MAX_VALUE
    }


}






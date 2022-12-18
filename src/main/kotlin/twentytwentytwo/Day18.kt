package twentytwentytwo

import twentytwentytwo.Structures.Point3d

fun main() {
    val input = {}.javaClass.getResource("input-18.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val test = {}.javaClass.getResource("input-18-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day18(input)
    val testDay = Day18(test)
    println(testDay.part1())
    println(testDay.part2())
    println(day.part1())
    println(day.part2())
}

class Day18(private val input: List<String>) {
    private val cubes = input.map { it.split(",") }.map { (x, y, z) -> Point3d(x.toInt(), y.toInt(), z.toInt()) }.toSet()
    private val lava = Lava(cubes)
    fun part1(): Int {
        // count which of the six neigbours are not occupied.
        return cubes.sumOf { c -> c.neighbors().count { it !in cubes } }
    }

    fun part2(): Int {
        val unexposed = lava.getUnExposed()
        // count which of the six neigbours are not occupied and not in unexposed
        return cubes.sumOf { c -> c.neighbors().count { neighbour -> neighbour !in cubes && neighbour !in unexposed } }
    }

    class Lava(private val cubes: Set<Point3d>) {
        private val xBound = cubes.minOf { it.x } until cubes.maxOf { it.x }
        private val yBound = cubes.minOf { it.y } until cubes.maxOf { it.y }
        private val zBound = cubes.minOf { it.z } until cubes.maxOf { it.z }
        private val lava = xBound.map { x -> yBound.map { y -> zBound.map { z -> Point3d(x, y, z) } } }.flatten().flatten().toSet()
        private val potentialExposed = lava.toMutableSet()

        // copied basic idea from the graph class
        fun getUnExposed(): MutableSet<Point3d> {
            // find all connected to outside and remove.
            lava.forEach { node ->
                recurseRemoveExposed(node, setOf(node))
            }
            return potentialExposed
        }

        private tailrec fun recurseRemoveExposed(node: Point3d, connectedNodes: Set<Point3d>) {
            if (node.isExposed()) potentialExposed.removeAll(connectedNodes + node)
            else node.neighbors().filter { !it.isConnected(connectedNodes) && it !in cubes }.forEach { connection ->
                return recurseRemoveExposed(node, connectedNodes + connection)
            }
        }
        private fun Point3d.isExposed() = x !in xBound || y !in yBound || z !in zBound
        private fun Point3d.isConnected(connectedNodes: Set<Point3d>) = this in connectedNodes
    }
}


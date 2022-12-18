package twentytwentytwo

import twentytwentytwo.Structures.Point3d

fun main() {
    val input = {}.javaClass.getResource("input-18.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val test = {}.javaClass.getResource("input-18-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day18(input)
    val testDay = Day18(test)
    println(testDay.part1())
    println(day.part1())
   // println(day.part2())
}

class Day18(private val input: List<String>) {
    val cubes = input.map { it.split(",") }.map { (x,y,z) -> Point3d(x.toInt(),y.toInt(),z.toInt()) }.toSet()
    fun part1(): Int {
      // count which of the six neigbours are not occupied.
       return  cubes.sumOf  { c -> c.neighbors().count { it !in cubes }  }
    }

    fun part2(): Int {
        error("not found")
    }
}


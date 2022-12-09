package twentytwentytwo

import java.awt.Point

fun main() {
    val input = {}.javaClass.getResource("input-9.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day9(input)
    println(day.part1())
    println(day.part2())
}

class Day9(private val input: List<String>) {
    fun part1(): Int {
    var visited = mutableSetOf<Structures.Point2d>()
        var head = Structures.Point2d(0, 0)
        var tail1 = Structures.Point2d(0, 0)
        input.forEach {
            val (dir, steps) = it.split(" ")
            repeat(steps.toInt()) {
                head = head.move(dir)
                tail1 = tail1.follow(head)
                visited.add(tail1)
            }
        }
        return visited.size
    }

    fun part2(): Int {
        var visited = mutableSetOf<Structures.Point2d>()
        var head = Structures.Point2d(0, 0)
        var tail1 = Structures.Point2d(0, 0)
        var tail2 = Structures.Point2d(0, 0)
        var tail3 = Structures.Point2d(0, 0)
        var tail4 = Structures.Point2d(0, 0)
        var tail5 = Structures.Point2d(0, 0)
        var tail6 = Structures.Point2d(0, 0)
        var tail7 = Structures.Point2d(0, 0)
        var tail8 = Structures.Point2d(0, 0)
        var tail9 = Structures.Point2d(0, 0)
        input.forEach {
            val (dir, steps) = it.split(" ")
            repeat(steps.toInt()) {
                head = head.move(dir)
                tail1 = tail1.follow(head)
                tail2 = tail2.follow(tail1)
                tail3 = tail3.follow(tail2)
                tail4 = tail4.follow(tail3)
                tail5 = tail5.follow(tail4)
                tail6 = tail6.follow(tail5)
                tail7 = tail7.follow(tail6)
                tail8 = tail8.follow(tail7)
                tail9 = tail9.follow(tail8)
                visited.add(tail9)
            }


        }
        return visited.size
    }
}


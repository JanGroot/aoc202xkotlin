package twentytwentytwo

import twentytwentytwo.Structures.Point2d
import java.nio.file.Files.move

fun main() {
    val input = {}.javaClass.getResource("input-17.txt")!!.readText();
    val test = {}.javaClass.getResource("input-17-1.txt")!!.readText();
    val dayTest = Day17(test)
    println(dayTest.part1())
    // println(dayTest.part2())
    val day = Day17(input)
    println(day.part1())
    // println(day.part2())
}

class Day17(private val input: String) {

    val shapes = listOf("underscore", "plus", "corner", "pipe", "square")
    val cave = Cave(gas = input)

    fun part1(): Int {
        val cave = Cave(gas = input)
        (0 .. 2022).forEach {
            var factory = ShapeFactory(y = cave.top() + 4)
            val shape = when (shapes[it % shapes.size]) {
                "underscore" -> factory.underScore()
                "plus" -> factory.plus()
                "corner" -> factory.corner()
                "pipe" -> factory.pipe()
                "square" -> factory.square()
                else -> {
                    null
                }
            }
            cave.drop(shape!!)
            println("$it -> ${shapes[it % shapes.size]} -> ${cave.top()}")
        }
        return cave.top()
    }

    fun part2(): Int {
        error("not found")
    }


}

data class ShapeFactory(val x: Int = 2, val y: Int) {
    fun underScore() = setOf(Point2d(x, y), Point2d(x + 1, y), Point2d(x + 2, y), Point2d(x + 3, y))
    fun plus() = setOf(
        Point2d(x + 1, y),
        Point2d(x, y + 1),
        Point2d(x + 1, y + 1),
        Point2d(x + 2, y + 1),
        Point2d(x + 1, y + 2)
    )

    fun corner() = setOf(
        Point2d(x, y), Point2d(x + 1, y), Point2d(x + 2, y), Point2d(x + 2, y + 1), Point2d(x + 2, y + 2)
    )

    fun pipe() = setOf(Point2d(x, y), Point2d(x, y + 1), Point2d(x, y + 2), Point2d(x, y + 3))
    fun square() = setOf(Point2d(x, y), Point2d(x + 1, y), Point2d(x, y + 1), Point2d(x + 1, y + 1))
}

typealias Shape = Set<Point2d>

class Cave(val width: Int = 7, val gas: String) {
    val stack =
        mutableSetOf<Point2d>(Point2d(1, 0), Point2d(2, 0), Point2d(3, 0), Point2d(4, 0), Point2d(5, 0), Point2d(6, 0))
    var move = 0

    fun drop(shape: Shape) {
        var ds = shape
        val instruction = gas[move]
        when (instruction) {
            '<' -> ds = ds.left()
            '>' -> ds = ds.right()
        }
        move = (move + 1) % gas.length
        if (ds.down().any { it in stack }) {
            stack += ds
        } else drop(ds.down())
    }

    fun top(): Int = stack.maxOf { it.y }

    fun Shape.left() = if (any { it.x == 0 } || this.map{it.move("L")}.any{ it in stack}) this else this.map { it.move("L") }.toSet() // also check bound in stack

    fun Shape.right() = if (any { it.x == width - 1 }|| this.map{it.move("R")}.any{ it in stack}) this else this.map { it.move("R") }.toSet() //also check bound in stack

    fun Shape.down() = this.map { it.move("D") }.toSet()

}


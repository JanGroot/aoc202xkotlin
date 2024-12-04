package twentytwentyfour

import kotlin.math.abs

fun main() {
    val input = {}.javaClass.getResource("input-1.txt")!!.readText().split("\n");
    println(dayOnePartOne(input))
    println(dayOnePartTwo(input))
}

fun dayOnePartOne(input: List<String>): Int {
    val map = input
        .map { s -> s.split(" ") }.associate { it.first().toInt() to it.last().toInt() }
    return map.keys.sorted().zip(map.values.sorted()).sumOf { abs(it.first - it.second) }


}

fun dayOnePartTwo(input: List<String>): Int {
    val pairs = input
        .map { s -> s.split(" ") }.map { it.first().toInt() to it.last().toInt() }
    val left = pairs.map { it.first }
    val right = pairs.map {it.second}.toList()
    return left.sumOf { l -> l * right.count{it == l} }

}
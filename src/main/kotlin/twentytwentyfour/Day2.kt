package twentytwentyfour

import kotlin.math.abs

fun main() {
    val input = {}.javaClass.getResource("input-2.txt")!!.readText().split("\n");
   println(dayTwoPartOne(input))
    println(dayTwoPartTwo(input))
}

fun dayTwoPartOne(input: List<String>): Int {


    val projects = input
        .map { s -> s.split(" ").map { it.toInt() } }
    return projects
        .map { it.zipWithNext { a, b -> b - a } }
        .count {
            isSafe(it)
        }
}

private fun isSafe(it: List<Int>) =
    it.all { it in intArrayOf(1, 2, 3) } || it.all { it in intArrayOf(-1, -2, -3) }

fun dayTwoPartTwo(input: List<String>): Int {
    val projects = input
        .map { s -> s.split(" ").map { it.toInt() } }
    return projects
        .count { p ->
            (0 until p.size).any {
                val drop = p.toMutableList()
                drop.removeAt(it)
                isSafe( drop.zipWithNext { a, b -> b - a } .toList()) }
            }

}
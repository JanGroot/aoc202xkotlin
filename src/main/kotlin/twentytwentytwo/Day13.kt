package twentytwentytwo

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlin.math.sign


fun main() {
    val input = {}.javaClass.getResource("input-13.txt")!!.readText().split("\n\n");
    val day = Day13(input)
    println(day.part1())
    println(day.part2())
}

class Day13(private val input: List<String>) {

    fun part1(): Int {
        return input.mapIndexedNotNull { index, s ->
            val (a, b) = s.lines().also { println(it) }
            if (matches(a, b) <= 0) index + 1 else null
        }.also { print(it) }.sum()

    }

    fun part2(): Int {
        val markers = listOf("[[2]]", "[[6]]")
        var extended = input.filter { it.isNotEmpty() }.map{it.lines()}.flatten() + markers
        extended = extended.filter{it.isNotEmpty()}
        extended.forEach { println(it) }
        extended = extended.sortedWith { a, b -> matches(a, b)}
        return (extended.indexOf(markers.first()) + 1) * (extended.indexOf(markers.last()) + 1)
    }

    fun matches(a: String, b: String): Int {
        return matches(Json.decodeFromString<JsonArray>(a), Json.decodeFromString<JsonArray>(b))
    }

    fun matches(x: JsonElement, y: JsonElement) : Int{
        println("comparing $x with $y")
        return when  {
            (x is JsonPrimitive && y is JsonArray) -> matches(JsonArray(listOf(x)),y)
            (x is JsonArray && y is JsonPrimitive) -> matches(x,JsonArray(listOf(y)))
            (x is JsonArray && y is JsonArray) -> {
                (0 until minOf( x.size, y.size)).forEach {
                    val dif = matches(x[it], y[it])
                    if (dif != 0) return dif
                }
                return (x.size - y.size).sign
            }
            (x is JsonPrimitive && y is JsonPrimitive) -> (x.content.toInt() - y.content.toInt()).sign
            else -> {-1}
        }
    }
}

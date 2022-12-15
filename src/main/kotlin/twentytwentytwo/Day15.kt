package twentytwentytwo

import twentytwentytwo.Structures.Point2d
import java.math.BigInteger

fun main() {
    val input = {}.javaClass.getResource("input-15.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day15(input)
    println(day.part1(2000000))
    println(day.part2(4000000))
}

class Day15(private val input: List<String>) {
    fun part1(row: Int): Int {
        val points = mutableSetOf<Point2d>()
        input.map {
            val parts = it.split(": closest beacon is at x=")
            val s = parts[0].split("Sensor at ")[1].split("=", ",")
            val b = parts[1].split("=", ",")
            (Point2d(s[1].toInt(), s[3].toInt())) to Point2d(b[0].toInt(), b[2].toInt())
        }.forEach { (s, b) ->
            val dist = s.distanceTo(b)
            points.addAll(s.manArea(dist, row))
            points.remove(b)
        }
        return points.size
    }

    fun part2(max: Int): BigInteger {
        input.map {
            val parts = it.split(": closest beacon is at x=")
            val s = parts[0].split("Sensor at ")[1].split("=", ",")
            val b = parts[1].split("=", ",")
            (Point2d(s[1].toInt(), s[3].toInt())) to Point2d(b[0].toInt(), b[2].toInt())
        }.map { (s, b) ->
            (0..max).map { row ->
                val dst = s.distanceTo(b)
                row to s.manRanges(dst, row)
            }.filter { it.second != null }
        }.flatten().groupBy({ it.first }, { it.second!! }).forEach { (row, ranges) ->
            val sorted =
                ranges.sortedBy { it.first } // tried flatten here a couple of times... never stopped rrrrrruningggg..
            var outer = sorted.first().last
            sorted.map { r ->
                if (outer + 1 < r.first)
                    return (outer + 1).toBigInteger() * max.toBigInteger() + row.toBigInteger()
                else outer = maxOf(outer, r.last)
            }
        }

        error("no solution")
    }
}


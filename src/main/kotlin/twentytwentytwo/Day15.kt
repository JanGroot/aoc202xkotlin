package twentytwentytwo

import twentytwentytwo.Structures.Point2d
import java.math.BigInteger

fun main() {
    val input = {}.javaClass.getResource("input-15.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val testInput = {}.javaClass.getResource("input-15-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day15(input)
    val test = Day15(testInput)
    println(test.part1(10))
    println(test.part2(20, 4000000))
    println(day.part1(2000000))
    println(day.part2(4000000, 4000000))
}

class Day15(private val input: List<String>) {
    var sensorToBeacon = input.map {
        val parts = it.split(": closest beacon is at x=")
        val s = parts[0].split("Sensor at ")[1].split("=", ",")
        val b = parts[1].split("=", ",")
        (Point2d(s[1].toInt(), s[3].toInt())) to Point2d(b[0].toInt(), b[2].toInt())
    }

    fun part1(row: Int): Int {
        val possibleBeacons = sensorToBeacon.mapNotNull { (s, b) ->
            val dist = s.distanceTo(b)
            s.manRanges(dist, row)
        }.flatten().toSet()
        // remove any existing sensor or beacon on the row
        val taken = sensorToBeacon.mapNotNull { (s, b) ->
            when {
                s.y == row -> s
                b.y == row -> b
                else -> {
                    null
                }
            }
        }.toSet().count()
        return possibleBeacons.size - taken
    }

    fun part2(max: Int, score: Int): BigInteger {
        sensorToBeacon.map { (s, b) ->
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
                    return (outer + 1).toBigInteger() * score.toBigInteger() + row.toBigInteger()
                else outer = maxOf(outer, r.last)
            }
        }
        error("no solution")
    }
}


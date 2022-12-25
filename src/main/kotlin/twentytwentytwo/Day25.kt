package twentytwentytwo

import kotlin.math.pow

fun main() {
    val input = {}.javaClass.getResource("input-25.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day25(input)
    println(day.part1())
}

class Day25(private val input: List<String>) {
    fun part1(): String {
        return getElvesNumber(input.sumOf { l ->
            getNumber(l)
        })
    }

    private fun getNumber(l: String): Long {
        var total = 0.0
        l.reversed().forEachIndexed { index, c ->
            total += getDecimal(c) * 5.0.pow(index)
        }
        return total.toLong()
    }

    private fun getElvesNumber(n: Long): String {
        var temp = n
        var result = ""
        while (temp > 0) {
            println(temp)
            when (temp % 5) {
                0L -> result += '0'
                1L -> result += '1'
                2L -> result += '2'
                4L -> result += '-'
                3L -> result += '='
            }
            temp += 2
            temp /= 5
        }
        return result.reversed()
    }
    private fun getDecimal(c: Char): Long {
        return when (c) {
            '1' -> 1
            '2' -> 2
            '0' -> 0
            '-' -> -1
            '=' -> -2
            else -> {
                error("oops")
            }
        }
    }
}


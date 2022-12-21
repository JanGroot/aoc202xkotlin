package twentytwentytwo

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.math.BigInteger

fun main() {
    val input = {}.javaClass.getResource("input-21.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val tinput = {}.javaClass.getResource("input-21-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val test = Day21(tinput)
    val day = Day21(input)
    println(test.part1())
    //println(test.part2())
    println(day.part1())
    println(day.part2())
}

class Day21(private val input: List<String>) {
    fun part1(): Long {
        val monkeys = input.map { it.split(": ") }.map { (key, value) -> key to value }.toMap()
        var root = monkeys["root"]!!

        fun recurse(value: String): Long {
             return when {
                value.all { it.isDigit() } -> value.toLong()
                value.contains("*") -> {
                    val (a,b) = value.split(" * ")
                    return recurse(monkeys[a]!!) * recurse(monkeys[b]!!)
                }
                value.contains("-") -> {
                    val (a,b) = value.split(" - ")
                    return recurse(monkeys[a]!!) - recurse(monkeys[b]!!)
                }
                value.contains("+") -> {
                    val (a,b) = value.split(" + ")
                    return recurse(monkeys[a]!!) + recurse(monkeys[b]!!)
                }
                value.contains("/") -> {
                    val (a,b) = value.split(" / ")
                    return recurse(monkeys[a]!!) / recurse(monkeys[b]!!)
                }
                 else -> {0}
             }
        }



        return recurse(root) ;
    }

    fun part2(): String {
        val monkeys = input.map { it.split(": ") }.map { (key, value) -> key to value }.toMap().toMutableMap()
        var root = monkeys["root"]!!
        var (x, y) = root.split(" + ")


        fun recurse(value: String): BigInteger {
            return when {
                value.all { it.isDigit() } -> value.toBigInteger()
                value.contains("*") -> {
                    val (a,b) = value.split(" * ")
                    return recurse(monkeys[a]!!) * recurse(monkeys[b]!!)
                }
                value.contains("-") -> {
                    val (a,b) = value.split(" - ")
                    return recurse(monkeys[a]!!) - recurse(monkeys[b]!!)
                }
                value.contains("+") -> {
                    val (a,b) = value.split(" + ")
                    return recurse(monkeys[a]!!) + recurse(monkeys[b]!!)
                }
                value.contains("/") -> {
                    val (a,b) = value.split(" / ")
                    return recurse(monkeys[a]!!) / recurse(monkeys[b]!!)
                }
                else -> {
                    error("$value")
                }
            }
        }

        fun recurseString(value: String): String {
           return when {
                value.all { it.isDigit() } -> value
                value.contains("*") -> {
                    val (a,b) = value.split(" * ")
                    "(${recurseString(monkeys[a]!!)} * ${recurseString(monkeys[b]!!)})"
                }
                value.contains("-") -> {
                    val (a,b) = value.split(" - ")
                    "(${recurseString(monkeys[a]!!)} - ${recurseString(monkeys[b]!!)})"
                }
                value.contains("+") -> {
                    val (a,b) = value.split(" + ")
                    "(${recurseString(monkeys[a]!!)} + ${recurseString(monkeys[b]!!)})"
                }
                value.contains("/") -> {
                    val (a,b) = value.split(" / ")
                    "(${recurseString(monkeys[a]!!)} / ${recurseString(monkeys[b]!!)})"
                }
                else -> {
                    value
                }
            }
        }
        // what is happening?
        monkeys["humn"] = "1"
        println(recurse(monkeys[x]!!) to recurse(monkeys[y]!!))

        monkeys["humn"] = "2"
        println(recurse(monkeys[x]!!) to recurse(monkeys[y]!!))

        monkeys["humn"] = "123499000006"
        println(recurse(monkeys[x]!!) to recurse(monkeys[y]!!))

        val target = recurse(monkeys[y]!!)
        println(x)
        monkeys["humn"] = "X"
        println(recurseString(monkeys[x]!!))

        monkeys["humn"] = "3293777973422953"
        println(recurse(monkeys[x]!!))

        val first = generateSequence(3000) {
            it + 1
        }.map {
            monkeys["humn"] = "$it"
            recurse(monkeys[x]!!)
        }
            .filter { it == target }.first()

        println(first)



        return monkeys["humn"]!!
    }
}


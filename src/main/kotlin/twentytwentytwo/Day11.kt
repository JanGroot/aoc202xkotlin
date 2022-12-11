package twentytwentytwo

import java.math.BigInteger

fun main() {
    val input = {}.javaClass.getResource("input-11.txt")!!.readText().split("\n\n");
    val day = Day11(input)
    println(day.part1())
    println(day.part2())
}

class Day11(private val input: List<String>) {
    fun part1(): Long {
        val monkeys = input.map { it ->
            val lines = it.lines()
            val items = lines[1].split(": ")[1].split(", ").map { it.toBigInteger() }.toMutableList()
            val op = lines[2].split("= ")[1]
            val test = lines[3].split("by ")[1].toInt()
            val targetTrue = lines[4].split("monkey ")[1].toInt()
            val targetFalse = lines[5].split("monkey ")[1].toInt()
            Monkey(items, op, test, targetTrue, targetFalse)
        }
        repeat(20) {
            monkeys.forEachIndexed { i, it ->
                println("$i : $it")
                while (it.items.isNotEmpty()) {
                    var x = it.items.removeLast()
                    x = it.operate(x).divide(BigInteger.valueOf(3))
                    if (it.test(x)) monkeys[it.targetTrue ].items.add(x) else monkeys[it.targetFalse ].items.add(x)
                }
            }
        }
        val (a, b) = monkeys.sortedByDescending { monkey -> monkey.count }.map { it.count }.also { println(it) }
        return a *b
    }

    fun part2(): Long {

        val monkeys = input.map { it ->
            val lines = it.lines()
            val items = lines[1].split(": ")[1].split(", ").map { it.toBigInteger() }.toMutableList()
            val op = lines[2].split("= ")[1]
            val test = lines[3].split("by ")[1].toInt()
            val targetTrue = lines[4].split("monkey ")[1].toInt()
            val targetFalse = lines[5].split("monkey ")[1].toInt()
            Monkey(items, op, test, targetTrue, targetFalse)
        }
        val modTotal = monkeys.map { it.test }.reduce { acc, i ->  acc * i }.toBigInteger()
        repeat(10_000) {
            monkeys.forEachIndexed { i, it ->
                while (it.items.isNotEmpty()) {
                    var x = it.items.removeLast()
                    x = it.operate(x).mod(modTotal)
                    if (it.test(x)) monkeys[it.targetTrue ].items.add(x) else monkeys[it.targetFalse ].items.add(x)
                }
            }
        }
        val (a, b) = monkeys.sortedByDescending { monkey -> monkey.count }.map { it.count }.also { println(it) }
        return a *b
    }
}

data class Monkey(val items: MutableList<BigInteger>, val op: String, val test: Int, val targetTrue: Int, val targetFalse: Int) {
    var count = 0L
    fun operate(item: BigInteger): BigInteger {
        count++
        return when (op) {
            "old * old" -> item * item
            "old + 2" -> item + BigInteger.valueOf(2)
            "old + 1" -> item + BigInteger.valueOf(1)
            "old + 8" -> item + BigInteger.valueOf(8)
            "old + 4" -> item + BigInteger.valueOf(4)
            "old + 5" -> item + BigInteger.valueOf(5)
            "old * 17" -> item * BigInteger.valueOf(17)
            "old * 13" -> item * BigInteger.valueOf(13)
            else -> error("unknown op")
        }
    }

    fun test(item: BigInteger) = item.mod(test.toBigInteger()) == BigInteger.ZERO




}

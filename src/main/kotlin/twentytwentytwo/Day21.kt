package twentytwentytwo

import twentytwentytwo.Day21.Monkey.Operation
import twentytwentytwo.Day21.Monkey.Value


fun main() {
    val input = {}.javaClass.getResource("input-21.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val tinput = {}.javaClass.getResource("input-21-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val test = Day21(tinput)
    val day = Day21(input)
    println(test.part1())
    println(test.part2())
    println(day.part1())
    println(day.part2())
}

private const val HUMAN = "humn"

class Day21(input: List<String>) {
    private val monkeys =
        input.map { it.split(": ") }.associate { (key, value) -> key to parseMonkey(key, value) }.toMutableMap()

    fun part1(): Long {
        val root = monkeys["root"]!!
        return recurse(root);
    }

    fun part2(): Long {
        val root = (monkeys["root"] as Operation?)!!
        // approx
        var under = 0L
        var over = 5000000000000L
        monkeys[HUMAN] = Value(HUMAN, 1)
        val runLeft = recurse(monkeys[root.left]!!)
        monkeys[HUMAN] = Value(HUMAN, 1000)
        val leftConstant = (runLeft == recurse(monkeys[root.left]!!))
        val leftGrowing = (runLeft < recurse(monkeys[root.left]!!))
        val growing = leftConstant xor leftGrowing
        val target = if (leftConstant) recurse(monkeys[root.left]!!) else recurse(monkeys[root.right]!!)
        val variable = if (leftConstant) monkeys[root.right]!! else monkeys[root.left]!!

        while (true) {
            val guess = (under + over) / 2
            monkeys[HUMAN] = Value(HUMAN, guess)
            val result = recurse(variable)
            if (growing) {
                when {
                    (result < target) -> under = guess + 1
                    (result > target) -> over = guess - 1
                    else -> return guess
                }
            }
            else {
                when {
                    (result > target) -> under = guess + 1
                    (result < target) -> over = guess - 1
                    else -> return guess
                }
            }
        }
    }

    private fun recurse(monkey: Monkey): Long {
        return when (monkey) {
            is Value -> monkey.value
            is Operation -> {
                when (monkey.operand) {
                    "*" -> recurse(monkeys[monkey.left]!!) * recurse(monkeys[monkey.right]!!)
                    "+" -> recurse(monkeys[monkey.left]!!) + recurse(monkeys[monkey.right]!!)
                    "-" -> recurse(monkeys[monkey.left]!!) - recurse(monkeys[monkey.right]!!)
                    "/" -> recurse(monkeys[monkey.left]!!) / recurse(monkeys[monkey.right]!!)
                    else -> {
                        error("operation not found")
                    }
                }
            }
        }
    }


    sealed class Monkey(val name: String) {
        class Value(name: String, val value: Long) : Monkey(name)
        class Operation(name: String, val left: String, val operand: String, val right: String) : Monkey(name)
    }

    private fun parseMonkey(key: String, value: String): Monkey {
        return if (value.all { it.isDigit() }) Value(key, value.toLong())
        else {
            val split = value.split(" ")
            Operation(key, split[0], split[1], split[2])
        }
    }
}


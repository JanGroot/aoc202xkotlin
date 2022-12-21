package twentytwentytwo


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
                else -> {
                    error("$value")
                }
            }
        }
        // approx
        var under = 0L
        var over = 5000000000000L
        val target = recurse(monkeys[y]!!)
        while (true) {
            val guess = (under + over) / 2
            monkeys["humn"] = "$guess"
            val result = recurse(monkeys[x]!!)
            when {
                (result > target) -> under = guess + 1
                (result < target) -> over = guess - 1
                else -> return monkeys["humn"]!!
            }
        }
    }
}


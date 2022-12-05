package twentytwentytwo

fun main() {
    val (crates, moves) = {}.javaClass.getResource("input-5.txt")!!.readText().split("\n\n")
    val day = Day5(crates, moves)
    println(day.part1())
    println(day.part2())
}

class Day5(private val crates: String, private val moves: String) {

    fun part1(): String {
        val stacks = getStacks()
        getMoves().forEach {
            val (number, from, to) = it
            for (i in 1..number) {
                stacks[to]!!.add(stacks[from]!!.removeLast())
            }
        }
        return stacks.values.map { it.last() }.reduce { code, s -> code + s }
    }

    fun part2(): String {
        val stacks = getStacks()
        getMoves().forEach {
            val (number, from, to) = it
            val first = stacks[from]!!.take(stacks[from]!!.size - number)
            val second = stacks[from]!!.takeLast(number)
            stacks[from] = first.toMutableList()
            stacks[to]!!.addAll(second)
        }
        return stacks.values.map { it.last() }.reduce { code, s -> code + s }
    }

    private fun getStacks(): HashMap<Int, MutableList<String>> {
        val stackedMap = crates.lines()
            .filter { it.contains("[") }
            .map { s ->
                s.windowed(4, 4, true)
                    .map { s -> s.trim() }
                    .mapIndexed { index, value -> index + 1 to value }
                    .filter { it.second.isNotEmpty() }
            }.flatten()
            .groupByTo(HashMap(), keySelector = { p -> p.first }, valueTransform = { p -> p.second[1].toString() })
        stackedMap.values.forEach { it.reverse() }
        return stackedMap
    }

    private fun getMoves(): List<List<Int>> {
        val numRegex = "\\d+".toRegex()
        return moves.split("\n")
            .map { s ->
                numRegex
                    .findAll(s)
                    .map { r -> r.value.trim().toInt() }
                    .toList()
            }
            .filter { it.isNotEmpty() }
    }
}


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
            (number, from, to)  ->
            repeat(number) {
                stacks[to]!!.add(stacks[from]!!.removeLast())
            }
        }
        return stacks.values.map { it.last() }.reduce { code, s -> code + s }
    }

    fun part2(): String {
        val stacks = getStacks()
        getMoves().forEach { (number, from, to) ->
            val (first, second) = stacks[from]!!.split(number)
            stacks[from] = first
            stacks[to]!!.addAll(second)
        }
        return stacks.values.map { it.last() }.reduce { code, s -> code + s }
    }

    private fun getStacks(): HashMap<Int, MutableList<String>> {
        val stackedMap = crates.lines()
            .filter { it.contains("[") }
            .map { s ->
                s.windowed(4, 4, true)
                    .map { s -> s.filter { it.isUpperCase() } }
                    .mapIndexed { index, value -> index + 1 to value }
                    .filter { it.second.isNotEmpty() }
            }.flatten()
            .groupByTo(HashMap(), keySelector = { p -> p.first }, valueTransform = { p -> p.second })
        stackedMap.values.forEach { it.reverse() }
        return stackedMap
    }

    private fun getMoves(): List<List<Int>> {
        return moves.split("\n").filter { it.isNotEmpty() }
            .map { s ->
                s.split(" ").filter { s1 -> s1.any { it.isDigit() } }
                    .filter { it.isNotEmpty() }.map { it.toInt() }
            }

    }
    private fun MutableList<String>.split(number: Int): Pair<MutableList<String>, MutableList<String>> {
        return Pair(take(size - number).toMutableList(), takeLast(number).toMutableList())
    }

}




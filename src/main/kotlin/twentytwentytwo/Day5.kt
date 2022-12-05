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
        getMoves().forEach { (number, from, to) ->
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

    private fun getStacks(): MutableMap<Int, MutableList<String>> {
        val indexes = 1..33 step 4
        val lines = crates.linesFiltered{ it.contains("[") }.reversed()
        val listOfStacks = indexes.map { i -> lines.map { it[i] }.filter { it.isUpperCase() }.map { it.toString() } }
        return listOfStacks.mapIndexed { i, v -> i + 1 to v.toMutableList() }.toMap(HashMap())
    }

    /**
     * transforms "'move 8 from 3 to 2'\n 'move 8 from 3 to 2'" to [[8,3,2],[8,3,2]]
     */
    private fun getMoves(): List<List<Int>> {
        return moves.linesFiltered { it.isNotEmpty() }
            .map { s ->
                s.split(" ").filter { s1 -> s1.any { it.isDigit() } }
                    .filter { it.isNotEmpty() }.map { it.toInt() }
            }

    }

    private fun MutableList<String>.split(number: Int): Pair<MutableList<String>, MutableList<String>> {
        return Pair(take(size - number).toMutableList(), takeLast(number).toMutableList())
    }

}




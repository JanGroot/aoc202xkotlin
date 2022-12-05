package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-5.txt")!!.readText().split("\n").filter { it.isNotEmpty() };
    val day = Day5(input)
    println(day.part1())
    println(day.part2())
}

class Day5(private val input: List<String>) {
    fun part1(): String {
        val stacks = getStacks()
        input.forEach {
            val move = it.split(",").map{s -> s.toInt()}
            for ( i in 1..move[0]) {
                stacks[move[2]]!!.add(stacks[move[1]]!!.removeLast())
            }
        }
        return stacks.values.map { it.last() }.reduce { code, s -> code + s  }
    }

    fun part2(): String {
        val stacks = getStacks()
        input.forEach {
            val move = it.split(",").map{s -> s.toInt()}
            val from = stacks[move[1]]
            val first = from!!.take(from.size  - move[0])
            val second = from.takeLast(move[0])
            stacks[move[1]] = first.toMutableList()
            stacks[move[2]]!!.addAll(second)
        }
        return stacks.values.map { it.last() }.reduce { code, s -> code + s  }
    }

    private fun getStacks() = mutableMapOf(
        1 to arrayOf("H", "B", "V", "W", "N", "M", "L", "P").toMutableList(),
        2 to arrayOf("M", "Q", "H").toMutableList(),
        3 to arrayOf("N", "D", "B", "G", "F", "Q", "M", "L").toMutableList(),
        4 to arrayOf("Z", "T", "F", "Q", "M", "W", "G").toMutableList(),
        5 to arrayOf("M", "T", "H", "P").toMutableList(),
        6 to arrayOf("C", "B", "M", "J", "D", "H", "G", "T").toMutableList(),
        7 to arrayOf("M", "N", "B", "F", "V", "R").toMutableList(),
        8 to arrayOf("P", "L", "H", "M", "R", "G", "S").toMutableList(),
        9 to arrayOf("P", "D", "B", "C", "N").toMutableList()
    )
}


package twentytwentytwo


fun main() {
    val day3 = Day3({}.javaClass.getResource("input-3.txt")!!.readText().split("\n").filter { it.isNotEmpty() })
    println(day3.part1())
    println(day3.part2())
}

class Day3(private val input: List<String>) {
    private val charIntMap = (('a'..'z') + ('A'..'Z')).withIndex().associate { it.value to it.index + 1 }
    fun part1(): Int {
        return input.sumOf {
            val compartments = it.divide()
            charIntMap[compartments.first.toSet().intersect(compartments.second.toSet()).first()]!!
        }
    }

    fun part2(): Int {
        return input.windowed(3, 3).sumOf {
            charIntMap[it[0].toSet().intersect(it[1].toSet()).intersect(it[2].toSet()).first()]!!
        }
    }

    private fun String.divide() =
        Pair(this.substring(0, (lastIndex / 2) +1), this.substring((lastIndex /2) + 1, this.length))

}
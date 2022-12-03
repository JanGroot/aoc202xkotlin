package twentytwentytwo


fun main() {
    val day3 = Day3({}.javaClass.getResource("input-3.txt")!!.readText().split("\n").filter { it.isNotEmpty() })
    println(day3.part1())
    println(day3.part2())
}

class Day3(private val input: List<String>) {
    private val charIntMap = (('a'..'z') + ('A'..'Z')).withIndex().associate { it.value to it.index + 1 }
    fun part1(): Int {
        return input.map {
            val compartmentA = it.substring(0, it.midpoint() + 1)
            val compartmentB = it.substring(it.midpoint() + 1, it.length)
            val intersect = compartmentA.toCharArray().intersect(compartmentB.toCharArray().asIterable().toSet())
            charIntMap[intersect.first()]!!
        }.toList().sum()
    }

    fun part2(): Int {
        return input.windowed(3, 3).map {
            charIntMap[it[0].toCharArray().intersect(it[1].toCharArray().toSet()).intersect(it[2].toSet()).first()]!!
        }.toList().sum()
    }

    private fun String.midpoint(): Int =
        lastIndex / 2
}
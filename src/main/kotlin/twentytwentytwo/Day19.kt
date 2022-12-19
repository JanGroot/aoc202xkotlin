package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-19.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val test = {}.javaClass.getResource("input-19-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    //val day = Day19(input)
    val testDay = Day19(test)
    println(testDay.part1())
    println(testDay.part2())
    //println(day.part1())
    //println(day.part2())
}

class Day19(private val input: List<String>) {
    val blueprints  = input.mapIndexed { index, s ->
        val id = index + 1
        val split = s.split("robot costs ", " ore. Each ", " ore and ", " clay. Each geode ", " obsidian.")
        println(split)
        val ore = Robot("ore", mutableListOf("ore" to split[1].toInt()))
        val clay = Robot("clay", mutableListOf("ore" to split[3].toInt()))
        val obsidian = Robot("obsidian", mutableListOf("ore" to split[5].toInt(), "clay" to split[6].toInt()))
        val geode = Robot("geode", mutableListOf("ore" to split[8].toInt(), "obsidian" to split[9].toInt()))
        Blueprint(id, listOf(ore,clay,obsidian,geode))
    }.also { println(it) }

    fun part1(): Int {
        error("not found")
    }

    fun part2(): Int {
        error("not found")
    }
}

data class Blueprint(val id: Int,val robots: List<Robot>)
data class Robot(val type: String, val costs: List<Cost>)
typealias Cost = Pair<String,Int>


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
    val blueprints = input.mapIndexed { index, s ->
        val id = index + 1
        val split = s.split("robot costs ", " ore. Each ", " ore and ", " clay. Each geode ", " obsidian.")
        println(split)
        val ore = Robot("ore", mapOf("ore" to split[1].toInt()))
        val clay = Robot("clay", mapOf("ore" to split[3].toInt()))
        val obsidian = Robot("obsidian", mapOf("ore" to split[5].toInt(), "clay" to split[6].toInt()))
        val geode = Robot("geode", mapOf("ore" to split[8].toInt(), "obsidian" to split[9].toInt()))
        Blueprint(id, mapOf(ore, clay, obsidian, geode))
    }.also { println(it) }

    fun part1(): Int {
        error("not found")
    }

    fun part2(): Int {
        error("not found")
    }
}

class Blueprint(val id: Int, val robots: Map<String, Map<String, Int>>) {

    val memoize = mutableMapOf<State, Int>()
    val max = 0

    fun maxGeodes() {

    }

    fun step(state: State): Int {
        return memoize.computeIfAbsent(state) {
            val (comp1, step) = state
            val (producers, inventory) = comp1
            if (step > 24) return@computeIfAbsent inventory["geode"] ?: 0
            var produced = produce(producers, inventory)
            step((producers to inventory) to step + 1)
            buyRobots(state)

        }
    }

    private fun buyRobots(state: State): Int {
        val producers = state.getMutableProducers()
        val inventory = state.getMutableConsumers()
        return when {
            inventory.getOrDefault("ore", 0) >= robots["ore"]!!.getOrDefault("ore", 0) -> {
                inventory["ore"] = inventory["ore"]!! - robots["ore"]!!.getOrDefault("ore", 0)
                return 0


            }

            else -> {0}
        }

    }

    private fun produce(producers: Map<String, Int>, inventory: Map<String, Int>): Map<String, Int> = producers.map {
        it.key to inventory.getOrDefault(it.key, 0) + it.value
    }.toMap()


}

fun State.getMutableProducers() = first.first.toMutableMap()
fun State.getMutableConsumers() = first.second.toMutableMap()

fun State.getStep() = second

typealias Robot = Pair<String, Map<String, Int>>
typealias State = Pair<Pair<Map<String, Int>, Map<String, Int>>, Int>


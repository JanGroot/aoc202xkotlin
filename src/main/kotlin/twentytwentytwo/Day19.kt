package twentytwentytwo

import java.lang.Integer.max

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
    }

    fun part1(): Int {
        return blueprints.maxOf { it.maxGeodes() }
        error("not found")
    }

    fun part2(): Int {
        error("not found")
    }
}

class Blueprint(val id: Int, val robots: Map<String, Map<String, Int>>) {

    val cache = mutableSetOf<State>()
    var geodeMax = 0

    fun maxGeodes(): Int {
        step((mapOf("ore" to 1) to (emptyMap<String, Int>())) to 1)
        return maxGeodes();
    }

    fun step(state: State) {
        if (state.getStep() == 24) return
        if (state !in cache) {
            geodeMax = max(geodeMax, state.inventory()["geode"] ?: 0)
            cache.add(state)
            val produced = produce(state.producers(), state.inventory())
            buyRobots((state.producers() to produced) to state.getStep())
            step((state.producers() to produced) to state.getStep() + 1)

        } else {
            geodeMax = max(geodeMax, state.inventory()["geode"] ?: 0)
        }
    }

    private fun buyRobots(state: State) {
        robots.keys.forEach {
            if (canBuyRobot(it, state.inventory()) && shouldBuyRobot(it, state.producers()[it] ?: 0)) {
                step(buyRobot(it, state))
            }
        }
    }

    private fun buyRobot(robot: String, state: State): State {
        val costs = robots[robot]!!.map {
            it.key to state.inventory()[it.key]!! - it.value
        }
        val robots = (state.producers()[robot] ?: 0) + 1
        return ((state.producers() + (robot to robots) to state.inventory() + costs) to state.getStep() + 1)
    }

    private fun canBuyRobot(robot: String, inventory: Map<String, Int>): Boolean =
        robots[robot]!!.all {
            ( inventory[it.key] ?: 0 )>= it.value
        }

    private fun shouldBuyRobot(robot: String, number: Int) =
        (robot == "geode") || robots.filterValues { it.containsKey(robot) }.values.all { it[robot]!! > number }


    private fun produce(producers: Map<String, Int>, inventory: Map<String, Int>): Map<String, Int> = producers.map {
        it.key to inventory.getOrDefault(it.key, 0) + it.value
    }.toMap()

}

fun State.producers() = first.first
fun State.inventory() = first.second

fun State.getStep() = second

typealias Robot = Pair<String, Map<String, Int>>
typealias State = Pair<Pair<Map<String, Int>, Map<String, Int>>, Int>


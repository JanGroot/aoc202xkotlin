package twentytwentytwo

import java.lang.Integer.max
import java.util.Collections.max as colMax


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
        val oreLimit = colMax(
            listOf(
                ore.second["ore"] ?: 0, clay.second["ore"] ?: 0, obsidian.second["ore"] ?: 0, geode.second["ore"] ?: 0
            )
        )
        val clayLimit = colMax(
            listOf(
                ore.second["clay"] ?: 0,
                clay.second["clay"] ?: 0,
                obsidian.second["clay"] ?: 0,
                geode.second["clay"] ?: 0
            )
        )
        val obsidianLimit = colMax(
            listOf(
                ore.second["obsidian"] ?: 0,
                clay.second["obsidian"] ?: 0,
                obsidian.second["obsidian"] ?: 0,
                geode.second["obsidian"] ?: 0
            )
        )
        Blueprint(id, mapOf(ore, clay, obsidian, geode), oreLimit, clayLimit, obsidianLimit)
    }

    fun part1(): Int {
        return blueprints.maxOf { it.maxGeodes() }
    }

    fun part2(): Int {
        error("not found")
    }
}

class Blueprint(
    val id: Int,
    private val robots: Map<String, Map<String, Int>>,
    private val oreLimit: Int,
    private val clayLimit: Int,
    private val obsidianLimit: Int
) {

    val cache = mutableSetOf<State>()

    fun maxGeodes(): Int {
        return step(State(mapOf("ore" to 1), emptyMap(), 1))
    }

    fun step(state: State): Int {
        if (state.step == 24) return 0
        return if (state !in cache) {
            cache.add(state)
            val produced = produce(state.producers, state.inventory)
            val max = step(State(state.producers, produced, state.step + 1))
            max(max, buyRobots(State(state.producers, produced, state.step)))
        } else {
            state.inventory["geode"] ?: 0
        }
    }

    private fun buyRobots(state: State): Int {
        var max = 0
        robots.keys.forEach {
            if (canBuyRobot(it, state.inventory) && shouldBuyRobot(it, state.producers[it] ?: 0)) {
                max = max(max, step(buyRobot(it, state)))
            }
        }
        return max
    }

    private fun buyRobot(robot: String, state: State): State {
        val costs = robots[robot]!!.map {
            it.key to state.inventory[it.key]!! - it.value
        }
        val robots = (state.producers[robot] ?: 0) + 1
        return State(state.producers + (robot to robots), state.inventory + costs, state.step + 1)
    }

    private fun canBuyRobot(robot: String, inventory: Map<String, Int>): Boolean = robots[robot]!!.all {
        (inventory[it.key] ?: 0) >= it.value
    }

    private fun shouldBuyRobot(robot: String, number: Int): Boolean = when (robot) {
        "geode" -> true
        "ore" -> number < oreLimit
        "obsidian" -> number < obsidianLimit
        "clay" -> number < clayLimit
        else -> {
            error("oops")
        }
    }


    private fun produce(
        producers: Map<String, Int>, inventory: Map<String, Int>
    ): Map<String, Int> = producers.map {
        it.key to inventory.getOrDefault(it.key, 0) + it.value
    }.toMap()

}

typealias Robot = Pair<String, Map<String, Int>>

data class State(val producers: Map<String, Int>, val inventory: Map<String, Int>, val step: Int)


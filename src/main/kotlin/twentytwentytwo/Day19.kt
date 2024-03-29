package twentytwentytwo

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


suspend fun main() {
    val input = {}.javaClass.getResource("input-19.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val test = {}.javaClass.getResource("input-19-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day19(input)
    val testDay = Day19(test)
    //println(testDay.part1())
    //println(testDay.part2())
    //println(day.part1())
    println(day.part2())
}

class Day19(private val input: List<String>) {
    val blueprints = input.mapIndexed { index, s ->
        val id = index + 1
        val split = s.split("robot costs ", " ore. Each ", " ore and ", " clay. Each geode ", " obsidian.")
        println(split)
        val oreLimit = maxOf(split[1].toInt(), split[5].toInt(), split[3].toInt())
        Blueprint(
            id,
            split[1].toInt(),
            split[3].toInt(),
            split[5].toInt(),
            split[6].toInt(),
            split[8].toInt(),
            split[9].toInt(),
            oreLimit,
            split[6].toInt(),
            split[9].toInt()
        )
    }

    suspend fun part1(): Int {
        return blueprints.pmap { it.id * it.maxGeodes(State(oreRobot = 1, step = 24)) }.sum()
    }

    suspend fun part2(): Int {
        return blueprints.take(3).pmap{it.maxGeodes(State(oreRobot = 1, step = 32)).also { println(it) } }
            .reduce(Int::times)
    }

    suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
        map { async { f(it) } }.awaitAll()
    }
}

class Blueprint(
    val id: Int,
    private val oreCost: Int,
    private val clayCostOre: Int,
    private val obsidianCostOre: Int,
    private val obsidianCostClay: Int,
    private val geodeCostOre: Int,
    private val geodeCostObsidian: Int,
    private val oreLimit: Int,
    private val clayLimit: Int,
    private val obsidianLimit: Int
) {

    fun maxGeodes(state: State): Int {
        var max = 0
        var cache = mutableSetOf<State>()
        var queue = Structures.ArrayListQueue<State>()
        queue.enqueue(state)
        while (!queue.isEmpty) {
                var current = queue.dequeue()!!
                if (!cache.contains(current)) {
                    cache.add(current)
                    if (current.step == 0) {
                        max = maxOf(max, current.geode)
                    } else {
                        val next = current.produce()
                        if (current.canBuy("geode")) {
                            queue.enqueue(next.buy("geode"))
                            queue.enqueue(next)
                        } else
                            if (current.canBuy("obsidian")) {
                                queue.enqueue(next.buy("obsidian"))
                            } else {
                                if (current.canBuy("ore")) {
                                    queue.enqueue(next.buy("ore"))
                                }
                                if (current.canBuy("clay")) {
                                    queue.enqueue(next.buy("clay"))
                                }
                                queue.enqueue(next)
                            }
                    }
                }
            }
        return max
    }

    private fun State.produce(): State {
        return State(
            ore + oreRobot,
            clay + clayRobot,
            obsidian + obsidianRobot,
            geode + geodeRobot,
            oreRobot,
            clayRobot,
            obsidianRobot,
            geodeRobot,
            step - 1
        )
    }


    private fun State.canBuy(robot: String): Boolean {
        return when (robot) {
            "geode" -> ore >= geodeCostOre && obsidian >= geodeCostObsidian
            "obsidian" -> ore >= obsidianCostOre && clay >= obsidianCostClay && obsidianRobot < obsidianLimit
            "clay" -> ore >= clayCostOre && clayRobot < clayLimit
            "ore" -> ore >= oreCost && oreRobot < oreLimit
            else -> {
                error("oops")
            }
        }
    }

    private fun State.buy(robot: String): State {
        return when (robot) {
            "geode" -> State(
                ore - geodeCostOre,
                clay,
                obsidian - geodeCostObsidian,
                geode,
                oreRobot,
                clayRobot,
                obsidianRobot,
                geodeRobot + 1,
                step
            )

            "obsidian" -> State(
                ore - obsidianCostOre,
                clay - obsidianCostClay,
                obsidian,
                geode,
                oreRobot,
                clayRobot,
                obsidianRobot + 1,
                geodeRobot,
                step
            )

            "clay" -> State(
                ore - clayCostOre, clay, obsidian, geode, oreRobot, clayRobot + 1, obsidianRobot, geodeRobot, step
            )

            "ore" -> State(
                ore - oreCost, clay, obsidian, geode, oreRobot + 1, clayRobot, obsidianRobot, geodeRobot, step
            )

            else -> {
                error("oops")
            }
        }
    }

}

data class State(
    val ore: Int = 0,
    val clay: Int = 0,
    val obsidian: Int = 0,
    val geode: Int = 0,
    val oreRobot: Int = 0,
    val clayRobot: Int = 0,
    val obsidianRobot: Int = 0,
    val geodeRobot: Int = 0,
    val step: Int
)



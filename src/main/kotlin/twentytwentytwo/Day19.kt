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

    fun part1(): Int {
        return blueprints.maxOf { it.maxGeodes(State(oreRobot = 1, step = 24)) }
    }

    fun part2(): Int {
        error("not found")
    }
}

class Blueprint(
    val id: Int,
    val oreCost: Int,
    val clayCostOre: Int,
    val obsidianCostOre: Int,
    val obsidianCostClay: Int,
    val geodeCostOre: Int,
    val geodeCostObsidian: Int,
    private val oreLimit: Int,
    private val clayLimit: Int,
    private val obsidianLimit: Int
) {
    val cache = mutableMapOf<State, Int>()


    fun maxGeodes(state: State): Int {
        if (state.step == 0) return -1
        if (cache.containsKey(state)) return cache[state]!!
        val current = state.produce()
        var max = maxGeodes(current)
        arrayOf("obsidian", "clay", "ore", "geode").forEach {
            if (current.canBuy(it) && current.shouldBuy(it)) {
                max = maxOf(max, maxGeodes(current.buy(it)))
            }
        }
        cache[state] = maxOf(max, current.geode)
        return max;
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

    private fun State.shouldBuy(robot: String): Boolean {
        return when (robot) {
            "geode" -> true
            "ore" -> oreRobot < oreLimit
            "obsidian" -> obsidianRobot < obsidianLimit
            "clay" -> clayRobot < clayLimit
            else -> {
                error("oops")
            }
        }
    }


    private fun State.canBuy(robot: String): Boolean {
        return when (robot) {
            "geode" -> ore >= geodeCostOre && obsidian >= geodeCostObsidian
            "obsidian" -> ore >= obsidianCostOre && clay >= obsidianCostClay
            "clay" -> ore >= clayCostOre
            "ore" -> ore >= oreCost
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
                ore - clayCostOre,
                clay,
                obsidian,
                geode,
                oreRobot,
                clayRobot + 1,
                obsidianRobot,
                geodeRobot,
                step
            )

            "ore" -> State(
                ore - oreCost,
                clay,
                obsidian,
                geode,
                oreRobot + 1,
                clayRobot,
                obsidianRobot,
                geodeRobot,
                step
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



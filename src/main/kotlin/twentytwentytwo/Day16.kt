package twentytwentytwo

import java.lang.Integer.max

fun main() {
    val input = {}.javaClass.getResource("input-16.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val testInput = {}.javaClass.getResource("input-16-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val test = Day16(testInput)
    val day = Day16(input)
    println(test.part1())
    println(test.part2())
    println(day.part1())
    println(day.part2())
}

class Day16(input: List<String>) {

    private val valves: Map<String, Valve>
    private val paths: Map<Valve, Map<Valve, Int>>

    init {
        valves = input.associate {
            val name = it.substringAfter("Valve ").substringBefore(" has")
            val rate = it.substringAfter("=").substringBefore(";").toInt()
            name to Valve(name, rate = rate)
        }
        paths = Graph(input.associate {
            val name = it.substringAfter("Valve ").substringBefore(" has")
            val edges = it.substringAfter("to valve").split(", ")
            valves[name]!! to edges.associate { valves[it.filter { it.isUpperCase() }]!! to 1 }
        }).findAllShortestPath().map { (k, v) -> k to v.filter { it.key.rate > 0 } }.toMap()
    }

    fun part1(): Int {
        val volcano = Volcano(paths, valves["AA"]!!)
        volcano.findHighestDeltaP()
        return volcano.pressure
    }

    fun part2(): Int {
        val volcano = Volcano(paths, valves["AA"]!!, 26)
        volcano.findHighestDeltaP(secondPass = true)
        return volcano.pressure
    }
}

class Volcano(private val paths: Map<Valve, Map<Valve, Int>>, private val start: Valve, private val timeToBlow: Int = 30) {
    var pressure = 0
    fun findHighestDeltaP(from: Valve = start, cache: Set<Valve> = mutableSetOf(), deltaT: Int = 0, deltaP: Int = 0, secondPass: Boolean = false) {
        pressure = max(pressure, deltaP)
        paths[from]!!
            .filter { (destination, distance) ->
            (!cache.contains(destination) && deltaT + distance + 1 < timeToBlow) }
            .forEach() { (destination, distance) ->
                findHighestDeltaP(
                    from = destination,
                    cache = cache + destination,
                    deltaT = deltaT + 1 + distance,
                    deltaP = deltaP + (timeToBlow - deltaT - distance - 1) * destination.rate,
                    secondPass
                )
            }
        if (secondPass) findHighestDeltaP(start, cache, deltaT = 0, deltaP)
    }
}

data class Valve(val name: String, var rate: Int = 0)

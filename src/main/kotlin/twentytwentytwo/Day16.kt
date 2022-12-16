package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-16.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val testInput = {}.javaClass.getResource("input-16-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val test = Day16(testInput)
    val day = Day16(input)
    println(test.part1())
  //  println(test.part2())
    println(day.part1())
   // println(day.part2())
}

class Day16(input: List<String>) {

    private val graph: Graph<Valve>
    private val valves: Map<String, Valve>
    private val valveMapMap:  Map<Valve, Map<Valve, Int>>
    private val paths: Map<Valve, Map<Valve, Int>>

    init {
        valves = input.associate {
            val name = it.substringAfter("Valve ").substringBefore(" has")
            val rate = it.substringAfter("=").substringBefore(";").toInt()
            name to Valve(name, rate = rate)
        }
        valveMapMap = input.associate {
            val name = it.substringAfter("Valve ").substringBefore(" has")
            val edges = it.substringAfter("to valve").split(", ")
            valves[name]!! to edges.associate { valves[it.filter { it.isUpperCase() }]!! to 1 }
        }
        graph = Graph(valveMapMap)
        paths = graph.findAllShortestPath()
    }
    fun part1(): Int {
        return 0;
    }

    fun part2(): Int {
        error("not found")
    }
}

data class Valve(val name: String, var rate: Int = 0)

package twentytwentytwo

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

    private val graphList = AdjacencyList<Valve>()
    private val valves: Map<String, Vertex<Valve>>

    init {
        valves = input.map {
            val name = it.substringAfter("Valve ").substringBefore(" has")
            name to graphList.createVertex(Valve(name))
        }.toMap()
        input.forEach {
            val name = it.substringAfter("Valve ").substringBefore(" has")
            val source = valves[name]!!
            val weight = it.substringAfter("=").substringBefore(";").toInt()
            val edges = it.substringAfter("to valve").split(", ")
            edges.forEach {
                val destination = valves[it.filter { it.isUpperCase() }]!!
                graphList.addDirectedEdge(source, destination, weight)
            }
        }
    }
    fun part1(): Int {
        val start = valves["AA"]
        (1 until 31).map {

        }

        error("not found")
    }

    fun part2(): Int {
        error("not found")
    }
}

data class Valve(val name: String, var open: Boolean = false)

data class Vertex<T>(val index: Int, val data: T)
data class Edge<T>(val source: Vertex<T>, val destination: Vertex<T>, val weight: Int? = null)
class AdjacencyList<T> {
    private val adjacencyMap = mutableMapOf<Vertex<T>, ArrayList<Edge<T>>>()
    fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(adjacencyMap.count(), data)
        adjacencyMap[vertex] = arrayListOf()
        return vertex
    }

    fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Int? = 0) {
        val edge = Edge(source, destination, weight)
        adjacencyMap[source]?.add(edge)
    }

    override fun toString(): String {
        return buildString {
            adjacencyMap.forEach { (vertex, edges) ->
                val edgeString = edges.joinToString { it.destination.data.toString() }
                append("${vertex.data} -> [$edgeString]\n")
            }
        }
    }

    fun getVertex(val name: String)
}
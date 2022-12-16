package twentytwentytwo

class Graph<T>(private val weightedPaths: Map<T, Map<T, Int>>) {

    //dijkstra
    fun findShortestPath(start: T, end: T): Int {
        if (start == end) return 0
        val paths = recurseFindShortestPath(NodePaths(start, end)).paths
        return paths.getValue(end)
    }

    private tailrec fun recurseFindShortestPath(nodePaths: NodePaths<T>): NodePaths<T> {
        return if (nodePaths.isFinished()) nodePaths
        else {
            val nextNode = nodePaths.nextNode(weightedPaths)
            recurseFindShortestPath(nextNode)
        }
    }

    //https://en.wikipedia.org/wiki/Floyd–Warshall_algorithm
    fun findAllShortestPath() =
        weightedPaths.keys.associateWith { k -> weightedPaths.keys.associateWith { findShortestPath(k, it) } }

}

data class NodePaths<T>(private val node: T, private  val end: T, val paths: Map<T, Int> = emptyMap()) {

    private fun updatePath(entry: Map.Entry<T, Int>): NodePaths<T> {
        val currentDistance = paths.getOrDefault(entry.key, Integer.MAX_VALUE)
        val newDistance = entry.value + paths.getOrDefault(node, 0)
        return if (newDistance < currentDistance)
            this + (entry.key to newDistance)
        else
            this
    }

    private fun updatePaths(weightedPaths: Map<T, Map<T, Int>>) = (weightedPaths[node]
        ?.map { updatePath(it) }
        ?.fold(copy()) { acc, item -> acc + item } ?: copy()) - node

    fun nextNode(weightedPaths: Map<T, Map<T, Int>>): NodePaths<T> {
        val updatedPaths = updatePaths(weightedPaths)
        val nextNode = updatedPaths.paths.minBy { it.value }.key
        return updatedPaths.copy(node = nextNode)
    }

    fun isFinished() = node == end

    operator fun plus(other: NodePaths<T>) = copy(paths = paths + other.paths)

    operator fun plus(pair: Pair<T, Int>) = copy(paths = paths + pair)

    operator fun minus(node: T)= copy(paths = paths - node)
}
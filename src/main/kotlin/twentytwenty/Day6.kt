fun main() {
    val groups = {}.javaClass.getResource("twentytwenty/input6.txt").readText().split("\n\n")
    println(getUniqueAnswersPerGroup(groups).map { it.size }.sum())
    println(getCommonAnswersPerGroup(groups).map { it.size }.sum())
}

fun getUniqueAnswersPerGroup(groups: List<String>) = groups.map { group -> group.lines().joinToString("").toSet() }

fun getCommonAnswersPerGroup(groups: List<String>) =
    groups.map { group -> group.lines().map { it.toSet() }.reduce { a, b -> a intersect b } }





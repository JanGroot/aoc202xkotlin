
fun main() {
    val input = {}.javaClass.getResource("twentytwenty/input10.txt").readText().lines().map { it.toInt() }.sorted()
    val counts = mutableMapOf<Int, Int>();
    input.reduce { a, b ->
        var c = counts.getOrDefault(b - a, 1)
        counts.put(b - a, ++c)
        b
    }
    println(counts[1]!! * counts[3]!!)
    val part2 = input.fold(mapOf(0 to 1L),
        { acc, i -> with(acc) { plus(i to (i - 3 until i).map { this.getOrDefault(it, 0) }.sum()) } })[input.last()]
    println(part2)
}
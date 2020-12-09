fun main() {
    val input = {}.javaClass.getResource("input9.txt").readText().lines().map { it.toLong() }
    val key = input.findElementWhichHasNoSumInPreamble(25)
    println("$key")
    println("${input.findRangeThatSumsTo(key).sumOfMinAndMax()}")
}

fun List<Long>.findElementWhichHasNoSumInPreamble(preamble: Int): Long {
    for (i in preamble until size) if (this.subList(i - preamble, i).hasNoPairThatSumsTo(this[i])) return this[i]
    error("not found")
}

fun List<Long>.hasNoPairThatSumsTo(number: Long) = find { (number - it) in this && it != number } == null

fun List<Long>.findRangeThatSumsTo(number: Long): List<Long> {
    for (i in indices) for (j in indices.reversed()) {
        if (j < i) break
        val sub = subList(i, j)
        if (sub.sum() == number) return sub
    }
    error("not found")
}

fun List<Long>.sumOfMinAndMax() = minOrNull()!! + maxOrNull()!!

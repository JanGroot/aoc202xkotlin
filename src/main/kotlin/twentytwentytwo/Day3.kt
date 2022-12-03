package twentytwentytwo


fun main() {
    val input = {}.javaClass.getResource("input-3.txt")!!.readText().split("\n")
    println(dayThreePartOne(input))
    println(dayThreePartTwo(input))
}

fun dayThreePartOne(input: List<String>): Int {
    val range = ('a'..'z') + ('A'..'Z')
    val charIntMap = range.withIndex().associate { it.value to it.index + 1 }
    println(charIntMap)
    return input.map {
        val compartmentA = it.substring(0, it.midpoint() + 1)
        val compartmentB = it.substring(it.midpoint() +1 , it.length)
        val intersect = compartmentA.toCharArray().intersect(compartmentB.toCharArray().asIterable().toSet())
        charIntMap[intersect.first()]!!
    }.toList().sum()
}

fun dayThreePartTwo(input: List<String>): Int {
    val range = ('a'..'z') + ('A'..'Z')
    val charIntMap = range.withIndex().associate { it.value to it.index + 1 }
    return input.windowed(3,3).map {
        charIntMap[it[0].toCharArray().intersect(it[1].toCharArray().toSet()).intersect(it[2].toSet()).first()]!!
    }.toList().sum()
}

fun String.midpoint(): Int =
    lastIndex / 2
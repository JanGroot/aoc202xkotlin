package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-20.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val tinput = {}.javaClass.getResource("input-20-1.txt")!!.readText().linesFiltered { it.isNotEmpty() };
    val day = Day20(input)
    val test = Day20(tinput)
    println(test.part1())
    println(test.part2())
    println(day.part1())
    println(day.part2())
}

class Day20(private val input: List<String>) {
    fun part1(): Long {
        val original = input.mapIndexed { index, i -> index to i.toLong() }
        val target = original.toMutableList()
        original.forEach {
            target.move(it)
        }
        return target.getWrapped(1000) + target.getWrapped(2000) + target.getWrapped(3000)
    }


    fun part2(): Long {
      val key = 811589153
        val original = input.mapIndexed { index, i -> index to i.toLong() * key }
        val target = original.toMutableList()
        repeat(10) {
            original.forEach {
                target.move(it)
            }
        }
        return target.getWrapped(1000) + target.getWrapped(2000) + target.getWrapped(3000)
    }

/*
    fun MutableList<Pair<Int, Int>>.move(element: Pair<Int, Int>) {
        val old = indexOf(element)
        removeAt(old)
        add((old + element.second).mod(size), element)
    }
    fun MutableList<Pair<Int, Int>>.getWrapped(index: Int): Int {
        return this[(indexOfFirst { it.second == 0 } + index).mod(size)].second
    }
*/

    fun MutableList<Pair<Int, Long>>.move(element: Pair<Int, Long>) {
        val old = indexOf(element)
        removeAt(old)
        add((old + element.second).mod(size), element)
    }
    fun MutableList<Pair<Int, Long>>.getWrapped(index: Int): Long {
        return this[(indexOfFirst { it.second == 0L } + index).mod(size)].second
    }
}


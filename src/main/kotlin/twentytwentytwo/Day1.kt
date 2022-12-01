package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-1.txt")?.readText()!!.lines()
    println(dayOnePartOne(input))
    println(dayOnePartTwo(input))
}

fun dayOnePartOne(input: List<String>): Int {
    input.spliterator()
    var max = 0
    var total = 0
    for (line in input) {
        if (line != "")
            total += Integer.parseInt(line)
        else {
            if (total > max) {
                max = total
            }

            total = 0
        }
    }
    return max
}

fun dayOnePartTwo(input: List<String>): Int {

    var totals = ArrayList<Int>()
    var total = 0
    for (line in input) {
        if (line != "") {
            total += Integer.parseInt(line)
        } else {
            totals.add(total)
            total = 0
        }
    }
    return totals.sortedDescending().take(3).sum()
}
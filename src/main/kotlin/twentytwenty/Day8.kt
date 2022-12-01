data class Instruction(val operation: String, val value: Int){
    var seen = false
}
fun main() {
    val run = readInput().run()
    println("part 1: ${run.first}")
    readInput().forEachIndexed { index, _ ->
        val output = readInput().run(index)
        if (!output.second) {
            println("part 2: ${output.first}")
            return
        }
    }
}

private fun readInput() =
    {}.javaClass.getResource("/twentytwenty/input8.txt").readText().lines()
        .map { Instruction(it.split(" ")[0], it.split(" ")[1].toInt()) }

fun List<Instruction>.run(index: Int = -1): Pair<Int, Boolean> {
    var (acc, pos) = 0 to 0
    while (!this[pos].seen) {
        var command = this[pos].operation
        if (index == pos) {
            command = when (command) {
                "nop" -> "jmp"
                "jmp" -> "nop"
                else -> "acc"
            }
        }
        when (command) {
            "jmp" -> pos += this[pos].value
            "acc" -> {
                acc += this[pos].value
                pos++
            }
            "nop" -> pos++
        }
        if (pos == this.size) return acc to false
    }
    return acc to true
}


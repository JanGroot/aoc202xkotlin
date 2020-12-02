data class Input(val under: Int, val upper: Int, val letter: Char, val password: String) {
    fun passes() = password.count { it == letter } in under..upper
    fun passes2() = listOf(upper, under).count {password[it -1] == letter} == 1
}

fun main() {
    val input = readLines("input2.txt").map {
        val match = """(\d+)-(\d+) (\w): (\w+)""".toRegex().find(it)
        val (under, upper, letter, password) = match!!.destructured
        Input(under.toInt(), upper.toInt(), letter[0], password)
    }
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<Input>): Int = input.count { it.passes() }

fun part2(input: List<Input>): Int = input.count { it.passes2() }

data class Input(val under: Int, val upper: Int, val letter: Char, val password: String)

fun main() {
    val input = readLines("input2.txt").map {
        val match = """(\d+)-(\d+) (\w): (\w+)""".toRegex().find(it)
        val (under, upper, letter, password) = match!!.destructured
        Input(under.toInt(), upper.toInt(), letter[0], password)
    }
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<Input>): Int = input.count { checkPass(it) }

fun part2(input: List<Input>): Int = input.count { checkPass2(it) }

fun checkPass(input: Input): Boolean {
    val count = input.password.count { it == input.letter }
    return count in input.under..input.upper
}

fun checkPass2(input: Input): Boolean {
    var count = 0
    if (input.password[input.under - 1] == input.letter) count++
    if (input.password[input.upper - 1] == input.letter) count++
    return count == 1
}
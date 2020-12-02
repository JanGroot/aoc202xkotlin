data class Password(val under: Int, val upper: Int, val letter: Char, val password: String) {
    fun passes() = password.count { it == letter } in under..upper
    fun passes2() = listOf(upper, under).count {password[it -1] == letter} == 1
}

fun main() {
    val passwords = readLines("input2.txt").map {
        val match = """(\d+)-(\d+) (\w): (\w+)""".toRegex().find(it)
        val (under, upper, letter, password) = match!!.destructured
        Password(under.toInt(), upper.toInt(), letter[0], password)
    }
    println(passwords.count { it.passes() })
    println(passwords.count { it.passes2() })
}


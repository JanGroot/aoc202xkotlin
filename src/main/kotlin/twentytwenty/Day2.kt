package twentytwenty

data class Password(val under: Int, val upper: Int, val letter: Char, val password: String) {
    fun letterOccursInRange() = password.count { it == letter } in under..upper
    fun hasLetterAtEitherPosition() = listOf(upper, under).count {password[it -1] == letter} == 1
}

fun main() {
    val passwords = {}.javaClass.getResource("twentytwenty/input2.txt").readText().lines().map {
        val match = """(\d+)-(\d+) (\w): (\w+)""".toRegex().find(it)
        val (under, upper, letter, password) = match!!.destructured
        Password(under.toInt(), upper.toInt(), letter[0], password)
    }
    println(passwords.count { it.letterOccursInRange() })
    println(passwords.count { it.hasLetterAtEitherPosition() })
}


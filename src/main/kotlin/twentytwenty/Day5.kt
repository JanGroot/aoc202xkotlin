fun main() {
    val seatNumbers = {}.javaClass.getResource("twentytwenty/input5.txt").readText().lines().map { it.toBinary("BR").toInt(2) }.sorted()
    println(seatNumbers.last())
    println((seatNumbers.first()..seatNumbers.last()).first { it !in seatNumbers })
}

fun String.toBinary(ones: String) = map { c -> if (c in ones) '1' else '0' }.joinToString("")




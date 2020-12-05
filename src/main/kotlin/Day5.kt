fun main() {
    val records = {}.javaClass.getResource("input5.txt").readText().lines().map { it.toBinary("BR").toInt(2) }.sorted()
    println(records.last())
    println((records.first()..records.last()).first { it !in records })
}

fun String.toBinary(ones: String) = map { c -> if (c in ones) '1' else '0' }.joinToString("")




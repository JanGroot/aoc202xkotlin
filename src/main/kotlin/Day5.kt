fun main() {
    val records = {}.javaClass.getResource("input5.txt").readText().lines().toInt().sorted()
    println(records.last())
    println((records.first()..records.last()).first { it !in records })
}

fun List<String>.toInt() =
    map { it.replace('B', '1').replace('R', '1').replace('F', '0').replace('L', '0').toInt(2) }




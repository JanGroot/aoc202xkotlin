data class Bag(val colour: String, val quantity: Int)

fun main() {
    val bagsByColour = {}.javaClass.getResource("input7.txt").readText().lines().map {
        val key = it.substringBefore(" bag")
        val bags = """(\d+) (\w+ \w+)\b""".toRegex().findAll(it).map { match ->
            val (quantity, colour) = match.destructured
            Bag(colour, quantity.toInt())
        }.toList()
        key to bags
    }.toMap()

    fun getParentBags(colour: String): List<String> =
        bagsByColour.filter { it.value.any { bag -> bag.colour == colour } }
            .flatMap { (key, _) -> getParentBags(key) } + colour

    fun getChildBags(colour: String): Int =
        1 + (bagsByColour[colour]?.map { it.quantity * getChildBags(it.colour) }?.sum() ?: 1)

    getParentBags("shiny gold").toSet().also { println(it.size - 1) }
    getChildBags("shiny gold").also { println(it - 1) }
}
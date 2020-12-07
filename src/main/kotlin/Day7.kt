data class Bag(val colour: String, val quantity: Int)

fun main() {
    val bagsByColour = {}.javaClass.getResource("input7.txt").readText().lines().map {
        val colour = it.substringBefore(" bag")
        val bags = """(\d+) (\w+ \w+)\b""".toRegex().findAll(it).map { match ->
            val (quantity, colour) = match!!.destructured
            Bag(colour, quantity.toInt())
        }.toList()
        colour to bags
    }.toMap()
    getParentBags(bagsByColour, "shiny gold").toSet().also { println(it.size - 1) }
    getChildBags(bagsByColour, "shiny gold").also { println(it - 1) }

}
fun getParentBags(bags: Map<String, List<Bag>>, colour: String): List<String> =
    bags.filter { it.value.any { bag -> bag.colour == colour } }
        .flatMap { (key, _) -> getParentBags(bags, key) } + colour

fun getChildBags(bags: Map<String, List<Bag>>, colour: String): Int =
     1 + (bags[colour]?.map { it.quantity * getChildBags(bags, it.colour) }?.sum() ?: 1)
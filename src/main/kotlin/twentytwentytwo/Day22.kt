package twentytwentytwo


fun main() {
    val input = {}.javaClass.getResource("input-22.txt")!!.readText().split("\n\n");
    val tinput = {}.javaClass.getResource("input-22-1.txt")!!.readText().split("\n\n");
    val test = Day22(tinput)
    val day = Day22(input)
    val testResult1 = test.part1()
    println((testResult1.first.first * 4) + (testResult1.first.second * 1000) + Day22.directions.indexOf(testResult1.second))
    val result1 = day.part1()
    println((result1.first.first * 4) + (result1.first.second * 1000) + Day22.directions.indexOf(result1.second))
    //println(test.part2())
    //println(day.part1())
    //println(day.part2())
}


class Day22(val input: List<String>) {
    val land: List<Pair<Pair<Int, Int>, Char>> = input[0].lines().mapIndexed { y, row ->
        row.mapIndexed { x, s -> x + 1 to y + 1 to s }
    }.flatten().filter { p -> p.second != ' ' }
    val path = "([R|L])|(\\d+)".toRegex().findAll(input[1]).map { it.value }.toList()

    var direction = "R"

    fun part1(): Pair<Pair<Int, Int>, String> {
        var position = land.first().first
        path.forEach { step ->
            //println("${position} next ${step}")
            when (step) {
                "R" -> direction = rotate("R")
                "L" -> direction = rotate("L")
                else -> {
                    position = move(position, step.toInt())
                }
            }

            //println(currentDirection)
        }

        return position to direction

    }

    fun part2() {
        TODO()
    }
    fun move(position: Pair<Int, Int>, steps: Int): Pair<Int, Int> {
        if (steps == 0) return position
        var next = move(position)
        var found = land.firstOrNull { p -> p.first == next }
            ?: when (direction) {
                "D" -> land.first { p -> p.first.first == next.first }
                "U" -> land.last { p -> p.first.first == next.first }
                "L" -> land.last { p -> p.first.second == next.second }
                "R" -> land.first { p -> p.first.second == next.second }
                else -> {
                    error("noooo!")
                }
            }
        //println(found)
        if (found.second == '#') return position
        return move(found.first, steps - 1)
    }

    fun move(position: Pair<Int, Int>): Pair<Int, Int> {
        return when (direction) {
            "U" -> position.first to position.second - 1
            "D" -> position.first to position.second + 1
            "L" -> position.first - 1 to position.second
            "R" -> position.first + 1 to position.second
            else -> {
                position
            }
        }
    }

    fun rotate(newDirection: String): String {
        return when (newDirection) {
            "R" -> directions[(directions.indexOf(direction) + 1).mod(4)]
            "L" -> directions[(directions.indexOf(direction) - 1).mod(4)]
            else -> {
                error("Cant move there")
            }
        }
    }

    /*    fun Structures.Point2d.move(steps: Int) =
            if (steps == 0) this
            else {
                if (land.contains(move(direction))
            }*/
    companion object {
        val directions = arrayOf("R", "D", "L", "U")


    }

}
package twentytwentytwo


fun main() {
    val input = {}.javaClass.getResource("input-22.txt")!!.readText().split("\n\n");
    val tinput = {}.javaClass.getResource("input-22-1.txt")!!.readText().split("\n\n");
    val test = Day22(tinput)
    val day = Day22(input)
    println(test.part1())
    println(test.part2())
    println(day.part1())
    println(day.part2())
}


class Day22(val input: List<String>) {
    val land = input[0].lines().mapIndexed { y, row ->
        row.mapIndexed { x, s -> x to y to s }
    }.flatten().filter { p -> p.second != ' ' }
    val path = "([R|L])|(\\d+)".toRegex().findAll(input[1]).map { it.value }.toList()


    fun part1() {
        var position = land.first().first
        println(land)
        println(path)
        path.forEach { step ->
            when (step) {
                "R" -> rotate("R")
                "L" -> rotate("R")
                else -> {
                    position = move(position, step.toInt())
                }
            }

            println(position)
        }


    }

    fun part2() {
        TODO()
    }

    /*    fun Structures.Point2d.move(steps: Int) =
            if (steps == 0) this
            else {
                if (land.contains(move(direction))
            }*/
    companion object {
        var currentDirection = "R"
        val directions = arrayOf("U", "R", "D", "L")
        fun rotate(newDirection: String): Unit {
            when (newDirection) {
                "R" -> currentDirection = directions[(directions.indexOf(currentDirection) + 1).mod(4)]
                "L" -> currentDirection = directions[(directions.indexOf(currentDirection) + 1).mod(4)]
                else -> {
                    error("Cant move there")
                }
            }
        }

        fun move(position:Pair<Int,Int>, steps: Int): Pair<Int,Int> {
            if (steps == 0) return position
            var next = Structures.Point2d(position.first, position.second).move(currentDirection)
            return move(next.x to next.y, steps -1)
        }
    }
}
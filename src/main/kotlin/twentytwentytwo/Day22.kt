package twentytwentytwo

import kotlin.math.sqrt


fun main() {
    val input = {}.javaClass.getResource("input-22.txt")!!.readText().split("\n\n");
    val tinput = {}.javaClass.getResource("input-22-1.txt")!!.readText().split("\n\n");
    val test = Day22(tinput)
    val day = Day22(input)
    //val testResult1 = test.part1()
    //println((testResult1.first.first * 4) + (testResult1.first.second * 1000) + Day22.directions.indexOf(testResult1.second))
    //val result1 = day.part1()
    //println((result1.first.first * 4) + (result1.first.second * 1000) + Day22.directions.indexOf(result1.second))
    var result2 = day.part2()
    println(result2)
    println((result2.first.first * 4) + (result2.first.second * 1000) + Day22.directions.indexOf(result2.second))
}


class Day22(val input: List<String>) {
    val land: List<Pair<Pair<Int, Int>, Char>> = input[0].lines().mapIndexed { y, row ->
        row.mapIndexed { x, s -> x + 1 to y + 1 to s }
    }.flatten().filter { p -> p.second != ' ' }

    val path = "([R|L])|(\\d+)".toRegex().findAll(input[1]).map { it.value }.toList()

    var direction = "R"

    val edges: List<Pair<Int, Pair<IntRange, IntRange>>>

    lateinit var position: Pair<Int, Int>

    init {
        val size = (land.size / 6).toDouble()
        val sides = (sqrt(size)).toInt()
        /*input[0].lines().windowed(1, sides, true).flatten().mapIndexed { indexY, s ->
            s.windowed(sides, sides).mapIndexed { index, s -> indexY to index + 1 to s }.filter { it.second.none { it == ' ' } }.also { println(it) }
        }.flatten().mapIndexed { index, pair ->
            val yfact = pair.first.second
            val xfact = pair.first.first
            index to ((1 + (xfact * sides))..((xfact + 1) * sides) to (1 + (yfact * sides))..((yfact + 1) * sides))
        }.also { println(it) }
        (1..size.toInt()).map { x ->
            val points = land.filter { it.first.first == x }
            points.map {
                it to points.size / sides + (x - 1 / sides)
                // let's try to slice it in a cube

            }
        }.flatten()*/


        val side1 = 1 to (51..100 to 1..50)
        val side2 = 2 to (101..150 to 1..50)
        val side3 = 3 to (51..100 to 51..100)
        val side4 = 4 to (1..50 to 101..150)
        val side5 = 5 to (51..100 to 101..150)
        val side6 = 6 to (1..50 to 151..200)
        edges = listOf(side6, side1, side2, side3, side4, side5)
    }

    fun part1(): Pair<Pair<Int, Int>, String> {
        position = land.first().first
        path.forEach { step ->
            print("${position} next ${step}")
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

    fun part2(): Pair<Pair<Int, Int>, String> {
        position = 51 to 1
        direction = "R"
        path.forEach { step ->
            print("${position} next ${step}")
            when (step) {
                "R" -> direction = rotate("R")
                "L" -> direction = rotate("L")
                else -> {
                    position = move2(position, step.toInt())
                }
            }

            //println(currentDirection)
        }

        return position to direction

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

    fun move2(position: Pair<Int, Int>, steps: Int): Pair<Int, Int> {
        if (steps == 0) return position
        println("$position + $direction")
        var next = move2(position)
        val side = position.getSide()
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
        val nextSide = next.getSide()
        if (side != nextSide) {
            /* val side1 = 1 to (51..100 to 1..50)
        val side2 = 2 to (101..150 to 1..50)
        val side3 = 3 to (51..100 to 51..100)
        val side4 = 4 to (1..50 to 101..150)
        val side5 = 5 to (51..100 to 101..150)
        val side6 = 6 to (1..50 to 151..200)*/
            var nextSet: Pair<String, Pair<Int, Int>> = when (side to direction) {
                1 to "U" -> { // to 6
                    "R" to (1 to position.first + 100) //ok
                }
                1 to "L" -> {  //to 4
                    "R" to (1 to 151 - position.second) //ok
                }
                2 to "U" -> { // to 6
                    "U" to (position.first - 100 to 200) //ok
                }

                2 to "R" -> {// to 5
                    "L" to (100 to 151 - position.second)
                }

                2 to "D" -> { // to 3
                    "L" to (100 to position.first - 50)
                }

                3 to "R" -> {
                    "U" to (position.second + 50 to 50)
                }

                3 to "L" -> {
                    "D" to (position.second - 50 to 101)//ok
                }

                4 to "L" -> {
                    "R" to (51 to 151 - position.second)
                }

                4 to "U" -> {
                    "R" to (51 to position.first + 50)
                }

                5 to "R" -> {
                    "L" to (150 to 151 - position.second)

                }

                5 to "D" -> {
                    "L" to (50 to position.first + 100)
                }

                6 to "D" -> {
                    "D" to (position.first + 100 to 1)
                }

                6 to "R" -> {
                    "U" to (position.second - 100 to 150)

                }

                6 to "L" -> {
                    "D" to (position.second - 100 to 1)
                }

                else -> {
                    direction to next
                }
            }

            found = land.firstOrNull { p -> p.first == nextSet.second }!!
            if (found.second == '#') {
                return position
            }
            direction = nextSet.first
        }

        //println(found)

        if (found.second == '#') return position
        return move2(found.first, steps - 1)
    }

    fun move2(position: Pair<Int, Int>): Pair<Int, Int> {
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

    companion object {
        val directions = arrayOf("R", "D", "L", "U")


    }

    fun Pair<Int, Int>.getSide(): Int {
        return edges.find {

            val (xr, yr) = it.second
            (this.first in xr && this.second in yr)
        }?.first ?: -1
    }

}
package twentytwentytwo

fun main() {
    val input = {}.javaClass.getResource("input-2.txt")!!.readText().split("\n");
    val day = Day2(input)
    println(day.part1())
    println(day.part2())
}

class Day2(private val input: List<String>) {
    fun part1(): Int {
        fun toScore(l: String): Int {
            return when (l) {
                "A X" -> 4;
                "A Y" -> 8;
                "A Z" -> 3;
                "B X" -> 1;
                "B Y" -> 5;
                "B Z" -> 9;
                "C X" -> 7;
                "C Y" -> 2;
                "C Z" -> 6;
                else -> {
                    0
                }
            }
        }
        return input
            .sumOf { l -> toScore(l) }
    }

    fun part2(): Int {
        fun toScore(l: String): Int {
            return when (l) {
                "A X" -> 3;
                "A Y" -> 4;
                "A Z" -> 8;
                "B X" -> 1;
                "B Y" -> 5;
                "B Z" -> 9;
                "C X" -> 2;
                "C Y" -> 6;
                "C Z" -> 7;
                else -> {
                    0
                }
            }
        }
        return input.sumOf { l -> toScore(l) };
    }
}


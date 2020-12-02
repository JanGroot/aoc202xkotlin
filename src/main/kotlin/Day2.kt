fun readLines() = {}.javaClass.getResource("input2.txt").readText().lines();

fun main() {
    val input = readLines()
    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int {
    return input.count { checkPass(it) }
}

fun part2(input: List<String>): Int {
    return input.count { checkPass2(it) }
}

fun checkPass(raw: String): Boolean {
    var rule = raw.split(":")[0]
    var password = raw.split(":")[1]
    var range = rule.split(" ")[0]
    var letter = rule.split(" ")[1].toCharArray()[0]
    val count = password.count { it == letter }
    val under = Integer.valueOf(range.split("-")[0])
    val upper = Integer.valueOf(range.split("-")[1])
    return count in under..upper
}

fun checkPass2(raw: String): Boolean {
    var rule = raw.split(":")[0]
    var password = raw.split(":")[1].trim()
    var range = rule.split(" ")[0]
    var letter = rule.split(" ")[1].toCharArray()[0]
    val under = Integer.valueOf(range.split("-")[0]) - 1
    val upper = Integer.valueOf(range.split("-")[1]) - 1
    return (password[under] == letter || password[upper] == letter) && !(password[under] == letter && password[upper] == letter)
}
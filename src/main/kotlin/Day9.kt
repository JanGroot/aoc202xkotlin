fun main() {
    var input = readInput()
    var key = 0L
    for (i in 25 until input.size) {
        val numbers = input.subList(i - 25, i)
        if (hasNoSum(numbers, input[i])) key = input[i]
    }
    println("$key")
    findRange(input, key).also {
        println("${it.sum()} ")
    }

}

fun hasNoSum(numbers: List<Long>, number: Long): Boolean {
    return numbers.find {
            i -> (number - i) in numbers
            && i != number } == null
}

fun findRange(numbers: List<Long>, number: Long): List<Long> {
    for (i in numbers.indices) {
        for (j in numbers.indices.reversed()) {
            if (j < i ) break
            var sub = numbers.subList(i, j)
            if (sub.sum() == number) return listOf(sub.minOrNull()!!, sub.maxOrNull()!!)
        }
    }
    return emptyList()
}

private fun readInput() =
    {}.javaClass.getResource("input9.txt").readText().lines().map{it.toLong()}


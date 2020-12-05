fun main() {
    val map = {}.javaClass.getResource("input3.txt").readText().lines()
    println(map.getTreesForSlope(3))
    println(map.getTreesForSlope(1) * map.getTreesForSlope(3) * map.getTreesForSlope(5) * map.getTreesForSlope(7) * map.getTreesForSlope(1,2))
}

fun List<String>.getTreesForSlope(right: Int, down: Int = 1): Int =
    (down until size step down).count { (this[it][(right * (it / down)) % this[it].length] == '#')
}


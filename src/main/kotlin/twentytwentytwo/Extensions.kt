package twentytwentytwo

fun String.linesFiltered(predicate: (String) -> Boolean): List<String> {
    return this.lines().filter(predicate)
}

//perhaps needed later?
fun String.getStartMarker(size: Int): Int {
    return this.windowed(size).indexOfFirst { it.toSet().size == size } + size
}
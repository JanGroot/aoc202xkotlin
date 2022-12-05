package twentytwentytwo

fun String.linesFiltered(predicate: (String) -> Boolean): List<String> {
    return this.lines().filter(predicate)
}
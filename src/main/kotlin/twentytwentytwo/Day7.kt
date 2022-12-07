package twentytwentytwo

import twentytwentytwo.File.Companion.ROOT
import twentytwentytwo.File.Companion.isDir


fun main() {
    val input =
        {}.javaClass.getResource("input-7.txt")!!.readText().linesFiltered { it.isNotEmpty() && !it.startsWith("$ ls") }
    val day = Day7(input)
    println(day.part1())
    println(day.part2())
}

class Day7(input: List<String>) {

    init {
        var current = ROOT;
        input.forEach { line ->
            when {
                line.startsWith("$ cd") -> {
                    current = current.changeDirectory(line.split(" ")[2])
                }
                else -> current.addChild(File.of(line))
            }
        }
    }

    fun part1(): Int {
        return ROOT.getAllFiles().filter { isDir(it) }.filter { it.dirSize() <= 100000 }.sumOf { it.dirSize() }

    }

    fun part2(): Int {
        val needed = 70000000 - ROOT.dirSize()
        return ROOT.getAllFiles().filter { isDir(it) }.filter { it.dirSize() >= 30000000 - needed }
            .minOf { it.dirSize() }
    }

}

class File(private val name: String, val size: Int) {
    private var parent: File = ROOT
    private var children: MutableList<File> = mutableListOf()

    fun addChild(node: File) {
        children.add(node)
        node.parent = this
    }

    fun changeDirectory(name: String): File {
        return when (name) {
            "/" -> ROOT
            ".." -> parent
            else -> children.first { it.name == name }
        }
    }

    fun dirSize(): Int = if (isDir(this)) children.sumOf { it.dirSize() } else size

    fun getAllFiles(): List<File> = (this.children.map { it.getAllFiles() }.flatten() + this)

    companion object {
        val ROOT = File("/", 0)
        fun isDir(file: File): Boolean = file.size == 0
        fun of(rawInput: String): File =
            rawInput.split(" ").let { (size, name) ->
                File(name, if (size == "dir") 0 else size.toInt())
            }
    }
}


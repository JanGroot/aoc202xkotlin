package twentytwentytwo

import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sign

typealias Visitor<T> = (Structures.TreeNode<T>) -> Unit

class Structures {


    interface Queue<T> {

        val count: Int
        val isEmpty: Boolean
        fun enqueue(element: T): Boolean
        fun dequeue(): T?
        fun peek(): T?
    }

    class ArrayListQueue<T> : Queue<T> {
        private val storage = arrayListOf<T>()
        override val count: Int
            get() = storage.size
        override val isEmpty: Boolean
            get() = count == 0

        override fun enqueue(element: T): Boolean = storage.add(element)
        override fun dequeue(): T? = if (isEmpty) null else storage.removeAt(0)
        override fun peek(): T? = storage.getOrNull(0)
    }


    class TreeNode<T>(private val value: T) {
        private val children: MutableList<TreeNode<T>> = mutableListOf()
        fun add(child: TreeNode<T>) = children.add(child)
        fun forEachDepthFirst(visit: Visitor<T>) {
            visit(this)
            children.forEach {
                it.forEachDepthFirst(visit)
            }
        }

        fun forEachLevelOrder(visit: Visitor<T>) {
            visit(this)
            val queue = ArrayListQueue<TreeNode<T>>()
            children.forEach {
                queue.enqueue(it)
                var node = queue.dequeue()
                while (node != null) {
                    visit(node)
                    node.children.forEach { queue.enqueue(it) }
                    node = queue.dequeue()
                }
            }
        }

        fun search(predicate: (T) -> Boolean): TreeNode<T>? {
            var result: TreeNode<T>? = null
            forEachDepthFirst {
                if (predicate(it.value)) {
                    result = it
                }
            }
            return result
        }
    }

    data class Point3d(val x: Int, val y: Int, val z: Int) {
        fun neighbors() =
            listOf(
                Point3d(x, y + 1, z),
                Point3d(x, y - 1, z),
                Point3d(x + 1, y, z),
                Point3d(x - 1, y, z),
                Point3d(x, y, z + 1),
                Point3d(x, y, z - 1),
            )


    }

    data class Point2d(val x: Int, val y: Int, var value: Char = ' ') {

        infix fun sameRow(that: Point2d): Boolean =
            y == that.y

        infix fun sameColumn(that: Point2d): Boolean =
            x == that.x

        fun distanceTo(other: Point2d) = abs(x - other.x) + abs(y - other.y)


        fun manRanges(dist: Int, row: Int): IntRange? {
            val width = dist - abs(y - row)
            // if negative this sensor has registered a beacon closer than this row is far :-)
            return if (width > 0) x - width..x + width else null
        }


        fun neighbors() =
            listOf(
                Point2d(x, y + 1),
                Point2d(x, y - 1),
                Point2d(x + 1, y),
                Point2d(x - 1, y)
            )

        private fun corners() = listOf(
            Point2d(x - 1, y - 1),
            Point2d(x - 1, y + 1),
            Point2d(x + 1, y - 1),
            Point2d(x + 1, y + 1)
        )

        infix fun lineTo(that: Point2d): List<Point2d> {
            val xDelta = (that.x - x).sign
            val yDelta = (that.y - y).sign
            val steps = maxOf((x - that.x).absoluteValue, (y - that.y).absoluteValue)
            return (1..steps).scan(this) { last, _ -> Point2d(last.x + xDelta, last.y + yDelta) }
        }

        val down by lazy { Point2d(x, y + 1) }
        val downLeft by lazy { Point2d(x - 1, y + 1) }
        val downRight by lazy { Point2d(x + 1, y + 1) }

        private fun allNeighbors() = neighbors() + corners()

        fun move(dir: String): Point2d {
            return when (dir) {
                "U" -> Point2d(x, y + 1)
                "D" -> Point2d(x, y - 1)
                "L" -> Point2d(x - 1, y)
                "R" -> Point2d(x + 1, y)
                else -> {
                    this
                }
            }
        }

        fun follow(other: Point2d): Point2d {
            val right = other.x > x
            val up = other.y > y
            return when {
                other in allNeighbors() + this -> this
                sameRow(other) -> if (right) move("R") else move("L")
                sameColumn(other) -> if (up) move("U") else move("D")
                else -> {
                    val step1 = if (right) move("R") else move("L")
                    if (up) step1.move("U") else step1.move("D")
                }
            }
        }
    }
}

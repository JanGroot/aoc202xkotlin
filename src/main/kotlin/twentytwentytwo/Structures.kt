package twentytwentytwo

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

    data class Point2d(val x: Int, val y: Int) {

        infix fun sameRow(that: Point2d): Boolean  =
            y == that.y

        infix fun sameColumn(that: Point2d): Boolean =
            x == that.x
    }
}
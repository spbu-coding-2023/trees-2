package tsl.iterator

import tsl.nodes.AbstractNode
import tsl.trees.AbstractBinaryTree
internal class BinaryTreeIterator<K : Comparable<K>, V, N : AbstractNode<K, V, N>>(
    tree: AbstractBinaryTree<K, V, N>
) : Iterator<Pair<K, V>> {

    private var stack = ArrayDeque<N>()
    private var currentNode: N? = tree.root

    override fun hasNext(): Boolean {
        return (!stack.isEmpty() || currentNode != null)
    }

    override fun next(): Pair<K, V> {
        while (currentNode != null) {
            currentNode?.let { stack.addLast(it) }
            currentNode = currentNode?.leftChild
        }

        val nextNode = stack.removeLast()
        currentNode = currentNode?.rightChild

        return Pair(nextNode.key, nextNode.value)
    }
}

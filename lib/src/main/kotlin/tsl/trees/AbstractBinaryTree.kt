package tsl.trees

import tsl.nodes.AbstractNode

abstract class AbstractBinaryTree<K : Comparable<K>, V, N : AbstractNode<K, V, N>> {
    internal var root: N? = null

    fun search(key: K): V? {
        return searchNode(root, key)
    }

    private fun searchNode(
        node: AbstractNode<K, V, N>?,
        key: K,
    ): V? {
        return if (node?.key == key) {
            node.value
        } else if (node == null) {
            return null
        } else {
            if (key < (node.key)) {
                searchNode(node.leftChild, key)
            } else {
                searchNode(node.rightChild, key)
            }
        }
    }

    abstract fun delete(key: K)

    abstract fun insert(
        key: K,
        value: V,
    )
}
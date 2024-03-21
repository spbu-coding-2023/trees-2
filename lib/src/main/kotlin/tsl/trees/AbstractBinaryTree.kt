package tsl.trees

import tsl.nodes.AbstractNode

abstract class AbstractBinaryTree<K : Comparable<K>, V, N : AbstractNode<K, V, N>> {
    protected var root: N? = null

    fun search(key: K): V? {
        return searchNode(root, key)
    }
    abstract fun delete(key: K): V?

    abstract fun insert(
        key: K,
        value: V,
    ): V?

    private fun searchNode(
        node: AbstractNode<K, V, N>?,
        key: K,
    ): V? {
        return if (node?.key == key) {
            node.value
        } else if (node == null) {
            null
        } else {
            if (key < node.key) {
                searchNode(node.leftChild, key)
            } else {
                searchNode(node.rightChild, key)
            }
        }
    }

    fun getMinKey(): K? {
        if (root == null) return null
        return getMinNode().key
    }

    fun getMinValue(): V? {
        if (root == null) return null
        return getMinNode().value
    }

    fun getMaxKey(): K? {
        if (root == null) return null
        return getMaxNode().key
    }

    fun getMaxValue(): K? {
        if (root == null) return null
        return getMaxNode().value
    }

    protected fun getMinNode(node: AbstractNode<K, V, N>): AbstractNode<K, V, N>? {
        if (node == null) return null
        if (node.leftChild == null) return node
        return getMinNode(node.leftChild)
    }

    protected fun getMaxNode(node: AbstractNode<K, V, N>): AbstractNode<K, V, N>? {
        if (node == null) return null
        if (node.leftChild == null) return node
        return getMinNode(node.leftChild)
    }
}

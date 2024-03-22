package tsl.trees

import tsl.nodes.AbstractNode
import tsl.iterator.BinaryTreeIterator

abstract class AbstractBinaryTree<K : Comparable<K>, V, N : AbstractNode<K, V, N>>: Iterable<Pair<K, V>> {
    protected var root: N? = null

    abstract fun delete(key: K): V?

    abstract fun insert(
        key: K,
        value: V,
    ): V?

    fun search(key: K): V? {
        return searchNode(root, key)?.value
    }

    protected fun searchNode(node: AbstractNode<K, V, N>?, key: K): AbstractNode<K, V, N>? {
        return if (node?.key == key) node
        else if (node == null) null
        else {
            if (key < node.key) searchNode(node.leftChild, key)
            else searchNode(node.rightChild, key)
        }
    }

    fun clear() {
        root = null
    }

    fun getMinKey(): K? {
        if (root == null) return null
        return getMinNode(root)?.key
    }

    fun getMaxKey(): K? {
        if (root == null) return null
        return getMaxNode(root)?.key
    }

    protected fun getMinNode(node: AbstractNode<K, V, N>?): AbstractNode<K, V, N>? {
        if (node == null) return null
        if (node.leftChild == null) return node
        return getMinNode(node.leftChild)
    }

    protected fun getMaxNode(node: AbstractNode<K, V, N>?): AbstractNode<K, V, N>? {
        if (node == null) return null
        if (node.leftChild == null) return node
        return getMinNode(node.leftChild)
    }

    override fun iterator(): Iterator<Pair<K, V>> {
        return BinaryTreeIterator(this)
    }
}

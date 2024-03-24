package tsl.trees

import tsl.nodes.AbstractNode
import tsl.iterator.BinaryTreeIterator

abstract class AbstractBinaryTree<K : Comparable<K>, V, N : AbstractNode<K, V, N>>: Iterable<Pair<K?, V?>> {
    internal var root: N? = null

    abstract fun delete(key: K): V?

    abstract fun insert(
        key: K,
        value: V,
    ): V?

    fun search(key: K): V? {
        return searchNode(root, key)?.value
    }

    protected fun searchNode(node: AbstractNode<K, V, N>?, key: K): AbstractNode<K, V, N>? {
        return if (node == null) null
        else if (node.key == key) node
        else if (key < node.key) searchNode(node.leftChild, key)
        else searchNode(node.rightChild, key)
    }

    fun clear() {
        root = null
    }

    fun getMinKey(): K? {
        return getMinNode(root)?.key
    }

    fun getMaxKey(): K? {
        return getMaxNode(root)?.key
    }

    protected fun getMinNode(node: N?): N? {
        return if (node == null) null
        else if (node.leftChild == null) node
        else  getMinNode(node.leftChild)
    }

    protected fun getMaxNode(node: N?): N? {
        return if (node == null) null
        else if (node.rightChild == null) node
        else  getMaxNode(node.rightChild)
    }

    override fun iterator(): Iterator<Pair<K?, V?>> {
        return BinaryTreeIterator(this)
    }
}

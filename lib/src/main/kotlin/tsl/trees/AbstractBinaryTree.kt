package tsl.trees

import tsl.iterator.BinaryTreeIterator
import tsl.nodes.AbstractNode

abstract class AbstractBinaryTree<K : Comparable<K>, V, N : AbstractNode<K, V, N>> :
    Iterable<Pair<K, V>> {

    internal var root: N? = null

    abstract fun delete(key: K): V?

    abstract fun insert(key: K, value: V): V?

    fun search(key: K): V? = searchNodeRec(root, key)?.value

    protected fun searchNodeRec(currNode: N?, keyToSearch: K): N? {
        return when {
            currNode == null -> null
            keyToSearch == currNode.key -> currNode
            keyToSearch < currNode.key -> searchNodeRec(currNode.leftChild, keyToSearch)
            else -> searchNodeRec(currNode.rightChild, keyToSearch)
        }
    }

    fun clear() {
        root = null
    }

    fun isEmpty(): Boolean = root == null

    fun getMinKey(): K? = getMinNodeRec(root)?.key

    protected fun getMinNodeRec(node: N?): N? {
        return when {
            node == null -> null
            node.leftChild == null -> node
            else -> getMinNodeRec(node.leftChild)
        }
    }

    fun getMaxKey(): K? = getMaxNodeRec(root)?.key

    protected fun getMaxNodeRec(node: N?): N? {
        return when {
            node == null -> null
            node.rightChild == null -> node
            else -> getMaxNodeRec(node.rightChild)
        }
    }

    override fun iterator(): Iterator<Pair<K, V>> = BinaryTreeIterator(this)
}

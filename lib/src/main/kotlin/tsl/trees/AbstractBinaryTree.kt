package tsl.trees

import tsl.iterator.BinaryTreeIterator
import tsl.nodes.AbstractNode

abstract class AbstractBinaryTree<K : Comparable<K>, V, N : AbstractNode<K, V, N>> :
    Iterable<Pair<K, V>> {

    internal var root: N? = null

    public abstract fun delete(key: K): V?

    public abstract fun insert(key: K, value: V): V?

    public fun search(key: K): V? = searchNodeRec(root, key)?.value

    protected fun searchNodeRec(currNode: N?, keyToSearch: K): N? {
        return when {
            currNode == null -> null
            keyToSearch == currNode.key -> currNode
            keyToSearch < currNode.key -> searchNodeRec(currNode.leftChild, keyToSearch)
            else -> searchNodeRec(currNode.rightChild, keyToSearch)
        }
    }

    public fun clear() {
        root = null
    }

    public fun isEmpty(): Boolean = root == null

    public fun getMinKey(): K? = getMinNodeRec(root)?.key

    protected fun getMinNodeRec(node: N?): N? {
        return when {
            node == null -> null
            node.leftChild == null -> node
            else -> getMinNodeRec(node.leftChild)
        }
    }

    public fun getMaxKey(): K? = getMaxNodeRec(root)?.key

    protected fun getMaxNodeRec(node: N?): N? {
        return when {
            node == null -> null
            node.rightChild == null -> node
            else -> getMaxNodeRec(node.rightChild)
        }
    }

    public override fun iterator(): Iterator<Pair<K, V>> = BinaryTreeIterator(this)
}

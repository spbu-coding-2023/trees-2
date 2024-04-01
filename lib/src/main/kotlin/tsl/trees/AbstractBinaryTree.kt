package tsl.trees

import tsl.iterator.BinaryTreeIterator
import tsl.nodes.AbstractNode

abstract class AbstractBinaryTree<K : Comparable<K>, V, N : AbstractNode<K, V, N>> :
    Iterable<Pair<K, V>> {
    internal var root: N? = null

    public abstract fun delete(key: K): V?

    public abstract fun insert(key: K, value: V): V?

    public fun search(key: K): V? = searchNodeRecursively(root, key)?.value

    protected fun searchNodeRecursively(currNode: N?, keyToSearch: K): N? {
        return when {
            currNode == null -> null
            keyToSearch == currNode.key -> currNode
            keyToSearch < currNode.key -> searchNodeRecursively(currNode.leftChild, keyToSearch)
            else -> searchNodeRecursively(currNode.rightChild, keyToSearch)
        }
    }

    public fun clear() {
        root = null
    }

    public fun isEmpty(): Boolean = root == null

    public fun getMinKey(): K? = getMinNodeRecursively(root)?.key

    public fun getMaxKey(): K? = getMaxNodeRecursively(root)?.key

    protected fun getMinNodeRecursively(node: N?): N? {
        return when {
            node == null -> null
            node.leftChild == null -> node
            else -> getMinNodeRecursively(node.leftChild)
        }
    }

    protected fun getMaxNodeRecursively(node: N?): N? {
        return when {
            node == null -> null
            node.rightChild == null -> node
            else -> getMaxNodeRecursively(node.rightChild)
        }
    }

    public override fun iterator(): Iterator<Pair<K, V>> = BinaryTreeIterator(this)
}

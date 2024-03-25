package tsl.trees

import tsl.nodes.AbstractNode
import tsl.iterator.BinaryTreeIterator

abstract class AbstractBinaryTree<K : Comparable<K>, V, N : AbstractNode<K, V, N>>: Iterable<Pair<K?, V?>> {
    internal var root: N? = null

    public abstract fun delete(key: K): V?

    public abstract fun insert(key: K, value: V): V?

    public fun search(key: K): V? {
        return searchNodeRecursively(root, key)?.value
    }

    protected fun searchNodeRecursively(currNode: N?, keyToSearch: K): N? {
        when {
            currNode == null -> return null
            keyToSearch == currNode.key -> return currNode
            keyToSearch < currNode.key -> return searchNodeRecursively(currNode.leftChild, keyToSearch)
            else -> return searchNodeRecursively(currNode.rightChild, keyToSearch)
        }
    }

    public fun clear(): V? {
        val rootValueBeforeClear = root?.value

        root = null

        return rootValueBeforeClear
    }

    public fun getMinKey(): K? = getMinNodeRecursively(root)?.key

    public fun getMaxKey(): K? = getMaxNodeRecursively(root)?.key

    protected fun getMinNodeRecursively(node: N?): N? {
        when {
            node == null -> return null
            node.leftChild == null -> return node
            else -> return getMinNodeRecursively(node.leftChild)
        }
    }

    protected fun getMaxNodeRecursively(node: N?): N? {
        when {
            node == null -> return null
            node.rightChild == null -> return node
            else -> return getMinNodeRecursively(node.rightChild)
        }
    }

    public override fun iterator(): Iterator<Pair<K?, V?>> = BinaryTreeIterator(this)
}

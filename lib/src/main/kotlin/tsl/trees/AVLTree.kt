package tsl.trees

import kotlin.math.max
import tsl.nodes.AVLNode

class AVLTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, AVLNode<K, V>>() {
    public override fun insert(key: K, value: V): V? {
        val oldValueByKey = search(key) // null if key isn't in the tree

        insertNodeAndBalanceRecursively(root, key, value)

        return oldValueByKey
    }

    private fun insertNodeAndBalanceRecursively(
        currNode: AVLNode<K, V>?,
        keyToInsert: K,
        valueToInsert: V
    ): AVLNode<K, V> {

        if (currNode == null) {
            val newNode = AVLNode(keyToInsert, valueToInsert)
            if (root == null) root = newNode
            return newNode
        }

        when {
            keyToInsert < currNode.key ->
                currNode.leftChild =
                    insertNodeAndBalanceRecursively(currNode.leftChild, keyToInsert, valueToInsert)
            keyToInsert > currNode.key ->
                currNode.rightChild =
                    insertNodeAndBalanceRecursively(currNode.rightChild, keyToInsert, valueToInsert)
            else -> {
                currNode.value = valueToInsert
                return currNode
            }
        }

        var balancedNode: AVLNode<K, V> = currNode

        if (getBalanceFactor(currNode) < -1) { // if left subtree got bigger
            currNode.leftChild?.let {
                if (keyToInsert > it.key) { // if inserted node has greater key
                    currNode.leftChild = rotateLeft(it) // perform left rotation first
                }
                balancedNode = rotateRight(currNode) // anyway, perform right rotation
            }
        } else if (getBalanceFactor(currNode) > 1) { // if right subtree got bigger
            currNode.rightChild?.let {
                if (keyToInsert < it.key) { // if inserted node has lesser key
                    currNode.rightChild = rotateRight(it) // perform right rotation first
                }
                balancedNode = rotateLeft(currNode) // anyway, perform left rotation
            }
        }

        updateHeight(balancedNode.leftChild)
        updateHeight(balancedNode.rightChild)
        updateHeight(balancedNode)

        return balancedNode
    }

    private fun rotateRight(oldUpperNode: AVLNode<K, V>): AVLNode<K, V> {
        val newUpperNode = oldUpperNode.leftChild ?: return oldUpperNode
        oldUpperNode.leftChild = newUpperNode.rightChild
        newUpperNode.rightChild = oldUpperNode

        if (root == oldUpperNode) root = newUpperNode // if root was rotated, set new root

        return newUpperNode
    }

    private fun rotateLeft(oldUpperNode: AVLNode<K, V>): AVLNode<K, V> {
        val newUpperNode = oldUpperNode.rightChild ?: return oldUpperNode
        oldUpperNode.rightChild = newUpperNode.leftChild
        newUpperNode.leftChild = oldUpperNode

        if (root == oldUpperNode) root = newUpperNode // if root was rotated, set new root

        return newUpperNode
    }

    private fun updateHeight(node: AVLNode<K, V>?): Int {
        if (node == null) return -1
        node.height = max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1
        return node.height
    }

    private fun getBalanceFactor(node: AVLNode<K, V>): Int =
        getHeight(node.rightChild) - getHeight(node.leftChild)

    private fun getHeight(node: AVLNode<K, V>?): Int = node?.height ?: -1

    public override fun delete(key: K): V? {
        val deletedValue: V =
            search(key) ?: return null // if key isn't in the tree, there's nothing to delete

        deleteAndBalanceRecursively(root, key)

        return deletedValue
    }

    private fun deleteAndBalanceRecursively(
        currNode: AVLNode<K, V>?,
        keyToDelete: K
    ): AVLNode<K, V>? {

        when {
            currNode == null -> return null // node to be deleted was not found
            keyToDelete < currNode.key -> // node to be deleted is in the left subtree
            currNode.leftChild = deleteAndBalanceRecursively(currNode.leftChild, keyToDelete)
            keyToDelete > currNode.key -> // node to be deleted is in the right subtree
            currNode.rightChild = deleteAndBalanceRecursively(currNode.rightChild, keyToDelete)
            else -> {
                if (currNode.leftChild == null || currNode.rightChild == null) {
                    if (currNode == root) root = null
                    return currNode.leftChild ?: currNode.rightChild
                } else {
                    val successor = getMinNodeRecursively(currNode.rightChild)
                    if (successor != null) {
                        // copy its successor
                        currNode.key = successor.key
                        currNode.value = successor.value

                        // delete original successor node from tree
                        val newSubtree =
                            deleteAndBalanceRecursively(currNode.rightChild, successor.key)
                        currNode.rightChild = newSubtree
                    }
                }
            }
        }

        var balancedNode: AVLNode<K, V> = currNode

        if (getBalanceFactor(currNode) < -1) { // if left subtree got bigger
            currNode.leftChild?.let {
                if (getBalanceFactor(it) > 0) { // if inserted node has greater key
                    currNode.leftChild = rotateLeft(it) // perform left rotation first
                }
                balancedNode = rotateRight(currNode) // anyway, perform left rotation
            }
        } else if (getBalanceFactor(currNode) > 1) { // if right subtree got bigger
            currNode.rightChild?.let {
                if (getBalanceFactor(it) < 0) { // if inserted node has greater key
                    currNode.rightChild = rotateRight(it) // perform right rotation first
                }
                balancedNode = rotateLeft(currNode) // anyway, perform left rotation
            }
        }

        updateHeight(balancedNode.leftChild)
        updateHeight(balancedNode.rightChild)
        updateHeight(balancedNode)

        return balancedNode
    }
}

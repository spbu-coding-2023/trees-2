package tsl.trees

import tsl.nodes.AVLNode

import kotlin.math.max

class AVLTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, AVLNode<K, V>>() {
    override fun insert(key: K, value: V): V? {
        val replacedValue = search(key)        // null if key isn't in the tree
        insertAndBalanceRecursively(root, key, value)
        return replacedValue
    }

    private fun insertAndBalanceRecursively(node: AVLNode<K, V>?, key: K, value: V): AVLNode<K, V>? {
        if (root == null) {
            root = AVLNode(key, value)
            return root
        }
        if (node == null) return AVLNode(key, value)

        if (key < node.key) {
            node.leftChild = insertAndBalanceRecursively(node.leftChild, key, value)
        } else if (key > node.key) {
            node.rightChild = insertAndBalanceRecursively(node.rightChild, key, value)
        } else return node

        updateHeight(node)

        val balanceFactor = getBalanceFactor(node)

        if (balanceFactor < -1) {                       // right cases
            node.leftChild?.let {
                if (key > it.key) {
                    node.leftChild = rotateLeft(it)     // left-right case
                }
                return rotateRight(node)
            }
        } else if (balanceFactor > 1) {                 // left cases
            node.rightChild?.let {
                if (key < it.key) {
                    node.rightChild = rotateRight(it)   // right-left case
                }
                return rotateLeft(node)
            }
        }

        return node
    }

    override fun delete(key: K): V? {
        val deletedValue: V = search(key) ?: return null    // if key isn't in the tree, there's nothing to delete
        deleteAndBalanceRecursively(root, key)
        return deletedValue
    }

    private fun deleteAndBalanceRecursively(node: AVLNode<K, V>?, key: K): AVLNode<K, V>? {
        if (node == null) return null           // node to be deleted was not found

        if (key < node.key) {
            node.leftChild = deleteAndBalanceRecursively(node.leftChild, key)
        } else if (key > node.key) {
            node.rightChild = deleteAndBalanceRecursively(node.rightChild, key)
        } else if (node.leftChild == null || node.rightChild == null) {     // if node to be deleted has 0 or 1 child
            return node.leftChild ?: node.rightChild
        } else {                                                            // if node to be deleted has 2 children
            val successor = getMinNode(node.rightChild)
            if (successor != null) {
                node.key = successor.key
                node.value = successor.value

                // delete successor node from tree
                val temp = deleteAndBalanceRecursively(node.rightChild, successor.key)
                if (temp != null) node.rightChild = temp
            }
        }

        updateHeight(node)

        val balanceFactor = getBalanceFactor(node)

        if (balanceFactor < -1) {                       // right rotation cases
            node.leftChild?.let {
                if (getBalanceFactor(it) > 0) {         // left-right case
                    node.leftChild = rotateLeft(it)
                }
                return rotateRight(node)
            }
        } else if (balanceFactor > 1) {                 // left rotation cases
            node.rightChild?.let {
                if (getBalanceFactor(it) < 0) {         // right-left case
                    node.rightChild = rotateRight(it)
                }
                return rotateLeft(node)
            }
        }

        return node
    }

    private fun rotateRight(oldUpperNode: AVLNode<K, V>): AVLNode<K, V> {
        val newUpperNode = oldUpperNode.leftChild ?: return oldUpperNode
        oldUpperNode.leftChild = newUpperNode.rightChild
        newUpperNode.rightChild = oldUpperNode

        if (root == oldUpperNode) root = newUpperNode       // if root was rotated, set new root

        updateHeight(oldUpperNode)
        updateHeight(newUpperNode)

        return newUpperNode
    }

    private fun rotateLeft(oldUpperNode: AVLNode<K, V>): AVLNode<K, V> {
        val newUpperNode = oldUpperNode.rightChild ?: return oldUpperNode
        oldUpperNode.rightChild = newUpperNode.leftChild
        newUpperNode.leftChild = oldUpperNode

        if (root == oldUpperNode) root = newUpperNode       // if root was rotated, set new root

        updateHeight(oldUpperNode)
        updateHeight(newUpperNode)

        return newUpperNode
    }

    private fun updateHeight(node: AVLNode<K, V>): Int {
        node.height = max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1
        return node.height
    }

    private fun getBalanceFactor(node: AVLNode<K, V>): Int {
        return getHeight(node.rightChild) - getHeight(node.leftChild)
    }

    private fun getHeight(node: AVLNode<K, V>?): Int {
        return node?.height ?: -1
    }
}

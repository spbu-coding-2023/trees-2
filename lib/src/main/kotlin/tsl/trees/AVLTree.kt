package tsl.trees

import tsl.nodes.AVLNode
import tsl.nodes.RBNode

import kotlin.math.max

class AVLTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, AVLNode<K, V>>() {

    // TODO: add return of replaced value or null if key was not present in the tree
    override fun insert(key: K, value: V): V? {
        insertAndBalanceRecursively(root, key, value)
        return value        // TODO: DELETE
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

        if (balanceFactor < -1) {               // *-right cases
            node.leftChild?.let {
                if (key > it.key) node.leftChild = rotateLeft(it)       // left-right case
                return rotateRight(node)
            }
        } else if (balanceFactor > 1) {         // *-left cases
            node.rightChild?.let {
                if (key < it.key) node.rightChild = rotateRight(it)     // right-left case
                return rotateLeft(node)
            }
        }

        return node
    }

    private fun rotateRight(oldUpperNode: AVLNode<K, V>): AVLNode<K, V> {
        val newUpperNode = oldUpperNode.leftChild ?: return oldUpperNode
        oldUpperNode.leftChild = newUpperNode.rightChild
        newUpperNode.rightChild = oldUpperNode

        if (root == oldUpperNode) root = newUpperNode      // if root was rotated, set new root

        updateHeight(oldUpperNode)
        updateHeight(newUpperNode)

        return newUpperNode
    }

    private fun rotateLeft(oldUpperNode: AVLNode<K, V>): AVLNode<K, V> {
        val newUpperNode = oldUpperNode.rightChild ?: return oldUpperNode
        oldUpperNode.rightChild = newUpperNode.leftChild
        newUpperNode.leftChild = oldUpperNode

        if (root == oldUpperNode) root = newUpperNode      // if root was rotated, set new root

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

    private fun getHeight(node: AVLNode<K, V>?) = node?.height ?: -1

    override fun delete(key: K): V? {
        deleteAndBalanceRecursively(root, key)
        return null         // TODO: DELETE
    }

    private fun deleteAndBalanceRecursively(node: AVLNode<K, V>?, key: K): AVLNode<K, V>? {
        if (node == null) return null

        if (key < node.key) {
            node.leftChild = deleteAndBalanceRecursively(node.leftChild, key)
        } else if (key > node.key) {
            node.rightChild = deleteAndBalanceRecursively(node.rightChild, key)
        } else {
            if (node.leftChild == null || node.rightChild == null) {        // case of 0 or 1 child
                val temp = node.leftChild ?: node.rightChild
                return temp
            } else {                                                        // case of 2 children
                var successor = node.rightChild
                while (successor?.leftChild != null) {
                    successor = successor.leftChild
                }
                if (successor != null) {
                    node.key = successor.key
                    node.value = successor.value
                    val temp = deleteAndBalanceRecursively(node.rightChild, successor.key)
                    if (temp != null) node.rightChild = temp
                }
            }
        }

        updateHeight(node)

        val balanceFactor = getBalanceFactor(node)

        if (balanceFactor < -1) {               // *-right cases
            node.leftChild?.let {
                if (getBalanceFactor(it) > 0) node.leftChild = rotateLeft(it)       // left-right case
                return rotateRight(node)
            }
        } else if (balanceFactor > 1) {         // *-left cases
            node.rightChild?.let {
                if (getBalanceFactor(it) < 0) node.rightChild = rotateRight(it)     // right-left case
                return rotateLeft(node)
            }
        }

        return node
    }
}

package tsl.trees

import tsl.nodes.AVLNode

import kotlin.math.max

class AVLTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, AVLNode<K, V>>() {

    // TODO: add return of replaced value or null if key was not present in the tree
    override fun insert(key: K, value: V) {
        insertAndBalanceRecursively(root, key, value)
    }

    private fun insertAndBalanceRecursively(node: AVLNode<K, V>?, key: K, value: V): AVLNode<K, V>? {
        if (root == null) {
            root = AVLNode(key, value)
            return root
        }
        if (node == null) return AVLNode(key, value)

        if (key == node.key) return node
        else if (key < node.key) {
            node.leftChild = insertAndBalanceRecursively(node.leftChild, key, value)
        } else if (key > node.key) {
            node.rightChild = insertAndBalanceRecursively(node.rightChild, key, value)
        }

        updateHeight(node)

        val balanceFactor = getHeight(node.rightChild) - getHeight(node.leftChild)

        // rotate if node is not balanced
        if (balanceFactor < -1) {               // *-right cases
            node.leftChild?.let {
                if (key > it.key) node.leftChild = rotateLeft(it)       // left-right case
                return rotateRight(node)
            }
        } else if (balanceFactor > 1) {           // *-left cases
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

    private fun rotateLeft(oldUpperNode: AVLNode<K, V>): AVLNode<K, V>? {
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

    private fun getHeight(node: AVLNode<K, V>?) = node?.height ?: -1

    override fun delete(key: K) {
        TODO("implement node deletion method")
    }
}
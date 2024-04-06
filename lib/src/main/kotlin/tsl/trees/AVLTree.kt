package tsl.trees

import kotlin.math.max
import tsl.nodes.AVLNode

class AVLTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, AVLNode<K, V>>() {

    public override fun insert(key: K, value: V): V? {
        val oldValueByKey = search(key)

        insertNodeAndBalanceRec(root, key, value)

        return oldValueByKey
    }

    private fun insertNodeAndBalanceRec(
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
                    insertNodeAndBalanceRec(currNode.leftChild, keyToInsert, valueToInsert)
            keyToInsert > currNode.key ->
                currNode.rightChild =
                    insertNodeAndBalanceRec(currNode.rightChild, keyToInsert, valueToInsert)
            else -> {
                currNode.value = valueToInsert
                return currNode
            }
        }

        var balancedNode: AVLNode<K, V> = currNode

        if (getBalanceFactor(currNode) < -1) {
            currNode.leftChild?.let {
                if (keyToInsert > it.key) currNode.leftChild = rotateLeft(it)

                balancedNode = rotateRight(currNode)
            }
        } else if (getBalanceFactor(currNode) > 1) {
            currNode.rightChild?.let {
                if (keyToInsert < it.key) currNode.rightChild = rotateRight(it)

                balancedNode = rotateLeft(currNode)
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
        val deletedValue = search(key) ?: return null

        deleteNodeAndBalanceRec(root, key)

        return deletedValue
    }

    private fun deleteNodeAndBalanceRec(currNode: AVLNode<K, V>?, keyToDelete: K): AVLNode<K, V>? {

        if (currNode == null) return null

        when {
            keyToDelete < currNode.key ->
                currNode.leftChild = deleteNodeAndBalanceRec(currNode.leftChild, keyToDelete)
            keyToDelete > currNode.key ->
                currNode.rightChild = deleteNodeAndBalanceRec(currNode.rightChild, keyToDelete)
            keyToDelete == currNode.key -> {
                if (currNode.leftChild != null && currNode.rightChild != null) {
                    val successor = getMinNodeRec(currNode.rightChild)
                    if (successor != null) {
                        currNode.key = successor.key
                        currNode.value = successor.value

                        val newSubtree = deleteNodeAndBalanceRec(currNode.rightChild, successor.key)
                        currNode.rightChild = newSubtree
                    }
                } else {
                    if (currNode == root) root = null
                    return currNode.leftChild ?: currNode.rightChild
                }
            }
        }

        var balancedNode: AVLNode<K, V> = currNode

        if (getBalanceFactor(currNode) < -1) {
            currNode.leftChild?.let {
                if (getBalanceFactor(it) > 0) currNode.leftChild = rotateLeft(it)

                balancedNode = rotateRight(currNode)
            }
        } else if (getBalanceFactor(currNode) > 1) {
            currNode.rightChild?.let {
                if (getBalanceFactor(it) < 0) currNode.rightChild = rotateRight(it)

                balancedNode = rotateLeft(currNode)
            }
        }

        updateHeight(balancedNode.leftChild)
        updateHeight(balancedNode.rightChild)
        updateHeight(balancedNode)

        return balancedNode
    }
}

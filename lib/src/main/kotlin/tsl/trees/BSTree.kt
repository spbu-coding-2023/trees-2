package tsl.trees

import tsl.nodes.BSTNode

class BSTree<K : Comparable<K>, V> : AbstractBinaryTree<K, V, BSTNode<K, V>>() {

    public override fun insert(key: K, value: V): V? {
        val nodeToInsert = BSTNode(key, value)

        if (root == null) {
            root = nodeToInsert
            return null
        }

        var currentNode = root

        while (currentNode != null) {
            when {
                nodeToInsert.key == currentNode.key -> {
                    val oldValue = currentNode.value
                    currentNode.value = nodeToInsert.value
                    return oldValue
                }
                nodeToInsert.key < currentNode.key -> {
                    if (currentNode.leftChild == null) {
                        currentNode.leftChild = nodeToInsert
                        return null
                    }
                    currentNode = currentNode.leftChild
                }
                nodeToInsert.key > currentNode.key -> {
                    if (currentNode.rightChild == null) {
                        currentNode.rightChild = nodeToInsert
                        return null
                    }
                    currentNode = currentNode.rightChild
                }
            }
        }
        return currentNode?.value
    }

    public override fun delete(key: K): V? {
        val deletedValue: V? = search(key) ?: return null

        deleteRecursively(root, key)

        return deletedValue
    }

    private fun deleteRecursively(currentNode: BSTNode<K, V>?, keyToDelete: K): BSTNode<K, V>? {
        if (currentNode == null) return null

        when {
            keyToDelete < currentNode.key ->
                currentNode.leftChild = deleteRecursively(currentNode.leftChild, keyToDelete)
            keyToDelete > currentNode.key ->
                currentNode.rightChild = deleteRecursively(currentNode.rightChild, keyToDelete)
            keyToDelete == currentNode.key -> {
                if (currentNode.hasTwoChildren()) {
                    val minRightNode = getMinNodeRec(currentNode.rightChild)
                    if (minRightNode != null) {
                        currentNode.key = minRightNode.key
                        currentNode.value = minRightNode.value
                        currentNode.rightChild =
                            deleteRecursively(currentNode.rightChild, minRightNode.key)
                    }
                } else {
                    if (currentNode == root) root = null
                    return currentNode.leftChild ?: currentNode.rightChild
                }
            }
        }

        return currentNode
    }
}
